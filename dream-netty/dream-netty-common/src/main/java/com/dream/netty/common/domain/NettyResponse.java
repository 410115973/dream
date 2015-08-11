package com.dream.netty.common.domain;

import java.io.Serializable;

public class NettyResponse implements INettyResponse, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5056430387897853242L;
	private CommandHeader command;
	private Object data;

	public NettyResponse(CommandHeader command) {
		this.command = command;
	}

	@Override
	public CommandHeader getHeader() {
		return command;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

}
