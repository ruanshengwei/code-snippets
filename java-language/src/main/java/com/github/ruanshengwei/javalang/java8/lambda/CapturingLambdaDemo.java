package com.github.ruanshengwei.javalang.java8.lambda;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * 由此可见 c1 情况并未捕获捕捉变量
 * c2 c3情况下会捕捉变量。一旦需要序列化的时候（序列化lambda产生的动态类），会序列化变量。此时如果变量未实现序列化则会导致错误。
 */
public class CapturingLambdaDemo {

    public static void main(String[] args) {

        VoidFunction<String> c1 = s -> System.out.println(s); // non-capturing lambda
        VoidFunction<String> c2 = System.out::println;        // instance method reference
        PrintStream sysout = System.out; // PrintStream doesn't implement Serializable
        VoidFunction<String> c3 = s -> sysout.println(s);     // capturing lambda

        // all print true because VoidFunction extends Serializable
        System.out.println(c1 instanceof Serializable);
        System.out.println(c2 instanceof Serializable);
        System.out.println(c3 instanceof Serializable);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // try serializing the non-capturing lambda
        boolean success = false;
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(c1); // succeed
            success = true;
        } catch (Exception e) {
            System.out.println("c1 serialization failed");
            e.printStackTrace(System.out);
        }
        if (success) System.out.println("c1 serialization succeeded");

        // try serializing the instance method reference
        success = false;
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(c2); // fail
            success = true;
        } catch (Exception e) {
            System.out.println("c2 serialization failed");
            e.printStackTrace(System.out);
        }
        if (success) System.out.println("c2 serialization succeeded");

        // try serializing the capturing lambda
        success = false;
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(c3); // fail
            success = true;
        } catch (Exception e) {
            System.out.println("c3 serialization failed");
            e.printStackTrace(System.out);
        }
        if (success) System.out.println("c3 serialization succeeded");


    }



}
