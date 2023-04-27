package chapter02.section10;

/**
 * @author Jezemy
 * @date 2023-04-27 22:07
 */
public class CommandResort {
    private static int num = 0;
    private static volatile boolean ready = false;
    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                if(ready){  // 1
                    System.out.println(num + num);  // 2
                }
                System.out.println("ReadThread ...");
            }
        }
    }
    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2; // 3
            ready = true; // 4
            System.out.println("WriteThread set over....");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread rt = new ReadThread();
        rt.start();

        WriteThread wt = new WriteThread();
        wt.start();
        
        Thread.sleep(10);
        rt.interrupt();
        System.out.println("main exit");
    }
}
