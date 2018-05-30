package com.mjl.utils.mutithread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by majiali on 2018/5/30.
 * 写一个生产者与消费者
 */
public class M6 {
    static BlockingQueue<Data> queue = new ArrayBlockingQueue<Data>(10);
    private static volatile AtomicInteger id = new AtomicInteger(1);


    static class Data{
        public Data(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class Producer implements Runnable{
        private BlockingQueue<Data> queue;
        public Producer(BlockingQueue<Data> queue){
            this.queue = queue;
        }
        private volatile Boolean flage = true;

        public void put(){
            while (flage){
                Data data = new Data(M6.id.incrementAndGet(), Thread.currentThread().getName());
                try {
                    queue.add(data);
                    System.out.printf("producer one data ,id is %d, name is %s%n", data.getId(), data.getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop(){
            flage = false;
        }

        @Override
        public void run() {
            put();
        }
    }

    static class Consumer implements Runnable{
        private BlockingQueue<Data> queue;
        public Consumer(BlockingQueue<Data> queue){
            this.queue = queue;
        }

        private volatile Boolean flag = true;

        public void take(){
            while (flag){
                try {
                    Data da = queue.take();
                    System.out.printf("consumer take the data id is %d, name is %s%n", da.getId(),da.getName());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop(){
            flag = false;
        }

        @Override
        public void run() {
            take();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);

        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);

        executorService.execute(producer1);
        executorService.execute(consumer1);
        executorService.execute(producer2);
        executorService.execute(consumer2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer1.stop();
        producer2.stop();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        consumer1.stop();
        consumer2.stop();
        executorService.shutdown();
    }
}
