package com.dream.netty.common.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * 
 * @author tiantian.gao
 * 
 */
public final class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	/**
	 * 将对象转换为JSON格式
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	public static String toStr(Object model) throws IOException {
		return MAPPER.writeValueAsString(model);
	}

	/**
	 * 将JSON字符串转换为指定类实例
	 * 
	 * @param <T>
	 * @param content
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T fromStr(String content, Class<T> clazz) throws IOException {
		return MAPPER.readValue(content, clazz);
	}

	public static <T> T fromStr(String content, TypeReference<T> ref) throws IOException {
		return MAPPER.readValue(content, ref);
	}

	/**
	 * 将JSON字符串转换为Map
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromStrToMap(String content) throws IOException {
		return fromStr(content, Map.class);
	}

}
