package com.dream.netty.common.domain;

import io.netty.channel.Channel;

import java.io.Serializable;

public class NettyResponse implements INettyResponse, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5056430387897853242L;

	private CommandHeader commandheader;

	private Channel channel;

	private Object data;

	private BaseRequestHeader baseRequestHeader;

	public Channel channel() {
		return channel;
	}

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
	public BaseRequestHeader getBaseRequestHeader() {
		return baseRequestHeader;
	}

	public void setBaseRequestHeader(BaseRequestHeader baseRequestHeader) {
		this.baseRequestHeader = baseRequestHeader;
	}

}
