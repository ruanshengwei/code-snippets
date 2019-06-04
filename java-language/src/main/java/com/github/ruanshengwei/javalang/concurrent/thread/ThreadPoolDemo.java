package com.github.ruanshengwei.javalang.concurrent.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 一个线程池，现在有很多个任务，有的任务计算量很大，有的任务IO量很大计算量很小。现在去每个线程池去拉取任务执行，
 * 有可能有的线程池总是拉取到计算量很大的任务，有的总是拉取到计算量很小的任务。如何优化？
 */
public class ThreadPoolDemo {

    private static Semaphore timeSemaphore = new Semaphore(5);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable timeTask = ()->{
            try {
                timeSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("time IO begin"+Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.err.println("time IO end"+Thread.currentThread().getName());
            timeSemaphore.release();
        };

        Runnable simpleTask = ()->{
            System.out.println("time Cpu begin"+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("time Cpu end"+Thread.currentThread().getName());
        };

        for (int i=0;i<100;i++){
            executor.submit(timeTask);
            executor.submit(simpleTask);
        }

    }
}
