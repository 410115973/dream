package com.dream.netty.common.main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dream.netty.common.channel.ServerChannelFactory;
import com.dream.netty.common.config.NettyServerConfig;

public class ServerStartTask extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStartTask.class);

	private NettyServerConfig config;

	public ServerStartTask(final NettyServerConfig config) {
		this.config = config;
	}

	public void run() {
		try {
			ApplicationContext factory = new FileSystemXmlApplicationContext(new String[] { "classpath:config/spring/appcontext-*.xml" });

			ServerBootstrap bootstrap = new ServerBootstrap()

			.group(new NioEventLoopGroup(config.getBossGroupCount()), new NioEventLoopGroup(config.getWorkGroupCount()))

			.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1048576));
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.childHandler(ServerChannelFactory.getInstance(config.getProtocolType()));

			bootstrap.bind(new InetSocketAddress(config.getHost(), config.getPort()));
			LOGGER.info("server host:{} port:{} is started", config.getHost(), config.getPort());
		} catch (Exception e) {
			LOGGER.warn("server start error,{}", e.getMessage(), e);
		}
	}
}
