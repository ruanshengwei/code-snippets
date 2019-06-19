package com.github.ruanshengwei.javalang.java8.lambda;


/**
 * 函数式接口可以被隐式转换为 lambda 表达式。
 *
 * new Runnable() 采用的是匿名内置类的方式。build之后可以看到 LambdaDemo$1.class
 */
public class LambdaDemo {

    public static void main(String[] args) {
        Thread A = new Thread(()->{
           System.out.println("Lambda A");
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class B");
            }
        });

        A.start();
        B.start();
    }

}
