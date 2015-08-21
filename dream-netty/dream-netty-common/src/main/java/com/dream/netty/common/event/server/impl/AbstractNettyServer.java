package com.dream.netty.common.event.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.handler.dispatcher.ChannelGroupHolder;

public abstract class AbstractNettyServer implements NettyServer {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyServer.class);

	protected InetSocketAddress socketAddress;
	protected ServerBootstrap bootstrap;
	private ChannelFuture bindFuture;

	@Override
	public void startServer() throws Exception {
		startServer(8888);
	}

	@Override
	public void startServer(int port) throws Exception {
		startServer(new InetSocketAddress("localhost", port));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void startServer(InetSocketAddress socketAddress) throws Exception {
		setInetAddress(socketAddress);
		bootstrap = createServerBootstrap();
		bootstrap.group(bossGroup(), workGroup());
		bootstrap.childHandler(getChannelInitializer());
		if (null != channel()) {
			bootstrap.channel(channel());
		} else {
			if (null != channelFactory()) {
				bootstrap.channelFactory(channelFactory());
			}
		}
		Map<ChannelOption, Object> bootStrapOptions = bootStrapOptions();
		for (ChannelOption option : bootStrapOptions.keySet()) {
			bootstrap.option(option, bootStrapOptions.get(option));
		}
		Collection<String> registerChannelGroups = registerChannelGroups();
		if (CollectionUtils.isNotEmpty(registerChannelGroups)) {
			for (String groupKey : registerChannelGroups) {
				ChannelGroupHolder.registerChannelGroup(groupKey);
			}
		}
		bindFuture = bootstrap.bind(getSocketAddress());
	}

	@Override
	public void stopServer() throws Exception {
		LOG.debug("In stopServer method of class: {}", this.getClass().getName());
		ChannelGroupHolder.disRegisterChannelGroup();
		try {
			bindFuture.channel().closeFuture().sync();
			bossGroup().shutdownGracefully();
			workGroup().shutdownGracefully();
		} catch (InterruptedException e) {
			LOG.error("Execption occurred while waiting for channels to close: {}", e);
		}
	}

	@Override
	public ServerBootstrap getServerBootstrap() {
		return bootstrap;
	}

	@Override
	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	protected void setInetAddress(InetSocketAddress inetAddress) {
		this.socketAddress = inetAddress;
	}

}
