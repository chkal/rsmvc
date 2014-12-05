package de.chkal.rsmvc.core;

public interface WritableModel<T> {

    T with(String key, Object value);

    T with(ReadableModel other);

}
