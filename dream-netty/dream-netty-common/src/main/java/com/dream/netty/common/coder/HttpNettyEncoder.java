package com.dream.netty.common.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.dream.netty.common.domain.NettyResponse;

public class HttpNettyEncoder  extends MessageToMessageEncoder<NettyResponse> {
	private INettyMessageHandler nettyMessageHandler = new HttpNettyMessageHandler();

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyResponse msg, List<Object> out) throws Exception {
		Object handlerMsg = nettyMessageHandler.handlerToMsg(msg);
		out.add(handlerMsg);
	}
}
