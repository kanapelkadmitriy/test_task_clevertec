package com.example.test_task_clevertec.cache;

public interface Cache<T> {

    T get(final Long key);
    void put(final Long key, final T value);
    int getSize();
    boolean isEmpty();
    void clear();
}
