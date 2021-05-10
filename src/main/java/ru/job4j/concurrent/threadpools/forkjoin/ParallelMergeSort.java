package ru.job4j.concurrent.threadpools.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask<int[]> {

    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        // создаем задачи для сортировки частей
        // производим деление. оно будет происходить, пока в частях не останется по одному элементу
        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);

        leftSort.fork(); // запускаем задачу асинхронно
        rightSort.fork();
        // когда уже нечего делить, получаем результаты и считаем таски вверх
        int[] left = leftSort.join(); // дожидается выполнения задачи и возвращает результат её выполнения
        int[] right = rightSort.join();
        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }
}