package com.dream.netty.common.main;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.config.NettyServerConfig;

/**
 * 启动,使用udp协议
 * 
 * @author mobangwei
 *
 */
public class ServerStart {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStart.class);

	public static void main(String[] args) throws FileNotFoundException {
		Log4jConfigurer.initLogging("classpath:log/log4j.xml");
		NettyServerConfig config = new NettyServerConfig();
		config.setHost("localhost");
		config.setPort(8888);
		config.setProtocolType("tcp");
		new ServerStartTask(config).start();
	}
}
