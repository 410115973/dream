package com.dream.netty.common.coder;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

@Sharable
public class JsonNettyDecoder extends MessageToMessageDecoder<String> {
	private JsonNettyMessageHandler nettyMessageHandler = new JsonNettyMessageHandler();

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		out.add(nettyMessageHandler.handlerForMsg(msg));
	}

}
