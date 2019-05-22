package com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 从测试发现。从文件中读取object时（对象反序列化时）并不会触发构造函数 但是会执行 readObject 函数
 * （执行的是本地的readObject 而不是传递过来的,被重写过的readObject函数）。
 * JtaTransactionManager 类的问题是 它本身readObject里面就会去执行外部代码。
 */
public class MyObject implements Serializable {

    private static final long serialVersionUID = -4365286012503534L;

    public MyObject() {

        System.out.println("Constructor MyObject");
    }

    public void test(){
        System.out.println("run test in server");
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        System.out.println("Constructor MyObject in readObject");
    }
}
