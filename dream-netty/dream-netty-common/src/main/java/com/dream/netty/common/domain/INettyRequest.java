package com.dream.netty.common.domain;

import io.netty.channel.Channel;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:26:47 类说明
 */
public interface INettyRequest {
	/**
	 * 公共头，协议相关
	 * 
	 * @return
	 */
	BaseRequestHeader getBaseRequestHeader();

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

	void setBaseRequestHeader(BaseRequestHeader baseRequestHeader);

	void setCommandHeader(CommandHeader commandHeader);

	<T extends INettyData> T dataConverter(Class<T> targetClass);

}
