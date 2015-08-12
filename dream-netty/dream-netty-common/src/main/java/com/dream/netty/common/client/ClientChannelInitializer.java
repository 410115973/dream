package com.dream.netty.common.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dream.netty.common.coder.PluginLengthFieldBasedFrameDecoder;

@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Resource(name = "clientChannelInboundHandler")
	private ChannelHandler clientChannelInboundHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("frameDecoder", new PluginLengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));

		// pipeLine.addLast("frameDecoder", new JsonNettyDecoder());
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("inBoundHandler", clientChannelInboundHandler);
		// pipeLine.addLast("frameEncoder", new JsonNettyEncoder());
		pipeLine.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		// pipeLine.addLast("outBoundHandler", new ChannelOutboundHandler());
	}
}
