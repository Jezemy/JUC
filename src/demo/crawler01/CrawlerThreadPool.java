package demo.crawler01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author Jezemy
 * @date 2023-05-07 21:17
 */
public class CrawlerThreadPool {
    public static ExecutorService producer = Executors.newFixedThreadPool(2);
    public static ExecutorService consumer = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        /**
         * 主要有两种任务：
         *      主页面任务：爬取主页面，然后把子页面打包成subPage并设置倒计时交给阻塞队列，子页面全爬取完毕后再进行整合。
         *      子页面任务：爬取子页面，然后把倒计时减1
         * 流程：生产者线程读取任务，然后爬取主页面，爬取到一半把子页面提交到阻塞队列
         * 消费者线程从阻塞队列中获取子页面处理然后减少倒计时，对应的主页面的所有子页面全处理完毕后
         * 主页面整合子页面的内容打印出来，再继续去执行下一个主页面任务。
         *
         * 在本例中，生产者要处理的主页面是固定的，而消费者要处理的数量是不固定的。
         * 这里手动提交生产者的页面，无限循环消费者能够解决，但是最后线程池一直关不掉，不优雅
         *
         * 优雅一点的话还要把生产者和消费者线程池封装一层，假设称为P和C线程
         * 主线程只用开启P和C线程
         * P线程的作用是获取需要爬取的主页面，然后把每个页面封装成MainPageThread提交到producer线程池，并维护一个共享标记K，表示是否处理完毕，处理完毕就结束producer线程池
         * C线程的作用是根据标记K，如果K结束了，才停止从阻塞队列获取资源，并结束线程池consumer
         *
         * 生产者设置一个标记，标记生产者线程任务执行完毕没，只有生产者线程任务执行完毕，消费者才能不再无限循环取值
         */

        // 提交固定数量的主页面
        producer.submit(new MainPageThread(new MainPage("主页面A")));
        producer.submit(new MainPageThread(new MainPage("主页面B")));

        // 不断判断阻塞队列有没有数据
        while (CrawlerQueue.size() >= 0) {
            consumer.submit(new SubPageThread());
        }


        producer.shutdown();
        consumer.shutdown();

    }
}

// Page继承体系
class Page {
    protected String url;

    public Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}

class MainPage extends Page {

    public MainPage(String url) {
        super(url);
    }
}

class SubPage extends Page {
    private final CountDownLatch count;

    public SubPage(String url, CountDownLatch count) {
        super(url);
        this.count = count;
    }

    public void countDown() {
        this.count.countDown();
    }
}

class MainPageThread implements Runnable {
    public MainPage mainPage;
    public List<SubPage> subPages;

    public MainPageThread(MainPage mainPage) {
        this.mainPage = mainPage;
        subPages = new ArrayList<>();
    }

    @Override
    public void run() {

        // 子页面个数，这里随机了
        int n = new Random().nextInt(10);
        System.out.println(Thread.currentThread() + mainPage.getUrl() + "爬取主页面的信息:" + mainPage.getUrl() + ", 得到子页面的数量n=" + n);
        // 创建对应数量的cyclicBarrier
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            SubPage subPage = new SubPage("子页面-" + (i + 1), countDownLatch);
            // 添加到list，用于后面合并数据
            subPages.add(subPage);
            // 添加到队列中
            System.out.println(Thread.currentThread() + mainPage.getUrl() + "添加子页面信息...进度" + (i + 1) + "/" + n);
            try {
                // 模拟爬取主页面慢
                Thread.sleep(1000);
                CrawlerQueue.put(subPage);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread() + mainPage.getUrl() + "主线程子页面添加失败" + subPage.url);
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread() + mainPage.getUrl() + "主页面进程进入等待");


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> collect = subPages.stream().map(SubPage::getUrl).collect(Collectors.toList());
        System.out.println(Thread.currentThread() + mainPage.getUrl() + "合并数据:" + collect.toString());
        System.out.println(Thread.currentThread() + mainPage.getUrl() + "完整爬取完毕！");
    }
}


class SubPageThread implements Runnable {

    @Override
    public void run() {
        // 从阻塞队列中获取子页面进行处理
        try {
            SubPage subPage = CrawlerQueue.take();
            System.out.println(Thread.currentThread() + "消费者爬取子页面：" + subPage.getUrl());
            // 模拟爬取子页面慢
            Thread.sleep(500);
            subPage.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 阻塞队列
class CrawlerQueue {
    public static LinkedBlockingQueue<SubPage> blockingQueue = new LinkedBlockingQueue<>();

    public static void put(SubPage subPage) throws InterruptedException {
        blockingQueue.put(subPage);
    }

    public static SubPage take() throws InterruptedException {
        return blockingQueue.take();
    }

    public static int size() {
        return blockingQueue.size();
    }
}