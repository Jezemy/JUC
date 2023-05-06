package chapter07;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Jezemy
 * @date 2023-05-05 20:34
 */
public class PriorityBlockingQueueTest {

    static class Task implements Comparable<Task> {
        private int priority = 0;
        private String taskName;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public int compareTo(Task o) {
            if (this.priority >= o.priority) {
                return 1;
            } else {
                return -1;
            }
        }

        public void doSomething() {
            System.out.println(taskName + ":" + priority);
        }
    }

    public static void main(String[] args) {
        PriorityBlockingQueue<Task> priorityQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName-" + i);
            priorityQueue.offer(task);
        }

        while (!priorityQueue.isEmpty()) {
            Task task = priorityQueue.poll();
            if (task != null) {
                task.doSomething();
            }
        }
    }
}
