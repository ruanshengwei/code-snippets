package com.github.ruanshengwei.javalang.java8.lambda;

import java.io.Serializable;

public interface VoidFunction<T> extends Serializable {
    void call(T source);
}
