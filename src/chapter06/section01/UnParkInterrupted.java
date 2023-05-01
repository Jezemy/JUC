package chapter06.section01;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jezemy
 * @date 2023-05-01 19:26
 */
public class UnParkInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park");

                // 调用park方法，挂起自己，只有子线程被中断才会退出循环
                while (!Thread.currentThread().isInterrupted()) {
                    LockSupport.park();
                }

                System.out.println("child thread end park");
            }
        });

        // 启动子线程
        thread.start();

        // 主线程休眠1s
        Thread.sleep(1000);

        System.out.println("main thread begin unpark!");

        // 使用unpark唤醒也没用，因为必须是中断才会唤醒
        LockSupport.unpark(thread);
        Thread.sleep(1000);

        System.out.println("main thread begin interrupted!");

        // 中断子线程
        thread.interrupt();
    }
}
