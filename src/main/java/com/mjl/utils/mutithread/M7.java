package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 线程通信
 * 用两个线程分别打印0-100之间的奇偶数。
     比如 有A，B两个线程；
     A线程打印1，3，5，7，9 … 99
     B线程打印0，2，4，6，8 … 100
 */
public class M7 {

    public static void main(String[] args) {
        Thread a = new Thread(new A());
        Thread b = new Thread(new B());
        a.start();
        b.start();
    }

    static class A implements Runnable{

        @Override
        public synchronized void run() {
            printA();
        }

        public void printA(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0 ; i<100 ; i+=2){
                System.out.println(Thread.currentThread().getName()+":" + i);
                notify();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class B implements Runnable{

        @Override
        public void run() {
            printB();
        }

        public void printB(){

            for(int i=1 ; i<100 ; i+=2){
                try {
                    wait();
                    System.out.println(Thread.currentThread().getName()+":" + i);
                    notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
