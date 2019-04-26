package com.mjl.utils.test;

/**
 * Created by majiali on 2018/11/1.
 */
public class Test2 {

    static int count=0;

    /**
     * 给类加锁？
     */
    public synchronized static void f1(){
        count++;
    }

    /**
     * 给对象加锁？
     */
    public synchronized void f2(){
        count++;
    }

    /**
     * 给对象加锁？
     */
    public void f3(){
        synchronized (this){
            count++;
        }
    }


    public static void main(String[] args) {
//        for(int i=0; i<10; i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    f1();
//                }
//            });
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    f2();
//                }
//            });
//        }
//
//        System.out.println(count);


    }
}
