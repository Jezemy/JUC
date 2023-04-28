package chapter04;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @author Jezemy
 * @date 2023-04-28 22:25
 */
public class LongAccumulatorTest {
    public static void main(String[] args) {
        // 等价的写法

        LongAdder adder = new LongAdder();

        LongAccumulator accumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 0);
    }
}
