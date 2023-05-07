package demo.crawler01;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jezemy
 * @date 2023-05-07 23:07
 */
public class CrawlerThreadPoolImprove {
    public static AtomicBoolean Finished = new AtomicBoolean(false);
    public static void main(String[] args) {
        // TODO 不知道为什么还是不能停下来
        new Thread(new P(Finished)).start();
        new Thread(new C(Finished)).start();
    }
}


class P implements Runnable {
    private AtomicBoolean Finished;
    private ExecutorService producer = Executors.newFixedThreadPool(2);

    public P(AtomicBoolean Finished){
        this.Finished = Finished;
    }

    @Override
    public void run() {
        // 处理所有主页面
        int n = new Random().nextInt(5);
        for (int i = 1; i <= n; i++) {
            MainPageThread mainPageThread = new MainPageThread(new MainPage("主页面" + i));
            producer.submit(mainPageThread);
        }
        // TODO 放这也不对，万一
        Finished.set(true);
        producer.shutdown();
    }
}

class C implements Runnable {
    public AtomicBoolean Finished;
    public ExecutorService consumer = Executors.newFixedThreadPool(2);

    public C(AtomicBoolean Finished){
        this.Finished = Finished;
    }

    @Override
    public void run() {
        // 如果已经finish，并且队列东西取完了，才能结束
        while (!Finished.get() || CrawlerQueue.size() > 0){
            consumer.submit(new SubPageThread());
        }
        consumer.shutdown();
    }
}