package com.github.ruanshengwei.javalang.concurrent.unsafeconstructor;

/**
 * this指针逃逸demo
 * 对象还没有构造完成，它的this引用就被发布出去了。
 */
public class ThisEscapeThreadDemo {

    static ThisEscape thisEscape;

    public static void main(String[] args) {

        //在构造器中启动新的线程
//        newThreadInConstructor();

    }

    private static void newThreadInConstructor() {

        new Thread(()->{
            thisEscape = new ThisEscape("name",1);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //可以看到输出了name is ok 。但是还是空指针。在一般的构造过程中。
        // java会保证全部初始化完成后才将引用发布出来。所以this引用逃逸是一个特殊的情况
        System.out.println(thisEscape.getName());
        System.out.println(thisEscape.getId());
    }

}
