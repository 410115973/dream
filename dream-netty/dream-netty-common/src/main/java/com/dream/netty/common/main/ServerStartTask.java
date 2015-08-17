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

import com.dream.netty.common.channel.initiializer.ServerChannelInitializerFactory;
import com.dream.netty.common.config.NettyServerConfig;
import com.dream.netty.common.handler.dispatcher.ChannelGroupHolder;
import com.dream.netty.common.protocol.enums.NettyProtocolType;

public class ServerStartTask extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStartTask.class);

	private NettyServerConfig config;

	public ServerStartTask(final NettyServerConfig config) {
		this.config = config;
	}

	public void run() {
		NioEventLoopGroup boss = new NioEventLoopGroup(config.getBossGroupCount());
		NioEventLoopGroup work = new NioEventLoopGroup(config.getWorkGroupCount());

		try {
			ApplicationContext factory = new FileSystemXmlApplicationContext(new String[] { "classpath:config/spring/appcontext-*.xml" });

			ServerBootstrap bootstrap = new ServerBootstrap()

			.group(boss, work)

			.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1048576));
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.childHandler(ServerChannelInitializerFactory.getInstance(NettyProtocolType.valueOf(config.getProtocolType())));

			ChannelFuture future = bootstrap.bind(new InetSocketAddress(config.getHost(), config.getPort()));
			// 启动时候注册多个组
			ChannelGroupHolder.registerChannelGroup("chat");
			ChannelGroupHolder.registerChannelGroup("player");
			// future.channel().closeFuture().sync();
			LOGGER.info("a {} server is started on host:{} port:{}", new Object[] { config.getProtocolType(), config.getHost(), config.getPort() });
		} catch (Exception e) {
			LOGGER.warn("server start error,{}", e.getMessage(), e);
		}
	}
}
