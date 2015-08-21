package com.dream.netty.common.event.server;

import java.net.InetSocketAddress;

import com.dream.netty.common.protocol.enums.TransmissionProtocol;

/**
 * server接口，所有的自定义server都会继承该接口
 * 
 * @author mobangwei
 *
 */
public interface Server {

	TransmissionProtocol getTransmissionProtocol();

	void startServer() throws Exception;

	void startServer(int port) throws Exception;

	void stopServer() throws Exception;

	InetSocketAddress getSocketAddress();
}
