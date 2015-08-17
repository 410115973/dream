package com.dream.netty.common.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import com.dream.netty.common.coder.PluginLengthFieldBasedFrameDecoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("frameDecoder", new PluginLengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));

		// pipeLine.addLast("frameDecoder", new JsonNettyDecoder());
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("inBoundHandler", new ClientChannelInboundHandler());
		// pipeLine.addLast("frameEncoder", new JsonNettyEncoder());
		pipeLine.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
	}
}
