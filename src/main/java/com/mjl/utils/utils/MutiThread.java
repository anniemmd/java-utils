package com.mjl.utils.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by majiali on 2018/5/15.
 * 多线程示例，指定线程数，异步等待结果
 */
public class MutiThread {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static Logger logger = LoggerFactory.getLogger(MutiThread.class);

    public List<ResultFile> mutiUpload(final MultipartFile[] files) {
        List<ResultFile> resultFiles = new ArrayList<ResultFile>();
        List<Future<ResultFile>> futures = new ArrayList<>();
        for (final MultipartFile file : files) {
            futures.add(ThreadPoolTaskExecutor.getInstance().getService().submit(new Callable<ResultFile>() {
                @Override
                public ResultFile call() throws Exception {
                    byte[] fileData = file.getBytes();
                    try {
                        if (file.getSize() > 10 * 1024 * 1024) {
                            logger.error("creative too big");
                            return null;
                        }
                        String result = restTemplate.postForObject("url", fileData, String.class, "host", System.currentTimeMillis());

                        return objectMapper.readValue(result, ResultFile.class);
                    } catch (IOException e) {
                        logger.error("uploadCreatives faild", e);
                        return null;
                    }
                }
            }));
        }

        for (Future<ResultFile> future : futures) {
            try {
                if (future != null) {
                    resultFiles.add(future.get());
                }
            } catch (Exception e) {
                logger.error("素材批量上传异常" + e);
            }

        }

        return resultFiles;
    }


    public static class ResultFile {

    }

    public static class ThreadPoolTaskExecutor {
        private static final ThreadPoolTaskExecutor INSTANCE = new ThreadPoolTaskExecutor();

        private final ExecutorService service;

        public static ThreadPoolTaskExecutor getInstance() {
            return INSTANCE;
        }

        private ThreadPoolTaskExecutor() {
            service = Executors.newFixedThreadPool(10);
        }

        public ExecutorService getService() {
            return service;
        }
    }
}
