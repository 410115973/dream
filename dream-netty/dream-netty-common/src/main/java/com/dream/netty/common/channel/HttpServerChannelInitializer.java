package com.dream.netty.common.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import com.dream.netty.common.coder.HttpNettyDecoder;
import com.dream.netty.common.coder.HttpNettyEncoder;

public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("frameDecoder", new HttpRequestDecoder());
		pipeLine.addLast("bizDecoder", new HttpNettyDecoder());
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("handler", new ChannelInboundHandler());
		pipeLine.addLast("frameEncoder", new HttpResponseDecoder());
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		pipeLine.addLast("bizEecoder", new HttpNettyEncoder());
	}
}
