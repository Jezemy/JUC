package chapter01.section11;

public class ThreadLocalSon {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello word");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread: " + threadLocal.get());
            }
        });
        thread.start();
        System.out.println("main: " + threadLocal.get());
    }
}
