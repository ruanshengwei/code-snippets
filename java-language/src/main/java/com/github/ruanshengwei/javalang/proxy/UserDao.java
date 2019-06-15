package com.github.ruanshengwei.javalang.proxy;

public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("保存数据");
    }

    @Override
    public void print() {
        System.out.println("print!!!");
    }

    @Override
    public final void finalClass() {
        System.out.println("Final Class");
    }
}
