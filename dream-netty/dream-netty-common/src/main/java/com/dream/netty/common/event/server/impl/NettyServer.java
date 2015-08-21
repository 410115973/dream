package com.dream.netty.common.event.server.impl;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.Collection;
import java.util.Map;

import com.dream.netty.common.event.server.Server;

/**
 * netty server，利用netty4.0版本的相关server
 * 
 * @author mobangwei
 *
 */
public interface NettyServer extends Server {
	/**
	 * 基本定义 bootstrap
	 * 
	 * @return
	 */
	public ServerBootstrap createServerBootstrap();

	/**
	 * 不同的协议的options不同
	 * 
	 * @param optionsList
	 */
	@SuppressWarnings("rawtypes")
	public Map<ChannelOption, Object> bootStrapOptions();

	public NioEventLoopGroup bossGroup();

	public NioEventLoopGroup workGroup();

	public ChannelHandler getChannelInitializer();

	/**
	 * 注册channel group
	 */
	public Collection<String> registerChannelGroups();

	/**
	 * @return Returns the created server bootstrap object.
	 */
	public ServerBootstrap getServerBootstrap();

	public <T extends ServerChannel> Class<T> channel();

	public <T extends ServerChannel> ChannelFactory<T> channelFactory();
}
