package com.mjl.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 */
@Component
public class JsonUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 将实体序列化为json字符串
	 *
	 * @param obj
	 * @return
	 */
	public String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("Error while serializing object to json.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将实体序列化为格式化的json字符串
	 *
	 * @param obj
	 * @return
	 */
	public String toPrettyJson(Object obj) {
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("Error while serializing object to pretty json.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将实体序列化为字节json
	 *
	 * @param obj
	 * @return
	 */
	public byte[] toBytesJson(Object obj) {
		try {
			return objectMapper.writeValueAsBytes(obj);
		} catch (Exception e) {
			logger.error("Error while serializing object to bytes json.", e);
			throw new RuntimeException(e);
		}
	}

	public <T> T fromJson(String json, Class<T> valueClass) {
		try {
			return objectMapper.readValue(json, valueClass);
		} catch (Exception e) {
			logger.error("Error while deserializing json to object.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json字节数组反序列化为实体 Note:此方法只支持简单实体类型的反序列化,如果需要反序列化为集合类型实体,使用 {@link #fromJson(byte[], Class, Class...)} }
	 *
	 * @param json
	 * @param valueClass 实体类型
	 * @return
	 * @see JsonUtil#fromJson(byte[], Class, Class...)
	 */
	public <T> T fromJson(byte[] json, Class<T> valueClass) {
		try {
			return objectMapper.readValue(json, valueClass);
		} catch (Exception e) {
			logger.error("Error while deserializing json to object.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json字符串反序列化为泛型类型实体
	 *
	 * @param json
	 * @param parametersFor 泛型实体类类型
	 * @param parameterClasses 泛型参数类型
	 * @return
	 */
	public <T> T fromJson(String json, Class<?> parametersFor, Class<?>... parameterClasses) {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametersFor, parametersFor, parameterClasses);
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error("Error while deserializing json to collection object.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json字符串反序列化为实体
	 *
	 * @param json
	 * @param typeReference 实体对应的类型引用
	 * @return
	 */
	public <T> T fromJson(String json, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			logger.error("Error while deserializing json to object.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json字节数组反序列化为泛型类型实体
	 *
	 * @param json
	 * @param parametersFor 泛型实体类类型
	 * @param parameterClasses 泛型参数类型
	 * @return
	 */
	public <T> T fromJson(byte[] json, Class<?> parametersFor, Class<?>... parameterClasses) {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametersFor, parametersFor, parameterClasses);
			return objectMapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error("Error while deserializing json to collection object.", e);
			throw new RuntimeException(e);
		}
	}
}
