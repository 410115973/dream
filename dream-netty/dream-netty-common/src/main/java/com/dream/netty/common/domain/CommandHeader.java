package com.dream.netty.common.domain;

import java.io.Serializable;

/**
 * 协议头文件
 * 
 * @author mobangwei
 *
 */
public class CommandHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4393462980394338020L;

	/**
	 * 一次交互的唯一id
	 */
	private long id;
	/**
	 * 映射的key
	 */
	private String mapping;
	/**
	 * 协议请求时间戳
	 */
	private long time;

	/**
	 * 0 私有协议 1 广播协议
	 */
	private int commandType = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getCommandType() {
		return commandType;
	}

	public void setCommandType(int commandType) {
		this.commandType = commandType;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

}
