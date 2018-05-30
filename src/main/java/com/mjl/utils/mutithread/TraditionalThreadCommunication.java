package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/23.
 * 子线程循环10次，接着回到主线程循环100次，又接着回到子线程循环10次，再接着回到主线程又循环100次，如次循环50次，请写出程序 ..
 */
public class TraditionalThreadCommunication {
    /**
     * @param args
     */
    public static void main(String[] args) {

        final Business business = new Business();
        new Thread(new Runnable() {

            @Override
            public void run() {
                //循环50次
                for (int i = 1; i <= 50; i++) {
                    //子线程循环10次
                    business.sub(i);
                }

            }
        }).start();
        //循环50次
        for (int i = 1; i <= 50; i++) {
            //主线程循环100次
            business.main(i);
        }

    }

}

/**
 *
 *
 在Java.lang.Thread类中，提供了sleep()，
 而java.lang.Object类中提供了wait()， notify()和notifyAll()方法来操作线程

 sleep()可以将一个线程睡眠，参数可以指定一个时间。
 wait()可以将一个线程挂起，直到超时或者该线程被唤醒。
 wait有两种形式wait()和wait(milliseconds).

 sleep和wait的区别有：

 1，这两个方法来自不同的类分别是Thread和Object

 2，最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。

 3，wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用

 synchronized(x){
 x.notify()
 //或者wait()
 }

 4,sleep必须捕获异常，而wait，notify和notifyAll不需要捕获异常(使用wait，需要捕捉异常)
 */
class Business {

    private volatile boolean bShouldSub = true;

    /**
     * 子线程循环10次
     * synchronized互斥
     * @param i
     */
    public synchronized void sub(int i) {
        //检查
        while (!bShouldSub) {
            try {
                this.wait();//wait方法释放了锁，使得其他线程可以使用同步控制块或者方法
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 1; j <= 10; j++) {
            System.out.println("[子线程]sub thread sequence of " + j + ",loop of " + i);
        }
        bShouldSub = false;
        this.notify();
    }

    /**
     * 主线程循环100次
     * synchronized互斥
     * @param i
     */
    public synchronized void main(int i) {
        while (bShouldSub) {
            try {
                this.wait();//wait方法释放了锁，使得其他线程可以使用同步控制块或者方法
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 1; j <= 100; j++) {
            System.out.println("[主线程]main thread sequence of " + j + ",loop of " + i);
        }
        bShouldSub = true;
        this.notify();
    }
}
