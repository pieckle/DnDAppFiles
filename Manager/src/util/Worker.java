package util;

import java.util.concurrent.*;

public class Worker {

    private static Worker instance;

    private static Worker getInstance(){
        if (instance == null){
            instance = new Worker();
        }
        return instance;
    }

    private ThreadPoolExecutor executor;

    private Worker(){
        executor = new ThreadPoolExecutor(
                1, 3,
                5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    public static <T> Future<T> get(Callable<T> task){
        return getInstance().executor.submit(task);
    }

    public static void run(Runnable task){
        getInstance().executor.submit(task);
    }

}
