package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/31.
 * join 练习
 */
public class M9 {

    static class Mut implements Runnable{

        @Override
        public void run() {
            for(int i=0; i<10; i++){
                System.out.print(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        System.out.println("start");
        Thread t = new Thread(new Mut());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");

    }
}
