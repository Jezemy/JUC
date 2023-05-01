package chapter06.section01;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jezemy
 * @date 2023-05-01 19:21
 */
public class UnParkTest2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park!");
                LockSupport.park();
                System.out.println("child thread end park!");
            }
        });

        // 启动子线程
        thread.start();

        // 主线程休眠
        Thread.sleep(1000);

        System.out.println("main thread begin unpark");

        // 主线程唤醒子线程
        LockSupport.unpark(thread);
    }
}
