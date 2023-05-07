package chapter07;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jezemy
 * @date 2023-05-07 22:20
 */
public class LinkedBlockingQueueTest {
    public static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(new Runnable() {
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

        Thread consumer = new Thread(new Runnable() {
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

        // 先执行consumer再执行producer看看
        consumer.start();
        Thread.sleep(1000);
        producer.start();

    }
}
