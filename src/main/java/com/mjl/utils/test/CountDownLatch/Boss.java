package com.mjl.utils.test.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created in 2019/3/12.
 *
 * @author majiali
 * @desc
 */
public class Boss implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(Boss.class);

    private String name;
    private CountDownLatch countDownLatch;

    public Boss(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        System.out.println("等待检查"+name);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
        }
        System.out.println("开始检查"+ name);

    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Boss boss = new Boss("老板", countDownLatch);

        Work work1 = new Work("工人1", countDownLatch);
        Work work2 = new Work("工人2", countDownLatch);
        Work work3 = new Work("工人3", countDownLatch);


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(work1);
        executorService.execute(work2);
        executorService.execute(work3);
        executorService.execute(boss);
        //????为什么运行完了还没终止，在等什么吗，线程池的线程需要停掉吗
        executorService.shutdown();
    }
}
