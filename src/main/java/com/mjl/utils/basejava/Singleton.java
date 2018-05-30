package com.mjl.utils.basejava;

/**
 * Created by majiali on 2018/5/30.
 * 写一个单例
 */
public class Singleton {

    private Singleton(){}

    public static class SingletonHold{
        private static Singleton instance = new Singleton();
    }

    public Singleton getInstance(){
        return SingletonHold.instance;
    }
}
