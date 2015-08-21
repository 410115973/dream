package com.dream.netty.common.channel.initiializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.udt.nio.NioUdtAcceptorChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import com.dream.netty.common.channel.ChannelInboundHandler;
import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;
import com.dream.netty.common.coder.PluginLengthFieldBasedFrameDecoder;
import com.dream.netty.common.coder.UdtNettyDecoder;
import com.dream.netty.common.coder.UdtNettyEncoder;

public class UdpServerChannelInitializer extends ChannelInitializer<NioUdtAcceptorChannel> {

	@Override
	protected void initChannel(NioUdtAcceptorChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("udtDecoder",new UdtNettyDecoder());
		pipeLine.addLast("frameDecoder", new PluginLengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
		pipeLine.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeLine.addLast("bizDecoder", new JsonNettyDecoder());
		pipeLine.addLast("handler", new ChannelInboundHandler());
		pipeLine.addLast("udtEncoder",new UdtNettyEncoder());
		pipeLine.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeLine.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		pipeLine.addLast("bizEecoder", new JsonNettyEncoder());
	}

}
