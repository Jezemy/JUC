package chapter02.section11;

/**
 * @author Jezemy
 * @date 2023-04-27 22:27
 */
public class TestForContent {
    static final int LINE_NUM = 1024;
    static final int COLUM_NUM = 1024;

    public static void main(String[] args) {
        long[][] array = new long[LINE_NUM][COLUM_NUM];

        long startTime = System.currentTimeMillis();
        // 逐行遍历
        for(int i = 0; i < LINE_NUM; i++){
            for(int j = 0; j < COLUM_NUM; j++){
                array[i][j] = i * 2 + j;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("cache time:" + (endTime - startTime));  // 9

    }
}
