package com.lxzh123.funcdemo.util;

public class TemplateTest<T> {
    private T item;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public boolean isNull() {
        return item == null;
    }

    public TemplateTest(T item) {
        this.item = item;
    }
}
