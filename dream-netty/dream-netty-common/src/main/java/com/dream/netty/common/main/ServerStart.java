package com.dream.netty.common.main;

import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.channel.UdpPipelineFactory;

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
		serverStart("localhost", 8888);
	}

	private static void serverStart(String host, Integer port) {
		try {
			ApplicationContext factory = new FileSystemXmlApplicationContext(new String[] { "classpath:config/spring/appcontext-*.xml" });

			ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
			UdpPipelineFactory pipelineFactory = (UdpPipelineFactory) factory.getBean("serverPipelineFactory");
			bootstrap.setPipelineFactory(pipelineFactory);
			bootstrap.setOption("receiveBufferSize", 500 * 1024 * 1024);// 设置单个udp最大接收字节1024，默认为768
			bootstrap.setOption("tcpNoDelay", true);
			bootstrap.setOption("receiveBufferSizePredictorFactory", new FixedReceiveBufferSizePredictorFactory(1048576));
			bootstrap.bind(new InetSocketAddress(host, port));
			LOGGER.info("server host:{} port:{} is started", host, port);
		} catch (Exception e) {
			LOGGER.warn("server start error,{}", e.getMessage(), e);
		}
	}

}
