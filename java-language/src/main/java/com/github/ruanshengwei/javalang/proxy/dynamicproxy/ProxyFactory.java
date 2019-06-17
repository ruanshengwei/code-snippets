package com.github.ruanshengwei.javalang.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    private Object target;// 维护一个目标对象

    public ProxyFactory(Object target) {
        this.target = target;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                (proxyInstance,method,args)->{
                    //单步调试时IDEA会调用被代理类的toString()方法（展示变量的值）
                    //例如在窗口中粘贴:method.getName() 每执行一次 控制台都会重复输出信息
                    // 代理UserDao类会代理该类的所有方法（包括toString），因此会重复输出
                    System.out.println("----------"+method.getName()+"-----------");
                    System.out.println("开启事务");

                    // 执行目标对象方法
                    Object returnValue = method.invoke(target, args);

                    System.out.println("提交事务");
                    return null;
                });
    }
}
