package concurrentTrain;

/**
 * @author kongh
 * @date 2022/10/4
 */
public class SingletonDoubleCheckExample {
    /**
     * volatile可以禁止JVM对指令进行重排序
     * <p>
     * 在new SingletonDoubleCheckExample()时，分了三个步骤：
     * 1、为example分配内存空间
     * 2、初始化example对象的值
     * 3、example 指向分配的内存地址
     * 其中指令的排序可能为1 -- 3 -- 2,
     * 加入volatile保证为1 -- 2-- 3顺序执行
     */
    private volatile static SingletonDoubleCheckExample example;

    private SingletonDoubleCheckExample() {
    }

    public static SingletonDoubleCheckExample getInstance() {
        //检查单例对象是否已经创建好了，如果== null，则进行创建，并在创建过程中加类锁
        if (example == null) {
            synchronized (SingletonDoubleCheckExample.class) {
                //第二次if判断目的是避免在一个线程创建单例对象的过程中，存在其他线程通过了第一次的非null判断进入创建单例对象过程
                //当这个线程释放锁的时候，其他线程并不知道单例对象已经创建出来，而再次创建。
                if (example == null) {
                    example = new SingletonDoubleCheckExample();
                }
            }
        }
        return example;
    }
}
