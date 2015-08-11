package com.dream.netty.common.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;

@Component
public class UdpPipelineFactory extends ChannelInitializer<SocketChannel> {
	@Resource(name = "serverHandler")
	public ChannelHandler inBoundHander;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("frameDecoder", new JsonNettyDecoder(Integer.MAX_VALUE, 0, 4));
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("inBoundHandler", inBoundHander);
		pipeLine.addLast("frameEncoder", new JsonNettyEncoder(4));
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
	}
}
