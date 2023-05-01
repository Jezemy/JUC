package chapter06.section02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jezemy
 * @date 2023-05-01 22:06
 */
public class ConditionTest {
    public static void main(String[] args) {
        // 创建基于AQS的锁
        ReentrantLock lock = new ReentrantLock();
        // 根据锁创建条件变量
        Condition condition = lock.newCondition();
        // 先获取到锁
        lock.lock();
        try {
            System.out.println("begin wait");
            // 再调用条件变量的await方法
            condition.await();
            System.out.println("end wait");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        lock.lock();
        try {
            System.out.println("begin signal");
            condition.signal();
            System.out.println("end signal");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
