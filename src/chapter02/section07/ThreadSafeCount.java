package chapter02.section07;

public class ThreadSafeCount {
    private Long value;
    public synchronized Long getCount(){
        return value;
    }

    public synchronized  void increase(){
        ++value;
    }

    public static void main(String[] args) {
        ThreadSafeCount safeCount = new ThreadSafeCount();
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++){
                    safeCount.increase();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++){
                    System.out.println(safeCount.getCount());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}
