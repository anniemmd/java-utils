package com.mjl.utils.test.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * created in 2019/3/12.
 *
 * @author majiali
 * @desc
 */
public class Work implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(Work.class);

    private String name;
    private CountDownLatch countDownLatch;

    public Work(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        System.out.println("正在干活"+name);

        try {
            Thread.sleep(new Random().nextInt(10));

             System.out.println("活干完了"+ name);
             countDownLatch.countDown();
        } catch (Exception e) {
        }
    }
}
