package com.mjl.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.rmi.UnexpectedException;
import java.util.StringJoiner;

/**
 * Created by majiali on 2018/5/15.
 *
 * 请求相关操作整理
 */
@Component
public class RestTemplateUtils {
    @Autowired
    private RestTemplate restTemplate;
    private static Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static String requestURI ="api";

    /**
     * 带请求头的请求，exchange万能请求
     * 构造请求参数，请求头
     */
    public void sendWithHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Encrypt-Device","1");
        String payload = null;
        HttpEntity<String> entity = new HttpEntity<String>(payload,headers);

        StringJoiner queryString = new StringJoiner("&");
        queryString.add("offset=" + 10);
        queryString.add("size=" + 10);
        queryString.add("timestamp=" + System.currentTimeMillis());

        ResponseEntity<String> resp = restTemplate.exchange("http://{host}/" + requestURI + '?' + queryString, HttpMethod.POST, entity, String.class, "host");

    }

    /**
     * 不带请求头的请求 exchange
     * @param comment
     * @throws UnexpectedException
     */
    public void sendWithoutHeaderV1(Comment comment) throws UnexpectedException {
        HttpEntity<Comment> requestEntity = new HttpEntity<>(comment);
        ResponseEntity<Result> result = restTemplate.exchange("http://{host}/api/xx?timestamp={timestamp}", HttpMethod.PUT, requestEntity, Result.class, "host", System.currentTimeMillis());
        logger.debug("Finished update vote comment {}, result {}", comment, result);
    }

    /**
     * 不带请求头的请求 postForObject
     * @param comment
     * @throws UnexpectedException
     */
    public void sendWithoutHeaderv2(Comment comment){
        Result result = restTemplate
                .postForObject("http://{host}/api/xx?timestamp={timestamp}", comment, Result.class, "host", System.currentTimeMillis());
        logger.debug("Finished text comment request, result {}", result);
    }


    public static class Comment{

    }
    public static class Result{

    }
}
