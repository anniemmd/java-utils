package com.mjl.utils.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

/**
 * created in 2019/3/11.
 *
 * @author majiali
 * @desc
 */
public class Test4 {
    private static Logger logger = LoggerFactory.getLogger(Test4.class);

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
        System.out.println(sdf.format(new AtomicLong(System.currentTimeMillis()).get()));

        System.out.println(sdf.format(System.currentTimeMillis()));


    }

}
