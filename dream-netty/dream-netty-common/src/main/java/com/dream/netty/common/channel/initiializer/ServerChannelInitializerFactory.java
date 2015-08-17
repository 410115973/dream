package com.dream.netty.common.channel.initiializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.protocol.enums.NettyProtocolType;

public class ServerChannelInitializerFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerChannelInitializerFactory.class);

	public static ChannelInitializer<SocketChannel> getInstance(NettyProtocolType protocolType) {
		switch (protocolType) {
		case http:
			return new HttpServerChannelInitializer();
		case tcp:
			return new TcpServerChannelInitializer();
		case websocket:
			return new WebSocketChannelInitializer();
		default:
			break;
		}
		LOGGER.warn("there is no valiable channel initializer of type:{}", protocolType);
		return null;
	}
}
