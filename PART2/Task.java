package PART2;

import java.util.concurrent.Callable;

public class Task<T> implements Comparable<Task<T>> , Callable<T>{

    private TaskType priority;
    private Callable<T> callable;

    private Task(Callable<T> callable, TaskType priority) {
        this.callable = callable;
        this.priority = priority;
    }

    private Task(Callable<T> callable) {
        this.callable = callable;
        this.priority = TaskType.OTHER;
    }

    public static <V> Task<V> createTask(Callable<V> callable, TaskType type) {
        return new Task<>(callable, type);
    }

    public static <V> Task<V> createTask(Callable<V> callable) {
        return new Task<>(callable);
    }

    @Override
    public T call() throws Exception {
        return callable.call();
    }

    public TaskType getPriority(){
        return this.priority;
    }

    public Callable<T> getCallable(){
        return this.callable;
    }

    @Override
    public int compareTo(Task that) {
        return Integer.compare(this.priority.getPriorityValue(), that.priority.getPriorityValue());
    }
}
