package com.dream.netty.common.domain;

import java.io.Serializable;

/**
 * 公用返回结果
 * 
 * @author mobangwei
 *
 */
public interface INettyResponse extends Serializable {

	/**
	 * 逻辑头
	 * 
	 * @return
	 */
	CommandHeader getCommandHeader();

	Object getData();

	void setData(Object data);

	void setCommandHeader(CommandHeader commandHeader);

	String channelGroup();

	void channelGroup(String channelGroup);

}
