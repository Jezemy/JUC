package chapter04;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author Jezemy
 * @date 2023-04-28 22:33
 */
public class CountZeroLongAdder {
    private static LongAdder adder = new LongAdder();

    private static Integer[] arrayOne = new Integer[]{0, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private static Integer[] arrayTwo = new Integer[]{10, 1, 2, 3, 0, 5, 6, 0, 56, 0};

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = arrayOne.length;
                for (Integer num : arrayOne) {
                    if (num == 0) {
                        adder.increment();
                    }
                }
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = arrayTwo.length;
                for (Integer num : arrayTwo) {
                    if (num == 0) {
                        adder.increment();
                    }
                }
            }
        });

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("ZERO COUNT: " + adder.longValue());


    }
}
