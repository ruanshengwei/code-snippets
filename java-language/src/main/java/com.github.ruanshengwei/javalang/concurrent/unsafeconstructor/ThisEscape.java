package com.github.ruanshengwei.javalang.concurrent.unsafeconstructor;

import org.springframework.util.StringUtils;

/**
 * 用于测试的对象
 * 内部类、匿名内部类都可以访问外部类的对象的域
 * 内部类构造的时候，会把外部类的对象this隐式的作为一个参数传递给内部类的构造方法，这个工作是编译器做的
 *
 * ThisEscape在构造函数中引入了一个内部类EventListener，而内部类会自动的持有其外部类（这里是ThisEscape）的this引用。
 * source.registerListener会将内部类发布出去，从而ThisEscape.this引用也随着内部类被发布了出去。
 * 但此时ThisEscape对象还没有构造完成，id已被赋值为1，但name还没被赋值，仍然为null。
 */
public class ThisEscape {

    public String name;

    public int id;

    public ThisEscape(EventSource<EventListener> source) {
        id = 1;
        //内部类是可以直接访问外部类的成员变量的（外部类引用this被内部类获取了）
        source.registerListener((content)->{
            System.out.println(content);
            System.out.println("id: "+ThisEscape.this.id);
            if (StringUtils.isEmpty(ThisEscape.this.name)){
                System.err.println("catch");
            }
            System.out.println("name: "+ThisEscape.this.name);
        });

        //方法二:增加构造时间使 逃逸更容易复现
//        try {
//            Thread.sleep(1000); // 调用sleep模拟其他耗时的初始化操作
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        name = "ruanshengwei";
    }

    public ThisEscape(String name, int id) {
        this.name = name;
        System.out.println("name is ok");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
