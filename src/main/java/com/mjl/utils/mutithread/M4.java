package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 * 写一个死锁
 */
public class M4 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new A());
        Thread t2 = new Thread(new B());
        t1.start();
        t2.start();
    }

    static class A implements Runnable{

        public synchronized static void doSomething(){
            System.out.println("a doing");
        }

        public synchronized  static  void getA(){
            System.out.println("A");
        }

        @Override
        public void run() {
            doSomething();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            B.getB();
        }
    }

    static class B implements Runnable{
        public synchronized static void doSomething(){

            System.out.println("b doing");
        }

        public synchronized  static void getB(){
            System.out.println("B");
        }

        @Override
        public void run() {
            doSomething();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            A.getA();
        }
    }
}
