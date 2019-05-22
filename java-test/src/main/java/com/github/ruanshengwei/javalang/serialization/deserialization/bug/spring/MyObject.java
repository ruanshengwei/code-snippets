package com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring;

import java.io.*;

/**
 * 从测试发现。从文件中读取object时（对象反序列化时）并不会触发构造函数 但是会执行 readObject 函数。
 * readObject函数如果是不安全的。那就有执行外部代码的可能。
 */
public class MyObject implements Serializable {

//    public static String exec(String cmd) throws Exception {
//        String sb = "";
//        BufferedInputStream in = new BufferedInputStream(Runtime.getRuntime().exec(cmd).getInputStream());
//        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
//        String lineStr;
//        while ((lineStr = inBr.readLine()) != null){
//            sb += lineStr + "\n";
//        }
//        inBr.close();
//        in.close();
//        return sb;
//    }

    public MyObject() {

        System.out.println("Constructor MyObject");
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        System.out.println("Constructor MyObject from client in readObject");
        System.out.println("Constructor MyObject from client in readObject");

        String sb = "";
        BufferedInputStream in = new BufferedInputStream(Runtime.getRuntime().exec("open /Applications/Calculator.app/").getInputStream());
        BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
        String lineStr;
        while ((lineStr = inBr.readLine()) != null){
            sb += lineStr + "\n";
        }
        inBr.close();
        in.close();
//        return sb;
//        String cmd="open /Applications/Calculator.app/";
//        try {
//            exec(cmd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
