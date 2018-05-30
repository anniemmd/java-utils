package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 */
public class M1 {

    public static void main(String[] args){
        Thread a = new Thread(new A());
        a.start();
        Thread b = new Thread(new B());
        b.start();
        System.out.printf("000,%s%n", Thread.currentThread().getName());
    }

    static class A extends Thread{
        @Override
        public void run(){
            System.out.printf("111,%s%n", Thread.currentThread().getName());
        }
    }

    static class B implements Runnable{

        @Override
        public void run() {
            System.out.printf("222,%s%n", Thread.currentThread().getName());
        }
    }
}
