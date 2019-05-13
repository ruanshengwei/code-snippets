package com.github.ruanshengwei.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *Condition 与 ReentrantLock 搭配使用。即 conditionOne.await(); conditionOne.signal(); 等必须在lock.lock()和lock.unlock之间才可以使用
 * Condition since 1.5
 */
public class ConditionReentrantLock{

    public static ReentrantLock lock = new ReentrantLock();
    //通过ReentrantLock创建Condition实例，并与之关联
    public static Condition conditionOne = lock.newCondition();
    public static Condition conditionTwo = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(()->{
            try
            {
                lock.lock();
                conditionOne.await();
                System.out.println("Thread One is going on");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        });
        threadOne.start();

        Thread threadTwo = new Thread(()->{
            try
            {
                lock.lock();
                conditionTwo.await();
                System.out.println("Thread Two is going on");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        });
        threadTwo.start();

        Thread.sleep(1000);
        lock.lock();
        conditionOne.signal();
        lock.unlock();//只有调用了 unlock  signal才会生效

        Thread.sleep(1000);
        lock.lock();
        conditionTwo.signal();
        lock.unlock();//只有调用了 unlock  signal才会生效
    }
}
