package com.mjl.utils.basejava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majiali on 2018/5/30.
 */
public class JVM {
   static List<Byte[]> list = new ArrayList<>();

    public static void inc(){
        for(int i=0; i <100000; i++){
            Byte[] b = new Byte[512];
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(b);
            System.out.println(i++);
        }
    }

    public static void main(String[] args) {
        inc();
    }
}
