package com.jumia.task.core.processor;

abstract class BaseProcessor<T> {

    public T execute() {
        validate();
        return process();
    }

    abstract void validate();

    abstract T process();
}
