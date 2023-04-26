package chapter01.section02;

/**
 * @author Jezemy
 * @date 2023-04-26 22:25
 */

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("I am a child Thread");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
