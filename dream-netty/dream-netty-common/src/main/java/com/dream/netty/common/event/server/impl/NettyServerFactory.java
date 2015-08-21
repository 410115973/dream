package com.dream.netty.common.event.server.impl;

import com.dream.netty.common.protocol.enums.TransmissionProtocol;

public class NettyServerFactory {

	public static AbstractNettyServer instanceServer(TransmissionProtocol protocol) {
		switch (protocol) {
		case UDP:
			return new NettyUDPServer();
		case TCP:
			return new NettyTCPServer();
		default:
			return new NettyTCPServer();
		}
	}
}
