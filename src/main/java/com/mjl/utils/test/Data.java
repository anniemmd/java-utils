package com.mjl.utils.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created in 2019/3/29.
 *
 * @author majiali
 * @desc
 */
public class Data {
    private static Logger logger = LoggerFactory.getLogger(Data.class);


    private String a;
    private String b;



    public void test(){
        this.a = "123";
        b = "234";
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
