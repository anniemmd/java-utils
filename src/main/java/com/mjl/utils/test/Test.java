package com.mjl.utils.test;

import java.util.concurrent.*;

/**
 * Created by majiali on 2018/8/2.
 */
public class Test {

    Executors e;
    Executor executor;
    ThreadPoolExecutor t;
    ThreadFactory threadFactory;
    ThreadLocal threadLocal;
    private final static LinkedBlockingQueue<Integer> QUEUE = new LinkedBlockingQueue<>(1000);

    public static void main(String[] args) throws Exception {
        ExecutorService exe = Executors.newFixedThreadPool(1);
        exe.execute(new Task());
        //exe.submit(new Task());
    }
        public static class Task implements Runnable {
            @Override
            public void run() {
                System.out.println(Integer.parseInt("123"));
                System.out.println(Integer.parseInt("XYZ"));//这里会抛RuntimeException
            }
        }

}
