package com.github.ruanshengwei.javalang.java8.lambda;

import java.util.Objects;
import java.util.function.BiConsumer;

public class MethodReferenceDemo {

    int size(){
        return 0;
    }

//    static int size(Object object){
//        return 0;
//    }

    public static void main(String[] args) {

        //编译通过。当目标的functional interface type的SAM方法的返回类型是void,与他对接
        //的method reference的返回类型是什么都可以。只是会被忽略。
        BiConsumer<Integer,Integer> b1 = MethodReferenceDemo::add;
        //编译不通过。对于lambda表达式没有提供适配处理

//        BiConsumer<Integer,Integer> b2 = (x,y)->{
//            return 0;
//        };
        Fun<MethodReferenceDemo,Integer> f1 = MethodReferenceDemo::size;
//        f1.apply();
    }

    interface Fun<T,R>{
        R apply(T arg);
    }

    public static int add(int a,int b){
        return 0;
    }

    public static int add(int a,int b,int c){
        return 0;
    }
}
