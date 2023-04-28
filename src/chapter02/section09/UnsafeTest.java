package chapter02.section09;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Jezemy
 * @date 2023-04-28 20:10
 */
public class UnsafeTest {
    static final Unsafe unsafe;

    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            // 使用反射获取Unsafe的成员变量theUnsafe
            Field field  = Unsafe.class.getDeclaredField("theUnsafe");

            // 设置为可存取
            field.setAccessible(true);

            // 获取该对象的值
            unsafe = (Unsafe) field.get(null);

            // 获取state在TestUnsafe中的偏移量
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        Boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);
    }

}
