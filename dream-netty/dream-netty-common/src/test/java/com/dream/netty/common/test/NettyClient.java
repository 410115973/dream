package com.dream.netty.common.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.UUID;

import com.dream.netty.common.biz.handler.NameInfoRequest;
import com.dream.netty.common.client.ClientPipelineFactory;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.JsonNettyRequest;
import com.dream.netty.common.utils.JsonUtils;

public class NettyClient {

	public static void main(String[] args) {
		// Parse options.
		String host = "127.0.0.1";
		int port = 8888;
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ClientPipelineFactory());
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
		try {
			String msgStr = JsonUtils.toStr(request);
			c.write(msgStr);
			c.flush();
			c.closeFuture().sync();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
