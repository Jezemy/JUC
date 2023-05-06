package chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Jezemy
 * @date 2023-05-06 22:22
 */
public class SemaphoreTest {
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread() + " over");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread() + " over");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 等待子线程执行完毕
        semaphore.acquire(2);
        System.out.println("all child thread over!");
        executorService.shutdown();
    }
}
