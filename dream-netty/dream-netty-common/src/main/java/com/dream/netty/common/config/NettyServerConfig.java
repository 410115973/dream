package com.dream.netty.common.config;


public class NettyServerConfig {
	private String host;

	private Integer port;

	private String protocolType;

	private Integer bossGroupCount = Runtime.getRuntime().availableProcessors() * 2;

	private Integer workGroupCount = 4;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public Integer getBossGroupCount() {
		return bossGroupCount;
	}

	public void setBossGroupCount(Integer bossGroupCount) {
		this.bossGroupCount = bossGroupCount;
	}

	public Integer getWorkGroupCount() {
		return workGroupCount;
	}

	public void setWorkGroupCount(Integer workGroupCount) {
		this.workGroupCount = workGroupCount;
	}

}
