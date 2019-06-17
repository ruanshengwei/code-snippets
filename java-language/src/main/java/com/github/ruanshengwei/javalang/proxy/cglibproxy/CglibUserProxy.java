package com.github.ruanshengwei.javalang.proxy.cglibproxy;

import com.github.ruanshengwei.javalang.proxy.ClassUserDao;
import com.github.ruanshengwei.javalang.proxy.UserDao;

/**
 * cglib (Code Generation Library )是一个第三方代码生成类库，运行时在内存中动态生成一个子类对象从而实现对目标对象功能的扩展。
 *
 * cglib特点
 *
 * JDK的动态代理有一个限制，就是使用动态代理的对象必须实现一个或多个接口。
 * 如果想代理没有实现接口的类，就可以使用CGLIB实现。
 * CGLIB是一个强大的高性能的代码生成包，它可以在运行期扩展Java类与实现Java接口。
 * 它广泛的被许多AOP的框架使用，例如Spring AOP和dynaop，为他们提供方法的interception（拦截）。
 * CGLIB包的底层是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。
 * 不鼓励直接使用ASM，因为它需要你对JVM内部结构包括class文件的格式和指令集都很熟悉。
 * cglib与动态代理最大的区别就是
 *
 * 使用动态代理的对象必须实现一个或多个接口
 * 使用cglib代理的对象则无需实现接口，达到代理类无侵入。
 */
public class CglibUserProxy {

    public static void main(String[] args) {

        interfaceTest();

        finalMethodTest();

        classTest();
    }

    private static void finalMethodTest() {
        System.out.println("----------finalMethodTest-----------");
        //目标对象
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        //代理对象
        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        //执行代理对象方法
        proxy.finalClass();
    }

    private static void classTest() {
        System.out.println("----------classTest-----------");
        ClassUserDao classUserDao = new ClassUserDao();

        ClassUserDao proxy =  (ClassUserDao) new ProxyFactory(classUserDao).getProxyInstance();

        proxy.classPrint();
    }

    private static void interfaceTest() {
        System.out.println("----------interfaceTest-----------");
        //目标对象
        UserDao target = new UserDao();
        System.out.println(target.getClass());
        //代理对象
        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        //执行代理对象方法
        proxy.print();
    }
}
