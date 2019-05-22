package com.github.ruanshengwei.javalang.concurrent.shareobject;

/**
 * 引用测试
 */
public class ReferenceDemo {

    static class Node{
        public String name;

        public Node(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Node A = new Node("A");
        Node B = A;

        System.out.println("A:"+A.name);
        System.out.println("B:"+B.name);

        B = null;

        System.out.println("A:"+A.name);
        System.out.println("B:"+B.name);
    }

}
