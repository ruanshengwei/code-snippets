package com.github.ruanshengwei.javalang.proxy;

public class ClassUserDao implements IUserDao{

    public void classPrint(){
        System.out.println("This is a class");
    }

    @Override
    public void save() {
        System.out.println("ClassUserDao save");
    }
}
