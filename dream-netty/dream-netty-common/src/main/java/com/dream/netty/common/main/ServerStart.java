package com.dream.netty.common.main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.FileNotFoundException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.channel.UdpServerChannelInitializer;

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

	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; // 默认
	protected static final int BIZTHREADSIZE = 4;
	/*
	 * NioEventLoopGroup实际上就是个线程池,
	 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
	 * 每一个NioEventLoop负责处理m个Channel,
	 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
	 */
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	private static void serverStart(String host, Integer port) {
		try {
			ApplicationContext factory = new FileSystemXmlApplicationContext(new String[] { "classpath:config/spring/appcontext-*.xml" });

			ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1048576)).option(ChannelOption.TCP_NODELAY, true)
					.childHandler(new UdpServerChannelInitializer());
			bootstrap.bind(new InetSocketAddress(host, port));
			LOGGER.info("server host:{} port:{} is started", host, port);
		} catch (Exception e) {
			LOGGER.warn("server start error,{}", e.getMessage(), e);
		}
	}
}
