package com.dream.netty.common.domain;

import java.util.Map;

/**
 * 所有请求的公共头 可以包含不同协议的字段
 * 
 * @author mobangwei
 *
 */
public interface BaseRequestHeader {
	/**
	 * 协议类型
	 * 
	 * @return
	 */
	String protocolType();

	/**
	 * 版本号
	 * 
	 * @return
	 */
	String version();

	/**
	 * 加密类型
	 * 
	 * @return
	 */
	String encryptType();

	/**
	 * 协议包含的多个自定义类型
	 * 
	 * @return
	 */
	Map<String, Object> extra();
}
