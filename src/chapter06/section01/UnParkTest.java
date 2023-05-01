package chapter06.section01;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jezemy
 * @date 2023-05-01 19:18
 */
public class UnParkTest {
    public static void main(String[] args) {
        System.out.println("begin park");
        // 使用当前线程获取许可证
        LockSupport.unpark(Thread.currentThread());
        // 再次调用park方法
        LockSupport.park();
        System.out.println("end park");
    }
}
