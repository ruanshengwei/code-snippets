package com.github.ruanshengwei.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *Condition 与 ReentrantLock 搭配使用。即 conditionOne.await(); conditionOne.signal(); 等必须在lock.lock()和lock.unlock之间才可以使用
 * Condition since 1.5
 *
 * . 1. Lock的业务层级是和synchronize是一样的 ,等待只是为了并发. 他的解等待,都是有前面排队的人通知释放的.
 *    Lock的实现就是基于    1.变量变更     2.和Object.wait类似的操作.park,unpark实现的.
 *  2.队列上的等待层级是更上层了,或者说和锁的业务用例稍有不同.
 *    同步阻塞队列不仅要有并发安全的要求,也要有生产者消费者的用例要求.
 * 3.lock.lock 拿不到锁,等待,等待被notify后,并没有抛出InterruptedException ,只是简单允许下去(内部吃下exception,继续走) .而wait会抛出exception,需要业务自己操作.
 * 4. condition.await 使用上最好是有条件死循环 await
 */
public class ConditionReentrantLock{

    public static final ReentrantLock lock = new ReentrantLock();
    //通过ReentrantLock创建Condition实例，并与之关联
    public static final Condition conditionOne = lock.newCondition();
    public static final Condition conditionTwo = lock.newCondition();

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
