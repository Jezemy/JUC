package chapter01.section02;

/**
 * @author Jezemy
 * @date 2023-04-26 22:25
 */
public class RunableTask implements Runnable{
    @Override
    public void run() {
        System.out.println("I am a child thread");
    }

    public static void main(String[] args) {
        new Thread(new RunableTask()).start();
    }
}
