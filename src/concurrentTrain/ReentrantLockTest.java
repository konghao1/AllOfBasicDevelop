package concurrentTrain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kongh
 * @date 2022/10/4
 */


/**
 * 与synchronized关键字的比较
 *
 * 1、synchronized为JVM实现，ReentrantLock为JDK实现
 * 2、synchronized不能等待中断，ReentrantLock可中断（正在等待的线程放弃等待转而去处理其他的事务）
 * 3、synchronized为非公平锁，ReentrantLock默认非公平但可设置为公平（公平是指获得锁的顺序必须按照申请锁的顺序来执行）
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLockTest lockTest = new ReentrantLockTest();
        ReentrantLockTest lockTest2 = new ReentrantLockTest();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            lockTest.func();
        });

        executorService.execute(() -> {
            lockTest2.func();
        });
    }


    private Lock lock = new ReentrantLock();

    private void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + " " + Thread.currentThread().getName());
            }
        } finally {
            lock.unlock();
        }
    }
}
