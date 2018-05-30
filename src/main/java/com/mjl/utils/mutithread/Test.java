package com.mjl.utils.mutithread;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by majiali on 2018/5/23.
 *  线程1的功能就是输出1，线程2的功能就是输出2，
 以此类推.........
 现在有四个文件A B C D,
 初始都为空。现要让四个文件呈如下格式：
 A：1 2 3 4 1 2....
 B：2 3 4 1 2 3....
 C：3 4 1 2 3 4....
 D：4 1 2 3 4 1....
 */
public class Test {

    public static void main(String[] args) {
        FileWriteUtil util = new FileWriteUtil();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new WriteRunnable(util, 1, '1'));
        service.execute(new WriteRunnable(util, 2, '2'));
        service.execute(new WriteRunnable(util, 3, '3'));
        service.execute(new WriteRunnable(util, 4, '4'));
        service.shutdown();

        //new Thread(new WriteRunnable(util, 1, '1')).start();
        //new Thread(new WriteRunnable(util, 2, '2')).start();
        //new Thread(new WriteRunnable(util, 3, '3')).start();
        //new Thread(new WriteRunnable(util, 4, '4')).start();
    }

  static class WriteRunnable implements Runnable {
        private final FileWriteUtil util;
        private int threadNum;
        private char value;

        /**
         * @param util      写文件工具类
         * @param threadNum 线程号
         * @param value     写的字符
         */
        public WriteRunnable(FileWriteUtil util, int threadNum, char value) {
            this.util = util;
            this.threadNum = threadNum;
            this.value = value;
        }

        public void run() {
        /*
         * 假设循环6次，一直循环可以使用while(true)或者for(;;)
         */
            for (int i = 0; i < 6; i++) {
                synchronized (util) {
                    while (threadNum != util.getCurrentThreadNum()) {
                        try {
                            util.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    util.write(value, threadNum);
                    util.notifyAll();
                }
            }
        }
    }

   static class FileWriteUtil {
        private int currentThreadNum = 1;
        /**
         * 记录将字符写入文件的次数
         */
        private int count = 0;

        private String currentFileName;

        public void write(char value, int threadNum) {
            getCurrentFileName();
            FileWriter writer = null;
            try {
                //生成文件位置
                writer = new FileWriter("/Users/majiali/Downloads/" + currentFileName + ".txt", true);
                writer.write(value + " ");
                System.out.printf(
                        "ThreadNum=%d is executing. %c is written into file file%s.txt \n",
                        currentThreadNum, value, currentFileName);
                writer.flush();
                //System.out.println(count);//
                count++;
                currentThreadNum = threadNum;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            getNextThreadNum();
        }

        public int getCurrentThreadNum() {
            return currentThreadNum;
        }

        public void setCurrentThreadNum(int currentThreadNum) {
            this.currentThreadNum = currentThreadNum;
        }

        /**
         * 根据写的次数，判断该写哪个文件了？A，B,C,D.
         */
        private void getCurrentFileName() {
            int temp = count % 4;
            switch (temp) {
                case 0:
                    currentFileName = "A";
                    break;
                case 1:
                    currentFileName = "B";
                    break;
                case 2:
                    currentFileName = "C";
                    break;
                case 3:
                    currentFileName = "D";
                    break;
                default:
                    currentFileName = "E";
            }
        }

        private void getNextThreadNum() {
            if (count % 4 == 0) {
                if (currentThreadNum < 3) {
                    currentThreadNum += 2;
                } else {
                    currentThreadNum = (currentThreadNum + 2) % 4;
                }
            } else {
                if (currentThreadNum == 4) {
                    currentThreadNum = 1;
                } else {
                    currentThreadNum++;
                }
            }
        }
    }
}
