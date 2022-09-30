package concurrentTrain;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 并发问题出现的三个要素：
 * 可见性；CPU缓存引起，在一个线程在CPU1更新值之后尚未写到主存上时，另一个线程在CPU2上读不到线程1更新的值
 * 原子性：由于CPU的分时复用（线程切换）导致不同的线程执行指令间出现了线程切换，值会出现不准确
 * 有序性：指令重排序
 */
public class UnsafeExample {

    /**
     * 1000个线程同时对count进行自增操作，出现线程不安全
     */
    public static void main(String[] args) {
        final int threadSize = 1000;

        ThreadUnsafeExample unsafeExample = new ThreadUnsafeExample();

        final CountDownLatch latch = new CountDownLatch(threadSize);

        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            long start = System.currentTimeMillis();
            for (int i = 0; i < threadSize; i++) {
                executorService.execute(() -> {
                    unsafeExample.add();
                    latch.countDown();
                });
            }
            latch.await();
            executorService.shutdown();
            System.out.println(unsafeExample.getCount());
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - start) + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


class ThreadUnsafeExample {
    public int count = 0;

    public void add() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
