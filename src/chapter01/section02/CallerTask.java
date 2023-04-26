package chapter01.section02;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Jezemy
 * @date 2023-04-26 22:25
 */
public class CallerTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "Hello";
    }

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new CallerTask());
        new Thread(futureTask).start();
        try {
            String s = futureTask.get();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
