package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 线程通信：
 * 用两个线程分别打印0-100之间的奇偶数。
 * 比如 有A，B两个线程；
 * A线程打印1，3，5，7，9 … 99
 * B线程打印0，2，4，6，8 … 100
 */
public class M7_1 {
    private static volatile int num =0;
    private static volatile Boolean flag = true;
    private static int total =100;

    public static void main(String[] args) {
         Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (num <= total){
                    if(flag==true){
                        System.out.println(Thread.currentThread().getName() +":" + num++);
                    }
                    flag = false;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"B");

         Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (num <= total){
                    if(flag == false){
                        System.out.println(Thread.currentThread().getName() + ":" + num++);
                    }
                    flag = true;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"A");
        a.start();
        b.start();
    }

}
