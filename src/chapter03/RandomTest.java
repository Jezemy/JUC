package chapter03;

import java.util.Random;

/**
 * @author Jezemy
 * @date 2023-04-27 23:39
 */
public class RandomTest {
    public static void main(String[] args) {
        // 创建一个默认种子的随机数生成器
        Random random = new Random();
        // 输出10个在[0, 5)之间的随机数
        for(int i = 0; i < 10; ++i){
            System.out.println(random.nextInt(5));
        }
    }
}
