package com.github.ruanshengwei.javalang.concurrent.threadLocal;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ThreadLocal 内存泄漏问题探究
 * 每个线程中 含有 自己的 ThreadLocalMap。在ThreadLocalMap 中 使用 ThreadLocal作为key 进行变量保存
 * 存取内容时，都是通过ThreadLocal作为key 操作 ThreadLocalMap 。
 *
 * 为什么ThreadLocalMap 中 {@link ThreadLocal.ThreadLocalMap.Entry} 是weakReference类型的？
 */
public class ThreadLocalMemoryLeakDemo {

    private static ThreadLocal<String> shareThreadLoacl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

//        threadLocalTest();

        weakReferenceTest();

    }

    private static void weakReferenceTest() {

        //没有使用weakreference的情况下,product 置 null 后内存并为收回（map中还存在强引用）
        Product productA = new Product("1","A");

        Map mapA = new HashMap();
        mapA.put(productA,1);
        productA=null;

        Set keySetA =  mapA.keySet();
        System.out.println(keySetA);

        Product productB = new Product("2","B");
        WeakReference<Product> weakProductB = new WeakReference<>(productB);

        Map mapB = new HashMap();
        mapB.put(weakProductB,1);
        productB = null;
        Set keySetB =  mapB.keySet();
        WeakReference<Product> weakReference = (WeakReference<Product>)keySetB.iterator().next();

        while (true){
            if (weakReference.get()!=null){
                System.out.println(weakReference.get());
                System.out.println("not null");
            }else {
                System.out.println("Object has been collected.");
                break;
            }
        }

    }

    private static void threadLocalTest() throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        threadLocal.set("Test One");

        ThreadLocal<String> threadLocal1 = new ThreadLocal<>();

        threadLocal1.set("Test Two");

        System.out.println(threadLocal.get());
        System.out.println(threadLocal1.get());

        Thread A = new Thread(()->{
            shareThreadLoacl.set("Thread A");
            System.out.println(shareThreadLoacl.get());
        });
        A.setName("A");
        A.start();

        Thread B = new Thread(()->{
            shareThreadLoacl.set("Thread B");
            System.out.println(shareThreadLoacl.get());
        });
        B.setName("B");
        B.start();

        Thread.sleep(1000);
    }

    private static class Product {

        String id;

        String name;

        public Product(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
