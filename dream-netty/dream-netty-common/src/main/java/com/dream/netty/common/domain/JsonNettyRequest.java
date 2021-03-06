package com.dream.netty.common.domain;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.utils.JsonUtils;

/**
 * json的Request
 * 
 * @author mobangwei
 *
 */
public class JsonNettyRequest implements INettyRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8674285431816184437L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyRequest.class);

	private CommandHeader commandheader;

	private Channel channel;

	private Object data;

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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends INettyData> T dataConverter(Class<T> targetClass) {
		try {
			if (data instanceof Map) {
				return JsonUtils.fromStr(JsonUtils.toStr(data), targetClass);
			}
		} catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return (T) data;
	}

}
