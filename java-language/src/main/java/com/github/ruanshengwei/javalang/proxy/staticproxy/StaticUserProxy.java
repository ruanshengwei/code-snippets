package com.github.ruanshengwei.javalang.proxy.staticproxy;

import com.github.ruanshengwei.javalang.proxy.IUserDao;
import com.github.ruanshengwei.javalang.proxy.UserDao;

/**
 * 静态代理
 */
public class StaticUserProxy {

    public static void main(String[] args) {
        //目标对象
        IUserDao target = new UserDao();
        //代理对象
        UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();
    }
}
