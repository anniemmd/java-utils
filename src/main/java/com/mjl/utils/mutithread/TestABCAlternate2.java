package com.mjl.utils.mutithread;

import java.util.concurrent.Semaphore;

/**
 * Created by majiali on 2018/5/23.
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，
 * 要求输出的结果必须按顺序显示。如：ABCABCABC…… 依次递归
 *
 * 方法一:利用Semaphore类来实现
 */
public class TestABCAlternate2 {
    private Semaphore s1 = new Semaphore(0);//用于A线程
    private Semaphore s2 = new Semaphore(0);//用于B线程
    private Semaphore s3 = new Semaphore(0);//用于C线程

    public Thread a;
    public Thread b;
    public Thread c;

    public TestABCAlternate2(){
        a = new Thread() {
            public void run() {

                try {
                    s3.release();//释放C的许可
                    int i = 0;//计数器
                    while(i<10){
                        i++;
                        s3.acquire();//获得C输出线程的许可,将线程阻塞
                        System.out.print("A");//打印输出
                        s1.release();//把输出B的许可释放
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        b = new Thread(){
            public void run() {
                try {
                    int i=0;
                    while(i<10){
                        i++;
                        s1.acquire();//获得A输出线程的许可，将其阻塞
                        System.out.print("B");//
                        s2.release();//释放输出C的许可
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        c = new Thread(){
            public void run(){
                try {
                    int i=0;
                    while(i<10){
                        i++;
                        s2.acquire();//获得输出B线程的许可，将其阻塞
                        System.out.print("C");
                        s3.release();//释放输出A的许可
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//
            }
        };
    }

    public void printStar(){
        a.start();
        b.start();
        c.start();
    }
    public static void main(String[] args) {
        TestABCAlternate2 test = new TestABCAlternate2();
        test.printStar();
    }
}
