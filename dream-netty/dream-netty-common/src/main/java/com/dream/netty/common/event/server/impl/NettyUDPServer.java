package com.dream.netty.common.event.server.impl;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.nio.NioUdtAcceptorChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.channel.udt.UdtServerChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dream.netty.common.channel.initiializer.ServerChannelInitializerFactory;
import com.dream.netty.common.protocol.enums.TransmissionProtocol;

/**
 * udpServer
 * 
 * @author mobangwei
 *
 */
public class NettyUDPServer extends AbstractNettyServer {

	private int bossGroupCount = 4;

	private int workGroupCount = 4;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<ChannelOption, Object> bootStrapOptions() {
		Map<ChannelOption, Object> options = new HashMap<ChannelOption, Object>();
		options.put(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1048576));
		options.put(ChannelOption.TCP_NODELAY, false);
		options.put(ChannelOption.SO_BROADCAST, false);
		options.put(ChannelOption.SO_SNDBUF, 65536);
		options.put(ChannelOption.SO_RCVBUF, 65536);
		return options;
	}

	@Override
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossGroupCount);
	}

	@Override
	public NioEventLoopGroup workGroup() {
		return new NioEventLoopGroup(workGroupCount);
	}

	@Override
	public ChannelHandler getChannelInitializer() {
		return ServerChannelInitializerFactory.getInstance(getTransmissionProtocol());
	}

	@Override
	public Collection<String> registerChannelGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ServerChannel> Class<T> channel() {
		return null;
	}

	@Override
	public TransmissionProtocol getTransmissionProtocol() {
		return TransmissionProtocol.UDP;
	}

	@Override
	public ServerBootstrap createServerBootstrap() {
		return new ServerBootstrap();
	}

	@Override
	public <UdtServerChannel extends ServerChannel> ChannelFactory<UdtServerChannel> channelFactory() {
		return (ChannelFactory<UdtServerChannel>) NioUdtProvider.MESSAGE_ACCEPTOR;
	}

}
