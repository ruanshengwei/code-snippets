package com.github.ruanshengwei.javalang.concurrent.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * 有三个线程A、B、C 按顺序执行任务
 */
public class SequentiallyThread {

    private static volatile String state = "000";

    public static void main(String[] args) {
        //使用thread join 使得三个线程顺序执行
//        joinDemo();
//        lockSupportDemo();
        stateMachineDemo();
    }

    private static void stateMachineDemo() {
        Thread C = new Thread(()->{
            while (true){
                if (state.equals("110")){
                    System.out.println("C");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    state = "111";
                    break;

                }
            }
        });

        Thread B = new Thread(()->{
            while (true){
                if (state.equals("100")){
                    System.out.println("B");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    state = "110";
                    break;

                }
            }
        });

        Thread A = new Thread(()->{
            while (true){
                if (state.equals("000")){
                    System.out.println("A");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    state = "100";
                    break;

                }
            }
        });

        A.start();
        B.start();
        C.start();

    }

    private static void lockSupportDemo() {
        Thread C = new Thread(()->{
            LockSupport.park();
            System.out.println("C");
        });

        Thread B = new Thread(()->{
            LockSupport.park();
            System.out.println("B");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(C);
        });

        Thread A = new Thread(()->{
            System.out.println("A");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(B);
        });

        A.start();
        B.start();
        C.start();


    }

    private static void joinDemo() {

        Thread A = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A");
        });
        Thread B = new Thread(()->{
            try {
                A.join();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        });
        Thread C = new Thread(()->{
            try {
                B.join();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
        });

        A.start();
        B.start();
        C.start();
    }
}
