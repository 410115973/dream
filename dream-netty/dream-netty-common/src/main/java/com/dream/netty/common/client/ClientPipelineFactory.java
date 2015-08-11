package com.dream.netty.common.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 下午12:25:03 类说明
 */
public class ClientPipelineFactory extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("frameDecoder", new JsonNettyDecoder(Integer.MAX_VALUE, 0, 4));
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("inBoundHandler", new ClientHandler());
		pipeLine.addLast("frameEncoder", new JsonNettyEncoder(4));
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
	}
}
