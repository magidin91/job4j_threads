package ru.job4j.concurrent.non_blocking_algoritm;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {

    @Test
    public void whenIncrement5AndIncrement10() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                    }
                });
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        count.increment();
                    }
                });

        first.start();
        second.start();

        first.join();
        second.join();

        assertThat(count.get(), is(15));
    }
}