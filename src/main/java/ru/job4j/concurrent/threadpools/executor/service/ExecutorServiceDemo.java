package ru.job4j.concurrent.threadpools.executor.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        pool.submit(() -> System.out.println("Execute " + Thread.currentThread().getName()));
        pool.submit(() -> System.out.println("Execute " + Thread.currentThread().getName()));

        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
