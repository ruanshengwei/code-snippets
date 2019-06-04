package com.github.ruanshengwei.javalang.concurrent.tools;

import java.util.concurrent.CyclicBarrier;

/**CyclicBarrier和CountDownLatch的区别
 * CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重
 * 置。所以CyclicBarrier能处理更为复杂的业务场景。例如，如果计算发生错误，可以重置计数
 * 器，并让线程重新执行一次。
 */
public class CyclicBarrierTest {

    //2 代表着 必须有两个线程调用 await()方法 才能使得被阻塞的线程继续走下去
    private static CyclicBarrier simpleCyclicBarrier = new CyclicBarrier(2);

    //barrierAction Complete 会被优先输出
    private static CyclicBarrier barrierActionCyclicBarrier = new CyclicBarrier(2,()->{
        System.out.println("barrierAction Complete");
    });
    public static void main(String[] args) {
      simpleCyclicBarrierTest();
      barrierActionCyclicBarrierTest();
    }

    private static void barrierActionCyclicBarrierTest() {
        new Thread(()->{
            try {
                barrierActionCyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("barrierActionCyclicBarrier"+1);
        }).start();

        try {
            barrierActionCyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("barrierActionCyclicBarrier"+2);

    }

    private static void simpleCyclicBarrierTest() {
        new Thread(()->{
            try {
                simpleCyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();

        try {
            simpleCyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}
