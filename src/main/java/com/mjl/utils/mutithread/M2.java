package com.mjl.utils.mutithread;

/**
 * Created by majiali on 2018/5/30.
 */
public class M2 {
    public static void main(String[] args) {
        for(String url:args){
            Thread t = new Thread(new FileUpload(url));
            t.start();
        }
    }

    static class FileUpload implements Runnable{
        private String url;

        public FileUpload(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            upload(url);
            System.out.printf("文件已上传，%s%n", Thread.currentThread().getName());
        }

        private void upload(String url){

        }
    }
}
