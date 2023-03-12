package com.example.test_task_clevertec.cache;

public interface Cache<T> {

    T get(final Long key);
    void put(final Long key, final Object object);
    void delete(final Long key);
    int getSize();
    boolean isEmpty();
    void clear();
    boolean support(Class<?> type);
}
