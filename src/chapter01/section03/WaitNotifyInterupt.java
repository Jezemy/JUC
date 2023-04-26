package chapter01.section03;

/**
 * @author Jezemy
 * @date 2023-04-26 22:27
 */
public class WaitNotifyInterupt {

    static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("----begin----");
                try {
                    synchronized (obj) {
                        obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        threadA.start();
        Thread.sleep(1000);
        System.out.println("---begin interript threadA---");
        threadA.interrupt();
        System.out.println("---end interript threadA---");
    }
}
