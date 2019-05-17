package com.mjl.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by majiali on 2018/5/15.
 * 文件读取相关操作整理
 */
@Component
public class FileUtils {
    @Autowired
    private JsonUtil jsonUtil;
    private static long lastDomainModifyTime = -1;
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private  Map<String, String> countryMap;

    /**
     * 读取本地文件
     * @param filePath
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> readLocalFile(String filePath, Class<T> clazz){
        File file = new File(filePath);
        List<T> records = new ArrayList<>();
        if(file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                while((lineContent = br.readLine())!=null){
                    records.add(jsonUtil.fromJson(lineContent,clazz));
                }
                br.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return records;
    }

    /**
     * 读取远程文件
     * @param fileUrl
     * @return
     */
    public String readRemoteFile(String fileUrl){
        String data = "";
        try {
            URL oracle = new URL(fileUrl);
            URLConnection connection = oracle.openConnection();
            connection.setUseCaches(false);
            long lastModify = connection.getLastModified();
            if (lastDomainModifyTime != -1 && lastDomainModifyTime == lastModify) {
                logger.info("file has no change, lastmodify time is {}", lastModify);
                return data;
            }
            lastDomainModifyTime = lastModify;

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                data += inputLine;
            }
            in.close();
            logger.info("content is [{}]", data);
        } catch (Exception e) {
            logger.error("read failed", e);
        }
        return data;
    }

    /**
     * 读取大文件
     * @param fileUrl
     * @return
     */
    public String readBigFile(String fileUrl){
        StringBuilder builder = null;
        if (fileUrl.contains("size-")) {
            int size = Integer.parseInt(fileUrl.split("-")[1]);
            builder = new StringBuilder(size);
        } else {
            builder = new StringBuilder(1000000);
        }
        try {
            URL url = new URL(fileUrl);
            InputStream input = url.openConnection().getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(input), "UTF-8"));
            char[] buffer = new char[8192];
            int count = 0;
            while ((count = br.read(buffer)) != -1) {
                builder.append(buffer, 0, count);
            }
            br.close();
        } catch (Exception e) {
            logger.error("read failed" + e);
        }

        return builder.toString();
    }

    /**
     * 读取properties文件
     */
    public void readPropertiesFile(){
        Map<String, String> map = new HashMap<String, String>();
        Properties prop = new Properties();
        try {
            prop.load(FileUtils.class.getClassLoader().getResourceAsStream("country.properties"));
            Set<Object> keys = prop.keySet();// 返回属性key的集合
            for (Object key : keys) {
                map.put(key.toString(), prop.get(key).toString());
            }
            countryMap = map;
        } catch (IOException e) {
            logger.error("countryMap init failed" + e);
        }
    }

    /**
     * 根据路径查找文件
     * @param path
     */
    public void getFilesByPath(String path){
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList == null){
            return ;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
    }

    /**
     * 移除指定路径文件
     * @param filePaths
     */
    public void removeFile(List<String> filePaths){
        for(String filePath : filePaths){
            File file = new File(filePath);
            if(file.exists()){
                file.delete();
            }
        }
    }

    /**
     * 向文件追加字符 true
     * 替换 false
     * @throws IOException
     */
    public static void addToFile(String text) throws IOException {
        File b = new File("/data/text.txt");
        FileWriter fw = new FileWriter(b, true);
        fw.write(text);
        fw.close();
    }

    private static Set<String> read(String path){
        File file = new File(path);
        Set<String> records = new HashSet<>();
        if(file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                while((lineContent = br.readLine())!=null){
                    records.add(lineContent);
                }
                br.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return records;
    }

    /**
     * 读用户id
     */
    private static void readUserId(){
        try {
            //读取某个标签下的用户id集合
            StringBuilder text = new StringBuilder();
            Set<String> userIds = read("/Users/majiali/Desktop/user_data.txt");

            //插入标签详情数据
            Integer labelId= 2;
            int index = 129;
            for(String userId : userIds){
                text.append(String.format("(%d, %d, %d, '2019-05-16 15:35:18', '2019-05-16 15:35:18'),\n", index++, labelId, Long.parseLong(userId)));
            }

            //写入文件
            addToFile(text.toString());
        } catch (IOException e) {
        }
    }

    /**
     * 读渠道号
     */
    private static void readChannelCode(){
        try {
            //读取某个标签下的用户id集合
            StringBuilder text = new StringBuilder();
            Set<String> channelCodes = read("/Users/majiali/Desktop/channel_data.txt");

            //插入标签详情数据
            Integer labelId= 2;
            int index = 55;
            for(String channelCode : channelCodes){
                text.append(String.format("(%d, %s, %d, 0, 1, '2018-05-31 15:35:18', '2018-05-31 15:35:18'),\n", index++, "'"+channelCode+"'", labelId));
            }

            //写入文件
            addToFile(text.toString());
        } catch (IOException e) {
        }
    }



    public static void main(String[] args) {
        readChannelCode();
    }


}
