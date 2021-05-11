package ru.job4j.concurrent.threadpools.threadpool;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@Ignore
public class ThreadPoolTest {


    @Test
    public void test() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPool.work(() -> copyOnWriteArrayList.add(finalI));
        }
        Thread.sleep(1000);
        assertThat(copyOnWriteArrayList, is(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
}