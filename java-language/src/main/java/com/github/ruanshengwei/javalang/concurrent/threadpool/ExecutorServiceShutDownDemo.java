package com.github.ruanshengwei.javalang.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试
 * 可以通过调用线程池的shutdown或shutdownNow方法来关闭线程池。它们的原理是遍历线
 * 程池中的工作线程，然后逐个调用线程的interrupt方法来中断线程，所以无法响应中断的任务
 * 可能永远无法终止。但是它们存在一定的区别，shutdownNow首先将线程池的状态设置成
 * STOP，然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表，而
 * shutdown只是将线程池的状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线
 * 程。
 * 只要调用了这两个关闭方法中的任意一个，isShutdown方法就会返回true。当所有的任务
 * 都已关闭后，才表示线程池关闭成功，这时调用isTerminaed方法会返回true。至于应该调用哪
 * 一种方法来关闭线程池，应该由提交到线程池的任务特性决定，通常调用shutdown方法来关闭
 * 线程池，如果任务不一定要执行完，则可以调用shutdownNow方法。
 */
public class ExecutorServiceShutDownDemo {

    public static void main(String[] args) throws InterruptedException {

        shutDownNowTest();

        shutDownTest();

    }

    private static void shutDownTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {
                    // 忽略异常，阻止线程中断
                }
                System.out.println("shutdown : Hello,World!");
            }
        });
        // 停顿 1 s
        Thread.sleep(1 * 1000L);
        // 无法关闭任务
        executorService.shutdown();
    }

    private static void shutDownNowTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {
                    // 忽略异常，阻止线程中断
                }
                System.out.println("shutdownNow : Hello,World!");
            }
        });
        // 停顿 1 s
        Thread.sleep(1 * 1000L);
        // 无法关闭任务
        executorService.shutdownNow();
    }
}
