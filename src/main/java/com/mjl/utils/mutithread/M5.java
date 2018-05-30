package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 写一个死锁
 */
public class M5 {
     static String a = "1";
     static String b = "2";

    public static void main(String[] args) {
        Thread a = new Thread(new A());
        Thread b = new Thread(new B());
        a.start();
        b.start();
    }

   static  class A implements Runnable{
         @Override
         public void run() {
            synchronized(M5.a){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (M5.b){
                    System.out.println("11111");
                }
            }
         }
     }

   static  class B implements Runnable{

         @Override
         public void run() {
             synchronized (M5.b){
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 synchronized (M5.a){
                     System.out.println("22222");
                 }
             }
         }
     }

}
