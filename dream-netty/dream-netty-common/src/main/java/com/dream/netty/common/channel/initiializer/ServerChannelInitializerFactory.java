package com.dream.netty.common.channel.initiializer;

import io.netty.channel.ChannelInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.protocol.enums.TransmissionProtocol;

public class ServerChannelInitializerFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerChannelInitializerFactory.class);

	public static ChannelInitializer<?> getInstance(TransmissionProtocol protocol) {
		switch (protocol) {
		case HTTP:
			return new HttpServerChannelInitializer();
		case TCP:
			return new TcpServerChannelInitializer();
		case WEB_SOCKET:
			return new WebSocketChannelInitializer();
		case UDP:
			return new UdpServerChannelInitializer();
		default:
			break;
		}
		LOGGER.warn("there is no valiable channel initializer of type:{}", protocol);
		return null;
	}
}
