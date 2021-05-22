package ru.job4j.concurrent.threadpools.forkjoin;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SearchIndexTest {
    @Test
    public void searchElement() {
        assertThat(SearchIndex.search(IntStream.range(0, 200).boxed().toArray(Integer[]::new), 100), is(100));
        assertThat(SearchIndex.search(new Integer[]{1, 2, 3, 2, 5, null, 7, 8, 9, 10, 11, 12}, 5), is(4));
    }

    @Test
    public void searchNull() {
        Integer[] array = {1, 2, 3, 2, 5, null, 7, 8, 9, 10, 11, 12};

        assertThat(SearchIndex.search(array, null), is(5));
    }
}