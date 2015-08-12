package com.dream.netty.common.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.dream.netty.common.biz.handler.NameInfoRequest;
import com.dream.netty.common.client.ClientChannelInitializer;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.JsonNettyRequest;
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
			b.handler((ClientChannelInitializer) factory.getBean("clientChannelInitializer", ClientChannelInitializer.class));
			ChannelFuture future = b.connect(new InetSocketAddress(host, port));
			Channel c = future.channel();
			CommandHeader header = new CommandHeader();
			header.setId(UUID.randomUUID().getMostSignificantBits());
			header.setMapping("nameInfo");
			header.setTime(System.currentTimeMillis());
			JsonNettyRequest request = new JsonNettyRequest();
			request.setCommandHeader(header);
			NameInfoRequest a = new NameInfoRequest();
			a.setName("莫邦伟");
			request.setData(a);
			c.pipeline().get("frameEncoder");
			c.writeAndFlush(JsonUtils.toStr(request)).sync();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
