package chapter06.section02;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @author Jezemy
 * @date 2023-05-01 22:32
 */
public class ProducerConsumerTest {
    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();
    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int queueSize = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // 获取独占锁
                    lock.lock();
                    try {
                        // 如果队列满了，则等待
                        while (queue.size() == queueSize) {
                            notEmpty.await();
                        }

                        // 添加元素到队列
                        queue.add("ele-" + i);
                        // 唤醒消费线程
                        notFull.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // 获取独占锁
                    lock.lock();

                    try {
                        // 队列空则等待
                        while (0 == queue.size()) {
                            notFull.await();
                        }

                        // 消费一个元素
                        String ele = queue.poll();
                        System.out.println(ele);

                        // 唤醒生产线程
                        notEmpty.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
