package com.github.ruanshengwei.javalang.java8.function;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class SupplierDemo {

    public static void main(String[] args) {

        Supplier<String> mySupplier = ()->{
            return "A";
        };

        Stream.generate(mySupplier).forEach(System.out::println);
    }
}
