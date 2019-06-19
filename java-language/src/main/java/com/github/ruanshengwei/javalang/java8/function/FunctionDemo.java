package com.github.ruanshengwei.javalang.java8.function;

import java.util.function.Function;

public class FunctionDemo {

    public static void main(String[] args) {
        Function<String,String> myFunction1 = (e)->{
            return "MyFunction1: "+e;
        };

        Function<String,String> myFunction2 = (e)->{
            return "MyFunction2: "+e;
        };

        System.out.println(myFunction1.andThen(myFunction2).apply("a"));
        System.out.println(myFunction1.compose(myFunction2).apply("a"));
    }

}
