package com.github.ruanshengwei.concurrent.condition;

import java.util.concurrent.locks.LockSupport;

/**
 *  LockSupport 简单使用
 *  park()	相当于 suspend() 使线程挂起
 * unpark(Thread thread)	相当于 thread.resume() 唤醒线程
 * park(Object blocker)	功能和 park() 一样，但是 blocker 可以当作参数在不同线间传递
 * getBlocker(Thread t)	可以获取到线程 t 调用 park(Object blocker) 时传进去的 blocker
 * parkNanos(long nanos)	nanos 为纳秒，1毫秒(ms) = 1000000纳秒(ns)，让当前线程最长沉睡 nanos 时间长度（如果有其他线程在这时间内唤醒的话，会立刻运行）
 * parkNanos(Object blocker, long nanos)
 * parkUntil(long deadline)	deadline 为未来的一个时间时间戳，单位是毫秒，方法是作用是让当前线程沉睡至 deadline 的时间后重新唤醒
 * parkUntil(Object blocker, long deadline)	哈~这个也不说
 */
public class LockSupportDemo {

    private static Object tag = new Object();

    public static void main(String[] args) throws InterruptedException {
//        simpelLockSupport();
        lockSupportParkBlocker();
    }

    private static void lockSupportParkBlocker() throws InterruptedException {
        Thread threadTest = new Thread(()->{
            System.out.println("Thread Begin");
            System.out.println("LockSupport.park();");
            LockSupport.park("I am blocker");//可以用于线程间传递数据
            System.out.println("LockSupport.unpark();");
        });

        threadTest.start();

        Object blocker = LockSupport.getBlocker(threadTest);
        System.out.println(blocker);
        LockSupport.unpark(threadTest);
    }

    private static void simpelLockSupport() throws InterruptedException {

        Thread threadTest = new Thread(()->{
            System.out.println("Thread Begin");
            System.out.println("LockSupport.park();");
            LockSupport.park();
            System.out.println("LockSupport.unpark();");
        });

        threadTest.start();
        Thread.sleep(1000);
        LockSupport.unpark(threadTest);
    }

}
