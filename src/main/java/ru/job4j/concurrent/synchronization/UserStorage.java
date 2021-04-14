package ru.job4j.concurrent.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(@NotNull User user) {
        int id = user.getId();
        if (users.containsKey(id)) {
            return false;
        }
        users.put(id, user);

        return true;
    }

    public synchronized boolean update(@NotNull User user) {
        int id = user.getId();
        if (!users.containsKey(id)) {
            return false;
        }
        users.replace(user.getId(), user);

        return true;
    }

    public synchronized boolean delete(@NotNull User user) {
        int id = user.getId();
        if (!users.containsKey(id)) {
            return false;
        }
        users.remove(user.getId());

        return true;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!users.containsKey(fromId) || !users.containsKey(toId)) {
            return false;
        }
        User userFrom = users.get(fromId);
        User userTo = users.get(toId);
        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);

        return true;
    }
}
