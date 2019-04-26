package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 写一个死锁
 */
public class M5 {
     static String a = "1";
     static String b = "2";

    public static void main(String[] args) {
        t1.start();
        t2.start();

    }

   static Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized(M5.a){
                try {
                    System.out.println("ttt1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (M5.b){
                    System.out.println("11111");
                }
            }
        }
    });
    static Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (M5.b){
                try {
                    System.out.println("ttt2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (M5.a){
                    System.out.println("22222");
                }
            }
        }
    });

}
