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
    private static int i =0;
    private static int total = 100;

    public static void main(String[] args) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                    while(i<=total){
                        synchronized (M7.obj){
                            System.out.println(Thread.currentThread().getName()+":"+ i++);
                            try {
                                obj.notify();
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        },"B");

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                    while (i<=total){
                        synchronized(M7.obj){
                            try {
                                System.out.println(Thread.currentThread().getName() +":" + i++);
                                obj.notify();
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }

                }
            }
        }, "A");


        a.start();
        b.start();



    }

}
