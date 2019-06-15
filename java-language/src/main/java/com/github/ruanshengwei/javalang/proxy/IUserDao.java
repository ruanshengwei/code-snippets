package com.github.ruanshengwei.javalang.proxy;

public interface IUserDao {

    void save();

    default void print() {
        System.out.println("default print");
    }

    default void finalClass() {
        System.out.println("default final class");
    }
}
