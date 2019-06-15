package com.github.ruanshengwei.javalang.proxy.dynamicproxy;

import com.github.ruanshengwei.javalang.proxy.ClassUserDao;
import com.github.ruanshengwei.javalang.proxy.IUserDao;
import com.github.ruanshengwei.javalang.proxy.UserDao;

/**
 * 动态代理 也是 jdk 代理
 *
 * 静态代理与动态代理的区别主要在：
 *
 * 静态代理在编译时就已经实现，编译完成后代理类是一个实际的class文件
 * 动态代理是在运行时动态生成的，即编译完成后没有实际的class文件，而是在运行时动态生成类字节码，并加载到JVM中
 * 特点：
 * 动态代理对象不需要实现接口，但是要求目标对象必须实现接口，否则不能使用动态代理。
 *
 */
public class DynamicUserProxy {

    public static void main(String[] args) {

       interfaceTest();

//       classTest();

    }

    private static void classTest() {

//        这是错误代码 jdk代理必须实现接口
//        ClassUserDao classUserDao = new ClassUserDao();
//
//        ClassUserDao proxy =  (ClassUserDao) new ProxyFactory(classUserDao).getProxyInstance();
//
//        proxy.classPrint();
    }

    private static void interfaceTest() {

        IUserDao target = new UserDao();
        System.out.println(target.getClass());  //输出目标对象信息
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());  //输出代理对象信息
        proxy.finalClass();  //执行代理方法
    }
}
