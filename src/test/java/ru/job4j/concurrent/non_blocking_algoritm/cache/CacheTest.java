package ru.job4j.concurrent.non_blocking_algoritm.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CacheTest {

    @Test
    public void addOneEntityTwice() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);

        assertThat(cache.add(model), is(true));
        assertThat(cache.add(model), is(false));
    }

    @Test
    public void updateExistingEntity() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        Base stored = cache.get(1);
        stored.setName("SomeName");

        assertThat(cache.update(stored), is(true));
        /* проверяем изменение версии */
        assertThat(cache.get(1).getVersion(), is(1));
    }

    @Test
    public void updateNotExistingEntity() {
        Cache cache = new Cache();

        assertThat(cache.update(new Base(1, 0)), is(false));
    }

    @Test(expected = OptimisticException.class)
    public void updateNotEqualVersionEntities() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.update(new Base(1, 1));
    }

    @Test
    public void deleteEntity() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.delete(model);

        assertNull(cache.get(1));
    }
}