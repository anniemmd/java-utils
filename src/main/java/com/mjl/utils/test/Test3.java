package com.mjl.utils.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * created in 2019/2/21.
 *
 * @author majiali
 * @desc
 */
public class Test3 {
    private static Logger logger = LoggerFactory.getLogger(Test3.class);

    public static void main(String[] args) {
        String content = "MengUer";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        int ch;
        try {
            while ((ch = inputStream.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String,Integer> hashMap = new HashMap<>(12);
        List list;
        System.out.println("\n" + "-------华丽的分割线------");
        try {
            // 注意！
            inputStream.reset();
            while ((ch = inputStream.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
