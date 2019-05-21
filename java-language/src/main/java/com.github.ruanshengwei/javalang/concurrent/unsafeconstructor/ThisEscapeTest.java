package com.github.ruanshengwei.javalang.concurrent.unsafeconstructor;

public class ThisEscapeTest {

    public static void main(String[] args) {
        //方法1 增加循环 更容易发现
        for (;;){
            EventSource<EventListener> source = new EventSource<>();
            ListenerRunnable listRun = new ListenerRunnable(source);
            Thread thread = new Thread(listRun);
            thread.start();
            ThisEscape escape1 = new ThisEscape(source);
        }
    }
}
