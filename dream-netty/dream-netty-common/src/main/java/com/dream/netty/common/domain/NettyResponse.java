package com.dream.netty.common.domain;


public class NettyResponse implements INettyResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5056430387897853242L;

	private CommandHeader commandheader;

	private Object data;

	private String channelGroup;

	@Override
	public CommandHeader getCommandHeader() {
		return commandheader;
	}

	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void setCommandHeader(CommandHeader commandHeader) {
		this.commandheader = commandHeader;
	}

	@Override
	public String channelGroup() {
		return channelGroup;
	}

	@Override
	public void channelGroup(String channelGroup) {
		this.channelGroup = channelGroup;

	}

}
