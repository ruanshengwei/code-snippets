package com.github.ruanshengwei.javalang.proxy.dynamicproxy;

import com.github.ruanshengwei.javalang.proxy.UserDao;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 代理类的生成工具
 */
public class ProxyGeneratorUtils {

    public static void main(String[] args) {
        ProxyGeneratorUtils.writeProxyClassToHardDisk("/Users/ruanshengwei/Desktop/dev/code/code-snippets/java-language/src/main/java/com/github/ruanshengwei/javalang/proxy/dynamicproxy/$Proxy11.class");
    }

    /**
     * 把代理类的字节码写到硬盘上
     * @param path 保存路径
     */
    public static void writeProxyClassToHardDisk(String path) {
        // 第一种方法，这种方式在刚才分析ProxyGenerator时已经知道了
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);

        // 第二种方法

        // 获取代理类的字节码
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11", UserDao.class.getInterfaces());

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
