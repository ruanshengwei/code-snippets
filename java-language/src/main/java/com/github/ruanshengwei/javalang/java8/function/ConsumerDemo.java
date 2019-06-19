package com.github.ruanshengwei.javalang.java8.function;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerDemo {

    //方法引用
    static class MyConsumer{
        void MyAccept(String s){
            System.out.println("ruanshengwei:"+s);
        }

        static void MyAcceptStatic(String s){
            System.out.println("ruanshengwei:"+s);
        }
    }


    public static void main(String[] args) {

        Consumer<String> testConsumer = (a)->{
            System.out.println("testConsumer:"+a);
        };
        Stream.of("one","two","three").forEach(new MyConsumer()::MyAccept);
        Stream.of("one","two","three").forEach(MyConsumer::MyAcceptStatic);
        Stream.of("one","two","three").forEach(testConsumer);
    }

}
