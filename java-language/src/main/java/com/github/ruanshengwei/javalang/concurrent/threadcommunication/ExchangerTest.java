package com.github.ruanshengwei.javalang.concurrent.threadcommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Exchanger 测试类
 * Exchanger类允许在两个线程之间定义同步点。
 * 当两个线程都到达同步点时，他们交换数据结构，
 * 因此第一个线程的数据结构进入到第二个线程中，
 * 第二个线程的数据结构进入到第一个线程中。
 */
public class ExchangerTest {

    static class Producer implements Runnable{

        //生产者、消费者交换的数据结构
        private List<String> buffer;

        //生产者、消费者的交换对象
        private Exchanger<List<String>> exchanger;

        public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i=1;i<=5;i++){
                System.out.println("生产者:第 "+i+" 次提供数据");
                for (int j=0;j<3;j++){
                    System.out.println("生产者装入: "+i+"  "+j);
                    buffer.add("buffer: "+ i + "--"+j);
                }
                System.out.println("生产者数据装入完毕,等待交换");
                try {
                    exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable{

        //生产者、消费者交换的数据结构
        private List<String> buffer;

        //生产者、消费者的交换对象
        private Exchanger<List<String>> exchanger;

        public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i=1;i<=5;i++){
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者第: "+i+" 次提取");
                for (int j=0;j<3;j++){
                    System.out.println("消费者: "+buffer.get(0));
                    buffer.remove(0);
                }
            }
        }
    }

    public static void main(String[] args) {

        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();

        Exchanger<List<String>> exchanger = new Exchanger<>();

        Thread producerThread = new Thread(new Producer(buffer1,exchanger));

        Thread consumerThread = new Thread(new Consumer(buffer2,exchanger));

        producerThread.start();
        consumerThread.start();

    }
}
