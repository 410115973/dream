package com.dream.netty.common.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.event.server.ServerManager;
import com.dream.netty.common.event.server.impl.ServerManagerImpl;
import com.dream.netty.common.protocol.enums.TransmissionProtocol;

/**
 * 启动,使用udp协议
 * 
 * @author mobangwei
 *
 */
public class ServerStart {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStart.class);

	public static void main(String[] args) throws Exception {
		Log4jConfigurer.initLogging("classpath:log/log4j.xml");
		ServerManager serverManager = new ServerManagerImpl();
		serverManager.startServer(8989, TransmissionProtocol.UDP);
	}
}
