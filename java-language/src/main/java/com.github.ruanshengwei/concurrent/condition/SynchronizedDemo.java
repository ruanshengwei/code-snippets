package com.github.ruanshengwei.concurrent.condition;

/**
 * Synchronized 搭配使用Object wait  notify
 * 一个简单的生产者消费者模型
 */
public class SynchronizedDemo {

    private static final Object lock = new Object();

    private static int i=0;

    /**
     * 通过修改 变量 i 的值。控制其处于 0 1 两个之间变化。
     * @param args
     */
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
