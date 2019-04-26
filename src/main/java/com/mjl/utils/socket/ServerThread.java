package com.mjl.utils.socket;

import java.io.*;
import java.net.Socket;

/**
 * created in 2019/1/7.
 *
 * @author majiali
 * @desc
 */
/*
 * 服务器线程处理类
 */
public class ServerThread extends Thread {
    // 和本线程相关的Socket
    Socket so = null;

    public ServerThread(Socket socket) {// 初始化与本线程相关的Socket
        so = socket;
    }

    // 线程执行的操作，响应客户端的请求
    @Override
    public void run() {// 重写父类的run方法
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        BufferedWriter bw = null;
        try {
            // 3.获取一个输入流，并读取客户端信息
            is = so.getInputStream();// 字节输入流
            isr = new InputStreamReader(is);// 将字节输入流包装成字符输入流
            br = new BufferedReader(isr);// 加上缓冲流，提高效率
            String info = null;
            while ((info = br.readLine()) != null) {// 循环读取客户端信息
                System.out.println("我是服务器，客户端说：" + info);

            }
            so.shutdownInput();// 关闭输入流
            // 4.获取一个输出流，向客户端输出信息,响应客户端的请求
            os = so.getOutputStream();// 字节输出流
            pw = new PrintWriter(os);// 字符输出流
            bw = new BufferedWriter(pw);// 缓冲输出流
            bw.write("欢迎您！");
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 5.关闭资源
            try {
                if (os != null) {
                    os.close();
                }
                if (pw != null) {
                    pw.close();
                }
                if (bw != null) {
                    bw.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (!so.isClosed()) {
                    so.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
