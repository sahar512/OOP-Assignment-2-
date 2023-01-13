package PART2;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.*;
public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Test.class);

    @Test
    public void partialTest() {
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }
    @Test
    public void Test1(){
        CustomExecutor CE = new CustomExecutor();
        //CE.setCorePoolSize(1);
        //CE.setMaxPoolSize(1);
        for(int i=0; i<5;i++) {
            Callable<String> test0 = () -> {
                StringBuilder s = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return s.reverse().toString();
            };
            var revTask1 = CE.submit(test0, TaskType.IO);
            var task0 = Task.createTask(() -> {
                int count = 0;
                for (int j = 1; j <= 20; j++) {
                    count += j;
                }
                return count;
            }, TaskType.COMPUTATIONAL);
            var counttask0 = CE.submit(task0);
            logger.info(() -> "current max pirority=" + CE.getCurrentMax());
            var mathTest = CE.submit(() -> {
                return 1000 * Math.pow(1.02, 5);
            }, TaskType.OTHER);
            Callable<String> test1 = () -> {
                StringBuilder s = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return s.reverse().toString();
            };
            var test = CE.submit(test1, TaskType.IO);
            logger.info(() -> "current max pirority=" + CE.getCurrentMax());
            // System.out.println(CE.get.toString());
            String get0;
            int get1;
            try {
                get0 = (String) test.get();
                get1 = (int) counttask0.get();
                double get2 = (double) mathTest.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info(() -> "Reversed String = " + get0);
            logger.info(() -> String.valueOf("Total Price = " + get1));
            logger.info(() -> "Current maximum priority = " +
                    CE.getCurrentMax());
        }
        CE.gracefullyTerminate();
    }
}