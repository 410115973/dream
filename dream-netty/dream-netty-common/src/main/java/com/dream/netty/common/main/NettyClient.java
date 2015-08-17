package com.dream.netty.common.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.biz.handler.NameInfoRequest;
import com.dream.netty.common.client.ClientChannelInitializer;
import com.dream.netty.common.constants.NettyConstants;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.JsonNettyRequest;
import com.dream.netty.common.utils.EncryptUtils;
import com.dream.netty.common.utils.JsonUtils;

public class NettyClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("classpath:log/log4j.xml");
			ApplicationContext factory = new FileSystemXmlApplicationContext(new String[] { "classpath:config/spring/appcontext-*.xml" });

			// Parse options.
			String host = "127.0.0.1";
			int port = 8888;
			EventLoopGroup group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class);
			b.handler(new ClientChannelInitializer());
			ChannelFuture future = b.connect(new InetSocketAddress(host, port));
			Channel c = future.channel();
			sendMsg(c, "nameInfo", 1, getNameInfoRequest("莫邦伟"));
			sendMsg(c, "nameInfo", 0, getNameInfoRequest("李博"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private static void sendMsg(Channel c, String mapping, Integer commandType, INettyRequest request) throws Exception {
		CommandHeader header = new CommandHeader();
		header.setId(UUID.randomUUID().getMostSignificantBits());
		header.setMapping(mapping);
		header.setTime(System.currentTimeMillis());
		header.setCommandType(commandType);
		request.setCommandHeader(header);
		request.setData(EncryptUtils.encrypt((Serializable) request.getData(), NettyConstants.ENCRIPT_KEY));
		c.writeAndFlush(JsonUtils.toStr(request)).sync();
	}

	private static INettyRequest getNameInfoRequest(String name) {
		JsonNettyRequest request = new JsonNettyRequest();
		NameInfoRequest a = new NameInfoRequest();
		a.setName(name);
		request.setData(a);
		return request;
	}
}
