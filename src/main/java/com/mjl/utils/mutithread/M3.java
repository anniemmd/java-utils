package com.mjl.utils.mutithread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by majiali on 2018/5/30.
 *
 * 生成自增id
 */
public class M3 {
    public static void main(String[] args) {
        Thread[] threads = new Thread[100];
        int i = 1;
        for (Thread thread: threads){
            thread = new M();
            thread.setName("Thead"+ i++);
            thread.start();
        }
    }

    static class A{
        static Integer total=100 ;
        static Integer count =1;
        static AtomicInteger incCount= new AtomicInteger(1);

        public synchronized static Integer getId(){
                if(total -count > 0){
                    count ++;
                }else {
                    count =1;
                }
                System.out.println(Thread.currentThread().getName()+"--"+count);
                return count;
        }

        public static Integer getIncId(){
            if(incCount.intValue() < total){
                incCount.incrementAndGet();
            }
            System.out.println(incCount.intValue());
            return incCount.intValue();
        }

    }


    static class M extends Thread{

        @Override
        public void run() {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //A.getId();
            A.getIncId();
        }
    }


}
