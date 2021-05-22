package ru.job4j.concurrent.threadpools.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T el;

    public SearchIndex(T[] array, int from, int to, T el) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.el = el;
    }

    @Override
    protected Integer compute() {
        if (to + 1 - from <= 10) {  // если элементов <= 10 - ищем линейно и возвращаем индекс или -1
            for (int i = from; i <= to; i++) {
                if (array[i] == el || array[i].equals(el)) {
                    return i;
                }
            }
            return -1;
        }

        int mid = (from + to) / 2;
        // разбиваем текущую задачу на подзадачи - создаем задачи для сортировки частей
        SearchIndex leftSearchIndex = new SearchIndex(array, from, mid, el);
        SearchIndex rightSearchIndex = new SearchIndex(array, mid + 1, to, el);
        // производим деление. оно будет происходить, пока в частях не останется по одному элементу
        leftSearchIndex.fork();
        rightSearchIndex.fork();
        // Секция слияния результатов
        // объединяем полученные результаты
        Integer left = (Integer) leftSearchIndex.join();
        Integer right = (Integer) rightSearchIndex.join();

        return (left != -1) ? left : right;
    }

    public static <E> Integer search(E[] array, E el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndex<>(array, 0, array.length - 1, el));
    }
}