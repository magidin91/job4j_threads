package ru.job4j.concurrent.non_blocking_algoritm.cache;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public Base get(Integer id) {
        return memory.get(id);
    }

    public boolean add(Base model) {
        /* вызываем переопределенный в ConcurrentHashMap метод putIfAbsent */
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Обновляем данные, если значение присутствует и версии совпадают
     */
    public boolean update(Base model) {
        Integer id = model.getId();
        return memory.computeIfPresent(id, checkVersion(model, id)) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * Проверка и обновление версии.
     */
    @NotNull
    private BiFunction<Integer, Base, Base> checkVersion(Base model, Integer id) {
        return (key, oldValue) -> {
            int modelVersion = model.getVersion();
            if (oldValue.getVersion() != modelVersion) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(id, ++modelVersion);
        };
    }
}