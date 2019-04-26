package com.mjl.utils.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * created in 2019/1/7.
 *
 * @author majiali
 * @desc
 */
public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        //initServer();
        initServerThread();
    }


    private static void initServer(){
        try {
            //1.创建一个服务器端的Socket,即ServerSocket，指定绑定的端口
            ServerSocket ss= new ServerSocket(8888);
            //2.调用accept方法开始监听，等待客户端的连接
            System.out.println("服务器即将启动，等待客户端的连接...");
            Socket so=ss.accept();//accept方法返回Socket实例
            //3.获取一个输入流，并读取客户端信息
            InputStream is=so.getInputStream();//字节输入流
            InputStreamReader isr=new InputStreamReader(is);//将字节输入流包装成字符输入流
            BufferedReader br=new BufferedReader(isr);//加上缓冲流，提高效率
            String info=null;
            while((info=br.readLine())!=null){//循环读取客户端信息
                System.out.println("我是服务器，客户端说："+info);

            }
            so.shutdownInput();//关闭输入流
            //4.获取一个输出流，向客户端输出信息,响应客户端的请求
            OutputStream os=so.getOutputStream();//字节输出流
            PrintWriter pw=new PrintWriter(os);//字符输出流
            BufferedWriter bw=new BufferedWriter(pw);//缓冲输出流
            bw.write("欢迎您！");
            bw.newLine();
            bw.flush();

            //5.关闭资源
            os.close();
            pw.close();
            bw.close();
            br.close();
            isr.close();
            is.close();
            so.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initServerThread(){
        try {
            //1.创建一个服务器端的Socket,即ServerSocket，指定绑定的端口
            ServerSocket ss= new ServerSocket(8888);

            System.out.println("服务器即将启动，等待客户端的连接...");
            Socket so=null;
            //记录客户端的数量
            int count=0;
            //循环侦听等待客户端的连接
            while(true){
                //2.调用accept方法开始监听，等待客户端的连接
                so=ss.accept();//accept方法返回Socket实例
                //创建一个新的线程
                ServerThread st=new ServerThread(so);
                //启动线程，执行与客户端的交互
                st.start();//注意是start不是run
                count++;
                System.out.println("此时客户端数量为："+count);
                InetAddress add=so.getInetAddress();
                System.out.println("当前客户端的ip地址为"+add.getHostAddress());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
