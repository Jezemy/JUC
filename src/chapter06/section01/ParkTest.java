package chapter06.section01;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jezemy
 * @date 2023-05-01 19:13
 */
public class ParkTest {
    public static void main(String[] args) {
        System.out.println("begin park");
        LockSupport.park();
        System.out.println("end park");
    }
}
