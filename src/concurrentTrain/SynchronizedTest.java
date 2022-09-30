package concurrentTrain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {

    /**
     * 同步一个代码块时，关键字只作用于同一个对象，如果调用两个不同对象的同步代码块，不会同步
     */
    public static void main(String[] args) {
        SynchronizedTest test1 = new SynchronizedTest();
        SynchronizedTest test2 = new SynchronizedTest();
        ExecutorService executorService = Executors.newCachedThreadPool();
        /**
         * 两个不同的线程调用的是同一个对象的同步代码快，则两个线程会进行同步，是线程安全的，当线程A进入代码块时线程B必须等待
         */
        /*executorService.execute(() -> test1.fun());
        executorService.execute(() -> test1.fun());*/
        System.out.println();
        /**
         * 两个线程调用了不同对象的同步代码快，此时两个线程不会进行同步，是线程不安全的，输出会交叉执行
         */
        executorService.execute(() -> test1.fun());
        executorService.execute(() -> test2.fun());
        executorService.shutdown();

    }


    public void fun() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + " " + Thread.currentThread().getName());
            }
        }
    }
}
