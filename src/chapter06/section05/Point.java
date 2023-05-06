package chapter06.section05;

import java.util.concurrent.locks.StampedLock;

/**
 * @author Jezemy
 * @date 2023-05-02 12:29
 */
public class Point {
    private double x, y;

    // 锁实例
    private final StampedLock s = new StampedLock();

    // 写锁（writeLock）
    void move(double deltaX, double deltaY){
        long stamp = s.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            s.unlockWrite(stamp);
        }
    }

    // 乐观读锁(tryOptimisticRead)
    double distanceFromOrigin(){
        // 尝试获取乐观锁
        long stamp = s.tryOptimisticRead();
        // 将全部变量赋值到方法体栈内
        double currentX = x, currentY = y;
        // 检查（1）处获取了读锁标记后，锁有没有被其他线程排他性抢占
        if (!s.validate(stamp)){
            // 如果被抢占则获取一个共享读锁
            stamp = s.readLock();
            try {
                // 再次更新，将全部变量复制到方法体栈内
                currentX = x;
                currentY = y;
            } finally {
                // 释放共享读锁
                s.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 使用悲观锁获取读锁，并尝试转换为写锁
    void moveIfAtOrigin(double newX, double newY){
        long stamp = s.readLock();
        try {
            // 如果当前点在原点则移动
            while (x == 0.0 && y == 0.0){
                // 尝试将获取的读锁升级为写锁
                long ws = s.tryConvertToWriteLock(stamp);
                // 升级成功，则更新戳标记，并设置坐标值
                if (ws != 0L){
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放锁，显式获取独占写锁，然后循环重试
                    s.unlockRead(stamp);
                    stamp = s.writeLock();
                }
            }
        } finally {
            s.unlock(stamp);
        }
    }

}
