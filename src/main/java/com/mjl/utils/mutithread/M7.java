package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 线程通信：
 * 用两个线程分别打印0-100之间的奇偶数。
 * 比如 有A，B两个线程；
 * A线程打印1，3，5，7，9 … 99
 * B线程打印0，2，4，6，8 … 100
 */
public class M7 {
    private static Object obj = new Object();

    public static void main(String[] args) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (M7.obj){
                for(int i=0; i<100;i+=2){
                        System.out.println(Thread.currentThread().getName()+":"+i);
                    try {
                        notify();
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(M7.obj){
                for (int i=1; i<100; i+=2){
                    try {
                        System.out.println(Thread.currentThread().getName() +":" + i);
                        notify();
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
            }
        });


        a.start();
        b.start();



    }

}
