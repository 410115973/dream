package com.dream.netty.common.domain;

import java.util.Map;

/**
 * 所有请求的公共头 可以包含不同协议的字段
 * 
 * @author mobangwei
 *
 */
public class BaseRequestHeader {
	/**
	 * 版本号
	 * 
	 * @return
	 */
	private String version;

	/**
	 * 加密类型
	 * 
	 * @return
	 */
	private String encryptType;

	/**
	 * 协议包含的多个自定义类型
	 * 
	 * @return
	 */
	private Map<String, Object> extra;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

}
