package com.mjl.utils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majiali on 2018/5/15.
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 管道获取每个话题的评论数量
     * @param types
     * @param topicIds
     * @return
     */
    private Map<String,Long> zMultGetTopicsCount(String keystr, List<Integer> types, List<String> topicIds){
        Map<String,Long> map = new HashMap<>();
        List<Object> entries = (List<Object>)(redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection con = (StringRedisConnection) connection;
                for(String str : topicIds) {
                    for(Integer type : types){
                        String key = keystr;
                        con.zCount(key, Double.MIN_VALUE ,Double.MAX_VALUE);
                    }
                }
                return null;
            }
        }));

        int i = 0;
        for(String topicId : topicIds){
            for(Integer type : types){
                Long count = entries.get(i)== null? 0L : (Long)entries.get(i);
                map.put(topicId+":"+type, count);
                i++;
            }
        }
        return map;
    }
}
