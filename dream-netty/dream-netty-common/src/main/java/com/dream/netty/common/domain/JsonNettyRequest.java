package com.dream.netty.common.domain;

import io.netty.channel.Channel;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.utils.JsonUtils;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:36:31 类说明 游戏请求封装
 */
public class JsonNettyRequest implements INettyRequest, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8674285431816184437L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyRequest.class);

	private CommandHeader commandheader;

	private Channel channel;

	private Object data;

	private BaseRequestHeader baseRequestHeader;

	public Channel channel() {
		return channel;
	}

	@Override
	public void setCommandHeader(CommandHeader commandheader) {
		this.commandheader = commandheader;
	}

	public long commandId() {
		if (commandheader != null) {
			return commandheader.getId();
		} else {
			return -1;
		}
	}

	@Override
	public CommandHeader getCommandHeader() {
		return commandheader;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void channel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public <T extends INettyData> T dataConverter(Class<T> targetClass) {
		try {
			if (data instanceof Map) {
				return JsonUtils.fromStr(JsonUtils.toStr(data), targetClass);
			}
		} catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public BaseRequestHeader getBaseRequestHeader() {
		return baseRequestHeader;
	}

	@Override
	public void setBaseRequestHeader(BaseRequestHeader baseRequestHeader) {
		this.baseRequestHeader = baseRequestHeader;
	}

}
