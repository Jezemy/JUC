package chapter01.section11;

public class ThreadLocalTest {
    static void print(String str){
        // 1.1 打印当前线程本地内存中localVariable变量
        System.out.println(str + ":" + localVariable.get());
        // 1.2 清除当前线程本地内存中的localVariable变量
//        localVariable.remove();
    }
    // 2 创建ThreadLocal对象
    static ThreadLocal<String> localVariable = new ThreadLocal<>();

    public static void main(String[] args) {
        // 3 创建线程one
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                // 3.1 设置线程One中本地变量localVariable的值
                localVariable.set("threadOne local variable");
                // 3.2 调用打印函数
                print("threadOne");
                // 3.3 打印本地变量值
                System.out.println("threadOne remove after" + ":" + localVariable.get());
            }
        });

        // 4 创建线程two
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                // 3.1 设置线程Two中本地变量localVariable的值
                localVariable.set("threadTwo local variable");
                // 3.2 调用打印函数
                print("threadTwo");
                // 3.3 打印本地变量值
                System.out.println("threadTwo remove after" + ":" + localVariable.get());
            }
        });

        // 5 启动线程
        threadOne.start();
        threadTwo.start();


    }
}
