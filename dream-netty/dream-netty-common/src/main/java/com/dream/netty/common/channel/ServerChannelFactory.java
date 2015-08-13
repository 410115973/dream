package com.dream.netty.common.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerChannelFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerChannelFactory.class);

	public static ChannelInitializer<SocketChannel> getInstance(String protocolType) {
		if ("http".equals(protocolType)) {
			return new HttpServerChannelInitializer();
		}
		if ("tcp".equals(protocolType)) {
			return new TcpServerChannelInitializer();
		}
		LOGGER.warn("there is no valiable channel initializer of type:{}", protocolType);
		return null;
	}
}
