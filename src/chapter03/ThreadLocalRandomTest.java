package chapter03;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jezemy
 * @date 2023-04-27 23:58
 */
public class ThreadLocalRandomTest {
    public static void main(String[] args) {
        // 10 获取一个随机数生成器
        ThreadLocalRandom random = ThreadLocalRandom.current();
        
        // 11 输出10个[0, 5)之间的随机数
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
