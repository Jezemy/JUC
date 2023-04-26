package chapter01.section10;

public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){}
            }
        });

        // 下面这行设置thread为守护进程，注释打开和关闭有不同结果
        thread.setDaemon(true);

        // 启动子线程
        thread.start();

        System.out.println("main thread is over");
    }
}
