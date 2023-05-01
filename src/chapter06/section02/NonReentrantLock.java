package chapter06.section02;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Jezemy
 * @date 2023-05-01 22:21
 */
public class NonReentrantLock implements Lock, Serializable {

    // 内部帮助类
    private static class Sync extends AbstractQueuedSynchronizer {
        // 是否锁已经被持有

        @Override
        protected boolean isHeldExclusively() {
            // 判断该锁是否被占用
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            // CAS讲state从0切换为1
            if (compareAndSetState(0, 1)){
                // 设置当前持有锁的线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0) throw new IllegalMonitorStateException();
            // 重置持锁线程为空
            setExclusiveOwnerThread(null);
            // 设置当前锁的状态为0，表示未占用
            setState(0);
            return true;
        }

        // 提供条件变量接口
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    // 创建一个Sync来做具体的工作
    private final Sync sync = new Sync();


    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
