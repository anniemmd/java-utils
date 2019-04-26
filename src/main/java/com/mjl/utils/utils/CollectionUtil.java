package com.mjl.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * created in 2019/4/26.
 *
 * @author majiali
 * @desc
 */
public class CollectionUtil {
    private static Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 把列表分成N份（子列表为快照列表）
     *
     * @param list                      列表
     * @param elementsCountPerChildList 每个子列表里包含的元素数量
     * @param <T>                       泛型
     * @return N份列表
     */
    public static <T> List<List<T>> subList(List<T> list, int elementsCountPerChildList) {
        if (CollectionUtils.isEmpty(list) || elementsCountPerChildList <= 0) {
            return new ArrayList<>(0);
        }

        if (elementsCountPerChildList >= list.size()) {
            List<List<T>> bufferList = new ArrayList<>(1);
            bufferList.add(list);
            return bufferList;
        }

        List<List<T>> bufferList = new ArrayList<>();
        int fromIndex = 0;
        int toIndex = elementsCountPerChildList;
        while (true) {
            // 防止越界
            toIndex = Math.min(toIndex, list.size());

            List<T> child = list.subList(fromIndex, toIndex);
            if (CollectionUtils.isEmpty(child)) {
                break;
            }

            bufferList.add(child);
            fromIndex = toIndex;
            toIndex = toIndex + elementsCountPerChildList;
        }
        return bufferList;
    }
}
