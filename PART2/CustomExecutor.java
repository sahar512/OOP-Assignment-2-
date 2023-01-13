package PART2;

import java.util.PriorityQueue;
import java.util.concurrent.*;


public class CustomExecutor {
    // A custom ThreadPoolExecutor executor that overrides the newTaskFor method
    // to result a Comparable FutureTask instance.
    static class CustomThreadPoolExecutor extends ThreadPoolExecutor {
        public CustomThreadPoolExecutor(BlockingQueue<Runnable> workQueue) {
            //Runtime.getRuntime().availableProcessors()
            super(Runtime.getRuntime().availableProcessors()/2,
                    Runtime.getRuntime().availableProcessors(),
                    300,
                    TimeUnit.SECONDS, workQueue);
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            return new CustomFutureTask<T>(callable);
        }
    }

    // A comparable FutureTask
    static class CustomFutureTask<T> extends FutureTask<T> implements Comparable<CustomFutureTask<T>> {

        private final Task<T> task;

        public CustomFutureTask(Callable<T> task) {
            super(task);
            this.task = (Task<T>) task;
        }

        @Override
        public int compareTo(CustomFutureTask that) {
            return task.compareTo(that.task);
        }

        public int getPriority() {
            return this.task.getPriority().getPriorityValue();
        }
    }


    private ThreadPoolExecutor pool;
    private final PriorityBlockingQueue<Runnable> queue;

    public CustomExecutor() {
        queue = new PriorityBlockingQueue<Runnable>();
        pool = new CustomThreadPoolExecutor(queue);
    }

    private <T> Future<T> submit(Task<T> t) {
        return pool.submit(t);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        var task = Task.createTask(callable);
        return submit(task);
    }

    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        var task = Task.createTask(callable, type);
        return submit(task);
    }


    public int getCurrentMax() {
        synchronized (queue) {
            if (queue.peek() instanceof CustomFutureTask<?>)
                return ((CustomFutureTask<?>) queue.peek()).getPriority();
        }
        return -1;
    }

    public void gracefullyTerminate() {
        pool.shutdown();
    }
}

