package szu.panda;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Winnie
 * @Date: 2019-07-17
 * @Description:
 */
public class ThreadPoolTest {
    public static ThreadLocal<String> value = new ThreadLocal<String>();

    public static void main(String args[]) {
        value.set("I am main");
        ThreadPoolTest.ThreadPoolTest(value);
        System.out.println(Thread.currentThread().toString() + ":" + value.get());
    }

    public static void ThreadPoolTest(ThreadLocal threadLocal) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        threadPoolExecutor.execute(() -> {
                    value.set("1");
                    System.out.println(Thread.currentThread().toString() + ":" + value.get());
                }
        );
        threadPoolExecutor.execute(() -> {
                    value.set("2");
                    System.out.println(Thread.currentThread().toString() + ":" + value.get());
                }
        );
        threadPoolExecutor.execute(() -> {
                    value.set("3");
                    System.out.println(Thread.currentThread().toString() + ":" + value.get());
                }
        );
    }
}