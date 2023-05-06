package chapter06.section04;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Jezemy
 * @date 2023-05-02 12:16
 */
public class ReentrantReadWriteLockList {
    // 线程不安全的list
    private ArrayList<String> array = new ArrayList<>();
    // 独占锁
    private volatile ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    // 添加元素
    public void add(String e){
        writeLock.lock();
        try {
            array.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    // 删除元素
    public void remove(String e){
        writeLock.lock();
        try {
            array.remove(e);
        } finally {
            writeLock.unlock();
        }
    }

    // 获取数据
    public String get(int index){
        readLock.lock();
        try {
            return array.get(index);
        } finally {
            readLock.unlock();
        }
    }
}
