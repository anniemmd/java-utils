package com.mjl.utils.mutithread;

/**
 * 编写一个算法实现倒序输出一个字符串，比如输入hello world，输出dlrow ollel.
 */
public class M8 {

    public static void main(String[] args) {
        String s= "hello world";
        StringBuilder result = new StringBuilder(s);
        System.out.println("1:" + result.reverse().toString());

        result = new StringBuilder();
        for(int i=s.length()-1; i >= 0; i--){
            result.append(s.charAt(i));
        }
        System.out.println(result);

    }
}
