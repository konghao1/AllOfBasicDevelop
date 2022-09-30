package concurrentTrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 相对线程安全，添加synchronized方法块解决IndexOutOfBoundsException问题
 */
public class UnsafeExampleCollection {

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        List<Integer> synchronizedList = Collections.synchronizedList(UnsafeExampleCollection.list);
        while (true) {
            for (int i = 0; i < 10; i++) {
                synchronizedList.add(i);
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(() -> {

                synchronized (synchronizedList) {
                    for (int i = 0; i < synchronizedList.size(); i++) {
                        System.out.println(synchronizedList.remove(i));
                    }
                }
            });
            executorService.execute(() -> {
                synchronized (synchronizedList) {
                    for (int i = 0; i < synchronizedList.size(); i++) {
                        System.out.println(synchronizedList.get(i));
                    }
                }
            });
            executorService.shutdown();
        }
    }
}
