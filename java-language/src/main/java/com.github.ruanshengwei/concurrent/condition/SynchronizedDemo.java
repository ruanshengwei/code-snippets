package com.github.ruanshengwei.concurrent.condition;

/**
 * Synchronized 搭配使用Object wait  notify
 * 一个简单的生产者消费者模型
 */
public class SynchronizedDemo {

    private static final Object lock = new Object();

    private static int i=0;

    public static void main(String[] args) {

        new Thread(()->{

            synchronized (lock){
               for (;;){
                   if (i==0){
                       i++;
                       System.out.println("i:"+i);
                       lock.notify();
                   }else {
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
            }
        }).start();
        new Thread(()->{

            synchronized (lock){
               for (;;){
                   if (i>0){
                       i--;
                       lock.notify();
                   }else {
                       try {
                           System.out.println("i:"+i);
                           lock.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
            }
        }).start();
    }


}
