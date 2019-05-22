package com.github.ruanshengwei.javalang.serialization.deserialization.bug;

import java.io.*;

/**
 * 反序列化步骤。
 */
public class DeserializationDemo {

    public static void main(String[] args) throws Exception{
        //将String 对象（obj）持久化到文件中
//       saveString();

       //将自定义对象持久化到文件中;
       saveMyObject();
    }

    private static void saveMyObject() throws Exception {

        //定义myObj对象
        MyObject myObj = new MyObject();
        myObj.name = "hi";
        //创建一个包含对象进行反序列化信息的”object”数据文件
        FileOutputStream fos = new FileOutputStream("object");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        //writeObject()方法将myObj对象写入object文件
        os.writeObject(myObj);
        os.close();
        //从文件中反序列化obj对象
        FileInputStream fis = new FileInputStream("object");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //恢复对象
        MyObject objectFromDisk = (MyObject)ois.readObject();
        System.out.println(objectFromDisk.name);
        ois.close();
    }

    private static void saveString() throws Exception{
        //定义obj对象
        String obj="hello world!";
        //创建一个包含对象进行反序列化信息的”object”数据文件
        FileOutputStream fos=new FileOutputStream("object");
        ObjectOutputStream os=new ObjectOutputStream(fos);
        //writeObject()方法将obj对象写入object文件
        os.writeObject(obj);
        os.close();
        //从文件中反序列化obj对象
        FileInputStream fis=new FileInputStream("object");
        ObjectInputStream ois=new ObjectInputStream(fis);
        //恢复对象
        String obj2=(String)ois.readObject();
        System.out.print(obj2);
        ois.close();
    }

    static class MyObject implements Serializable {
        public String name;

        public MyObject() {
            System.out.println("in Consturctor");
        }

        //重写readObject()方法
        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            //执行默认的readObject()方法
            in.defaultReadObject();
            //执行打开计算器程序命令
            Runtime.getRuntime().exec("open /Applications/Calculator.app/");
        }
    }
}
