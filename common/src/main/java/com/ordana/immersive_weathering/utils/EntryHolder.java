package com.ordana.immersive_weathering.utils;

import java.util.function.Supplier;

public class EntryHolder<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private final String name;

    public EntryHolder(String name, Supplier<T> supplier) {
        this.supplier = supplier;
        this.name = name;
    }

    @Override
    public T get() {
        return supplier.get();
    }

    public String getID(){
        return name;
    }

    public static<T> EntryHolder<T> of(String name, Supplier<T> supplier){
        return new EntryHolder<>(name,supplier);
    }
}
