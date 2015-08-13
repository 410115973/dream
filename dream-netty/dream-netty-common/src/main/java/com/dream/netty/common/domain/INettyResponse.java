package com.dream.netty.common.domain;

import io.netty.channel.Channel;

/**
 * 公用返回结果
 * 
 * @author mobangwei
 *
 */
public interface INettyResponse {

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

}
