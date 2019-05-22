package com.github.ruanshengwei.javalang.serialization.deserialization.bug.spring;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(new MyObject());
//        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        socket.close();
    }
}
