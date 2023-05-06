package chapter07;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Jezemy
 * @date 2023-05-05 20:48
 */
public class DelayQueueTest {
    static class DelayedEle implements Delayed{

        private final long delayTime;
        private final long expire;
        private String taskName;

        public DelayedEle(long delay, String taskName){
            delayTime = delay;
            this.taskName = taskName;
            expire = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int)(this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayedEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        DelayQueue<DelayedEle> delayQueue = new DelayQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            DelayedEle element = new DelayedEle(random.nextInt(500), "task"+ i);
            delayQueue.offer(element);
        }

        DelayedEle ele = null;
        try{
            for (;;){
                while((ele = delayQueue.take()) != null){
                    System.out.println(ele.toString());
                }
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
