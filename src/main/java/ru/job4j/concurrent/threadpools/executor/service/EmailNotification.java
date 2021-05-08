package ru.job4j.concurrent.threadpools.executor.service;

import java.util.concurrent.ExecutorService;

public class EmailNotification {
    ExecutorService executorService;

    public EmailNotification(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void emailTo(User user) {
        executorService.submit(() -> {
            String userName = user.getUsername();
            String email = user.getEmail();

            String subject = String.format("Notification %s to email %s.", userName, email);
            String body = String.format("Add a new event to %s", userName);

            send(subject, body, email);
        });

    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        executorService.shutdown();
    }
}
