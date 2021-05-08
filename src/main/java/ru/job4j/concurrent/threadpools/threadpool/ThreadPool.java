package ru.job4j.concurrent.threadpools.threadpool;

import ru.job4j.concurrent.wait_notify_notify_all.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    public static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public void work(Runnable job) {
        tasks.offer(job);
        addWorker();
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private void addWorker() {
        if (threads.size() < SIZE){
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                Runnable runnable = tasks.poll();
                                runnable.run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            threads.add(thread);
            thread.start();
        }
    }
}