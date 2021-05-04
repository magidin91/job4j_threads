package ru.job4j.concurrent.non_blocking_algoritm.cache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}