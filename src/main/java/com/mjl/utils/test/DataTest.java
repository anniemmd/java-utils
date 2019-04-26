package com.mjl.utils.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created in 2019/3/29.
 *
 * @author majiali
 * @desc
 */
public class DataTest {
    private static Logger logger = LoggerFactory.getLogger(DataTest.class);

    public static void main(String[] args) {
        Data data = new Data();
        data.setA("qqq");
        data.setB("bbb");
        data.test();
        System.out.println(data.getA()+"--"+data.getB());
    }
}
