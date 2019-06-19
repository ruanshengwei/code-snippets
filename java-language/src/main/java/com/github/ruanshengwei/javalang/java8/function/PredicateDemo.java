package com.github.ruanshengwei.javalang.java8.function;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateDemo {

    public static void main(String[] args) {
        Predicate<String> predicate1 = (e)->{
            return true;
        };
        Predicate<String> predicate2 = (e)->{
            return false;
        };
        System.out.println(predicate1.test("one"));
        System.out.println(predicate1.negate().test("one"));

        System.out.println(predicate2.test("one"));
        System.out.println(predicate2.and(predicate1).test("one"));
        System.out.println(predicate2.or(predicate1).test("one"));

        Stream.of("one","two","three").filter(predicate1).forEach(System.out::println);
    }
}
