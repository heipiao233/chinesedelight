package net.heipiao.chinesedelight.utils;

import java.util.function.Supplier;

public class LazySupplier<T> implements Supplier<T> {
    private T value;
    private final Supplier<T> supplier;
    public LazySupplier(Supplier<T> original) {
        this.supplier = original;
    }
    @Override
    public T get() {
        if(value == null) return value = supplier.get();
        return value;
    }
}
