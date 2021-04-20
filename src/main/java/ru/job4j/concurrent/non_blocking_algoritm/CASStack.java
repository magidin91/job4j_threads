package ru.job4j.concurrent.non_blocking_algoritm;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASStack<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> current;
        do {
            current = head.get();
            temp.next = current;
        } while (!head.compareAndSet(current, temp));
    }

    public T poll() {
        Node<T> current;
        Node<T> temp;
        do {
            current = head.get();
            if (current == null) {
                throw new IllegalStateException("Stack is empty");
            }
            temp = current.next;
        } while (!head.compareAndSet(current, temp));
        current.next = null;
        return current.value;
    }

    private static final class Node<T> {
        final T value;

        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}