package concurrentTrain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {

    /**
     * 同步一个代码块时，关键字只作用于同一个对象，如果调用两个不同对象的同步代码块，不会同步
     * <p>
     * 同步一个方法的时候与同步代码同理，synchronized关键字只作用与同一个对象
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
//        executorService.execute(() -> test1.fun());
//        executorService.execute(() -> test2.fun());
        System.out.println();
        /**
         * 两个线程调用了不同对象的同步代码块，因为synchronized修饰同一类，所以会进行同步，线城是安全的
         */
        executorService.execute(() -> test1.fun2());
        executorService.execute(() -> test2.fun2());
        executorService.shutdown();

    }


    public void fun() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + " " + Thread.currentThread().getName());
            }
        }
    }

    /**
     * synchronized修饰一个方法的时候与代码块同理，关键字作用于同一个对象上面
     */
    public synchronized void func() {
    }

    /**
     * synchronized作用于一个类，就算是调用同一个类的不同对象也会进行同步
     */
    public void fun2() {
        synchronized (SynchronizedTest.class) {
            for (int i = 0; i < 20; i++) {
                System.out.println(i + " " + Thread.currentThread().getName() + "func2()");
            }
        }
    }

    /**
     * 同步一个静态方法，作用于整个类，因为相当于给类对象加锁，属于"类锁"
     * 修饰非静态方法，属于"对象锁"
     */
    public synchronized static void func3() {

    }
}
