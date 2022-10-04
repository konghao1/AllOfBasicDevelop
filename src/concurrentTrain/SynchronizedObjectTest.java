package concurrentTrain;

/**
 * @author kongh
 * @date 2022/10/4
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 1、一把锁只能由一个线程获取
 * 2、每个实例都有自己的一把锁，不同实例之间互不影响
 * 3、synchronized修饰的方法，无论正常执行完毕还是抛异常，都会释放锁
 * <p>
 * 加锁解锁原理：monitorenter、monitorexit，使得锁计数器+1或者-1
 * 可重入原理：在同一加解锁的过程中，每个对象拥有一个monitor计数器，当monitor计数器=0时候，释放锁，其他时间就+1或者-1来变化
 * 保证可见性原理：happens-before原则，对同一个监视器的解锁，happens-before该监视器的加锁
 */
public class SynchronizedObjectTest implements Runnable {

    static SynchronizedObjectTest instance = new SynchronizedObjectTest();

    Object block1 = new Object();
    Object block2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        //同步代码块为this，两个线程使用相同的锁
//        method1();
        //同步代码快为不同的实例锁，两个线程使用不同的锁
        method2();
    }

    private void method1() {
        synchronized (this) {
            System.out.println("我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }
    }

    private void method2() {
        synchronized (block1) {
            System.out.println("block1锁，我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block1锁，" + Thread.currentThread().getName() + "结束");
        }

        synchronized (block2) {
            System.out.println("block2锁，我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block2锁，" + Thread.currentThread().getName() + "结束");
        }
    }
}
