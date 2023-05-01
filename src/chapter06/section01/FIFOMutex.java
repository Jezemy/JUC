package chapter06.section01;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jezemy
 * @date 2023-05-01 19:37
 */
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<Thread>();

    public void lock(){
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // 如果当前线程不在队列队首，且不能切换上锁状态，那就阻塞
        while(waiters.peek() != current || !locked.compareAndSet(false, true)){
            LockSupport.park(this);
            // 判断当前线程唤醒原因是否是被中断
            if(Thread.interrupted()){
                wasInterrupted = true;
            }
        }
        // 如果当前线程执行完了，就从队列中移出
        waiters.remove();
        // 如果当前线程是因为被中断而返回的，但是中断一次只是不阻塞，要再中断一次才是抛出异常把该线程阻塞掉
        if(wasInterrupted){
            current.interrupt();
        }

    }
    public void unlock(){
        // 解锁就是唤醒队列第一个线程
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
