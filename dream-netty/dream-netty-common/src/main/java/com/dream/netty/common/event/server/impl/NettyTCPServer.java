package com.dream.netty.common.event.server.impl;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.channel.initiializer.ServerChannelInitializerFactory;
import com.dream.netty.common.protocol.enums.TransmissionProtocol;

/**
 * TCP SERVER
 * 
 * @author mobangwei
 *
 */
public class NettyTCPServer extends AbstractNettyServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyTCPServer.class);

	private int bossGroupCount = 4;

	private int workGroupCount = 4;

	private Set<String> groupNames = new HashSet<String>();

	@SuppressWarnings("rawtypes")
	@Override
	public Map<ChannelOption, Object> bootStrapOptions() {
		Map<ChannelOption, Object> options = new HashMap<ChannelOption, Object>();
		options.put(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1048576));
		options.put(ChannelOption.TCP_NODELAY, true);
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
		return groupNames;
	}

	@Override
	public TransmissionProtocol getTransmissionProtocol() {
		return TransmissionProtocol.TCP;
	}

	public int getBossGroupCount() {
		return bossGroupCount;
	}

	public void setBossGroupCount(int bossGroupCount) {
		this.bossGroupCount = bossGroupCount;
	}

	public int getWorkGroupCount() {
		return workGroupCount;
	}

	public void setWorkGroupCount(int workGroupCount) {
		this.workGroupCount = workGroupCount;
	}

	public Set<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(Set<String> groupNames) {
		this.groupNames = groupNames;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ServerChannel> Class<T> channel() {
		return (Class<T>) NioServerSocketChannel.class;
	}

	@Override
	public ServerBootstrap createServerBootstrap() {
		return new ServerBootstrap();
	}

	@Override
	public <T extends ServerChannel> ChannelFactory<T> channelFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
