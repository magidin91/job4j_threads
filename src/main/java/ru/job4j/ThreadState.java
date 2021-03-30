package ru.job4j;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        System.out.println("First is " + first.getState());
        System.out.println("Second is " + second.getState() + System.lineSeparator());

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First is " + first.getState());
            System.out.println("Second is " + second.getState());
        }

        System.out.println(System.lineSeparator() + "Both threads are terminated");
    }
}