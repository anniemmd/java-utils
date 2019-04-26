package com.mjl.utils.test.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * created in 2019/3/13.
 *
 * @author majiali
 * @desc
 */
public class TestSemaphore {
    private static Logger logger = LoggerFactory.getLogger(TestSemaphore.class);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        for(int i=0; i<20;i++){
            int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println( no + "获取资源");
                        Thread.sleep(5000);
                        semaphore.release();
                    } catch (InterruptedException e) {
                    }

                }
            };

            executorService.execute(runnable);
        }

        executorService.shutdown();
    }
}
