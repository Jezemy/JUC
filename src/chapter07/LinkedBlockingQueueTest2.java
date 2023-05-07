package chapter07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jezemy
 * @date 2023-05-07 22:25
 */
public class LinkedBlockingQueueTest2 {
    // 改用线程池
    public static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
    public static ExecutorService producer = Executors.newFixedThreadPool(1);
    public static ExecutorService consumer = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        // 先提交consumer
        consumer.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread() + "消费者开始从队列中获取数据");
                    System.out.println(Thread.currentThread() + "消费者获取数据成功，数据=" + blockingQueue.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread.sleep(1000);

        // 后提交producer
        producer.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread() + "生产者开始添加数据");
                    blockingQueue.put("abcd");
                    System.out.println(Thread.currentThread() + "生产者添加数据成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        producer.shutdown();
        consumer.shutdown();

    }
}
