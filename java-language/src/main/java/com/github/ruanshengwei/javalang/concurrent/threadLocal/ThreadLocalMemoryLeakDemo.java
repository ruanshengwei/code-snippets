package com.github.ruanshengwei.javalang.concurrent.threadLocal;

import java.lang.ref.ReferenceQueue;
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
 *
 * WeakReference是Java语言规范中为了区别直接的对象引用（程序中通过构造函数声明出来的对象引用）而定义的另外一种引用关系。
 * WeakReference标志性的特点是：reference实例不会影响到被应用对象的GC回收行为
 * （即只要对象被除WeakReference对象之外所有的对象解除引用后，该对象便可以被GC回收），
 * 只不过在被对象回收之后，reference实例想获得被应用的对象时程序会返回null。
 *
 * 我认为是这样:
 * 当一个ThrealLocal存在时,Entry即便是WeakReference的,但是因为容器中保留了 (static ThreadLocal<T>)还存在强引用,
 * 所以该对象并不会被回收，依旧可以get set。
 *
 * 但是当容器中的ThreadLocal可能已经发挥完作用,不再需要了的时候。如果不是weakreference。Entry 将对该ThreadLocal存在强引用。
 * 即便是外部将 static ThreadLocal = null 了。该threadLocal 仍然不会被回收。Entry.get仍将可以拿到ThreadLocal,这将导致
 * 这部分的内存无法自动清理,需要手动获取到这些threadlocalmap 并置空,且也不方便,至少得多做一个注册中心管理,以及监听器等。
 *
 * 而使用 weakReference 的情况下。 entry这部分的threadlocal会随着gc回收的时候被回收（且不会因为强引用导致heap中的内存无法清理）
 *
 * 但随着gc回收 entry.get 会为空,但是 entry对value存在强引用,value无法自己被回收,
 * 所以threadlocal 的 set get等方法中存在对key null的判断,以此来手动回收value
 *
 */
public class ThreadLocalMemoryLeakDemo {

    private static ThreadLocal<String> shareThreadLoacl = new ThreadLocal<>();

    private static ReferenceQueue<Product> referenceQueue = new ReferenceQueue();

    public static void main(String[] args) throws InterruptedException {

//        threadLocalTest();

//        weakReferenceTest();

    }

    private static void weakReferenceTest() {

        //没有使用weakreference的情况下,product 置 null 后内存并为收回（map中还存在强引用）
        Product productA = new Product("1","A");
        /**
         * //debug breakpoint 记录 productA的内存地址 (例如:HashMap@512)
         */
        Map mapA = new HashMap();
        mapA.put(productA,1);
        productA=null;

        Set keySetA =  mapA.keySet();
        System.out.println(keySetA);

        //
        Product productB = new Product("2","B");
        WeakReference<Product> weakProductB = new WeakReference<>(productB,referenceQueue);

        Map mapB = new HashMap();
        mapB.put(weakProductB,1);
        productB = null;
        Set keySetB =  mapB.keySet();
        WeakReference<Product> weakReference = (WeakReference<Product>)keySetB.iterator().next();

        while (true){
            if (weakReference.get()!=null){
                System.out.println(weakReference.get());
                System.out.println("not null");
                System.out.println("referenceQueue :"+referenceQueue.poll());
            }else {
                /**
                 * //debug breakpoint 记录 productA的内存地址 (例如:HashMap@512)
                 * 进行到此刻。可以在idea中的Memory中查看Product还剩几个。结果是1个。B 已经被回收。A 因为mapA存在的强引用并没有被回收
                 */
                WeakReference<Product> productWeakReference;
                if((productWeakReference = (WeakReference) referenceQueue.poll()) != null) {
                    System.out.println("回收了:" + productWeakReference);
                }
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
