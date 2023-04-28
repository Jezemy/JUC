package chapter05;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jezemy
 * @date 2023-04-28 23:56
 */
public class CopyListTest {
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 修改list中下标为1的元素
                arrayList.set(1, "baba");
                // 删除元素
                arrayList.remove(2);
                arrayList.remove(3);
            }
        });

        // 在启动子线程之前获取迭代器
        Iterator<String> iterator = arrayList.iterator();

        // 启动子线程
        thread.start();

        // 等待子线程执行完毕
        thread.join();

        // 迭代元素，迭代结果还是没有修改
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
