package com.dream.netty.common.domain;

import io.netty.channel.Channel;

/**
 * 公用的request
 * 
 * @author mobangwei
 *
 */
public interface INettyRequest {
	/**
	 * 逻辑头
	 * 
	 * @return
	 */
	CommandHeader getCommandHeader();

	Channel channel();

	void channel(Channel channel);

	Object getData();

	void setData(Object data);

	void setCommandHeader(CommandHeader commandHeader);

	<T extends INettyData> T dataConverter(Class<T> targetClass);

}
