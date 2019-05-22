package com.github.ruanshengwei.javalang.concurrent.shareobject;

/**
 * 通过静态变量在线程间传递信息
 *
 * exchanger 中 A线程 放内容至 slot
 * B线程发现slot不为空。则将slot置空。并且将自己的值赋值给slot的match唤醒A
 * A直接读取threadlocal 中的match
 *
 * 记录一个有意思的问题: 当A线程中的ThreadLocal 变量通过slot 传递给 B的时候。B可以改变A ThreadLocal中的变量的值.但是修改引用都会失效。
 * 可能是因为只是引用。不像c语言中那样直接改变了 地址下面的值？
 */
public class ThreadLocalShareObject {

    private static ThreadLocal<Node> nodeThreadLocal = new ThreadLocal<>();

    private static Node slot = null;

    public static void main(String[] args) throws InterruptedException {

        Thread producer = new Thread(()->{
            nodeThreadLocal.set(new Node("producer"));
            slot = nodeThreadLocal.get();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(nodeThreadLocal.get()==null){
                System.out.println("null");
            }
            System.out.println(nodeThreadLocal.get().name);
        });

        Thread consumer = new Thread(()->{
            nodeThreadLocal.set(new Node("consumer"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (slot!=null){
                System.out.println("slot From Producer:"+slot.name);
//                slot.name="match";
                slot = new Node("match");
                slot = null;
            }
        });

        producer.start();
        consumer.start();
        Thread.sleep(3000);
    }

    static class Node{

        public String name;

        public Node(String name) {
            this.name = name;
        }
    }
}
