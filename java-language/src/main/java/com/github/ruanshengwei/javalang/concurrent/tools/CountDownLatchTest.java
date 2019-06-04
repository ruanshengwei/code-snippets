package com.github.ruanshengwei.javalang.concurrent.tools;

import java.util.concurrent.CountDownLatch;

/**
 * 等待多线程完成
 */
public class CountDownLatchTest {

    private static  CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {

        new Thread(()->{
            try {
                countDownLatch.countDown();
                System.out.println("1");
                Thread.sleep(1000);
                countDownLatch.countDown();
                System.out.println("2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Complete");
    }
}
