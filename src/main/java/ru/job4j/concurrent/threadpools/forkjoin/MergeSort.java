package ru.job4j.concurrent.threadpools.forkjoin;

import java.util.Arrays;

/**
 * см. src/main/java/sorting/SortirovkaSliyaniem.java
 * https://fb-ru.turbopages.org/fb.ru/s/article/49769/uu-sortirovka-sliyaniem-algoritm-preimuschestva-i-osobennosti
 * Для сортировки массива определенной длины требуется дополнительная область памяти аналогичного размера,
 * в которой по частям собирается отсортированный массив.
 */
public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private static int[] sort(int[] array, int from, int to) {
        // при следующем условии, массив из одного элемента
        // делить нечего, возвращаем элемент
        if (from == to) {
            return new int[]{array[from]};
        }
        // попали сюда, значит в массиве более одного элемента
        // находим середину
        int mid = (from + to) / 2;
        // объединяем отсортированные части
        return merge(
                // сортируем левую часть
                sort(array, from, mid),
                // сортируем правую часть
                sort(array, mid + 1, to)
        );
    }

    // Метод объединения двух отсортированных массивов
    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) { // элементы левого массива мы уже поместили в общий
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] < right[ri]) { // сравниваем элемент левог ои правого массива
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 7, 4, 8, 9};
        Arrays.stream(MergeSort.sort(array)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(array).forEach(System.out::print);
    }
}