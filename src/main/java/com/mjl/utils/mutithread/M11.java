package com.mjl.utils.mutithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by majiali on 2018/5/31.
 * 信号量的使用
 * 10个资源，100个人去占用，避免并发
 */
public class M11 {
     static Semaphore semaphore = new Semaphore(10,true);
     static List<Boolean> resources = new ArrayList<>();
     static ReentrantLock lock = new ReentrantLock(true);

    static class Manage{
        static {
            for(int i=0; i<10; i++){
                resources.set(i,true);
            }
        }

        public static Integer getResourceId(){
            int id =-1;
            lock.lock();
            for(int i=0; i<10;i++){
                if(resources.get(i) == false){
                    resources.set(i, true);
                   id = i;
                }
            }
            lock.unlock();
            return id;
        }
    }


    static class User implements Runnable{

        @Override
        public void run() {
            Integer resourceId;
            try {
                semaphore.acquire();
                resourceId = Manage.getResourceId();
                System.out.printf("%s start to user the %d resource", Thread.currentThread().getName(), resourceId);

            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
                System.out.printf("%s end to user the %d resource", Thread.currentThread().getName(), resourceId);
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++){
            Thread t = new Thread(new User());
            t.setName("name" + i);
            t.start();
        }
    }
}
