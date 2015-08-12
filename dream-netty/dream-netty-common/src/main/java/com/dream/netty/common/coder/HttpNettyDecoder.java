package com.dream.netty.common.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;

import com.dream.netty.common.domain.HttpNettyRequest;
import com.dream.netty.common.domain.INettyRequest;

public class HttpNettyDecoder extends MessageToMessageDecoder<HttpRequest> {
	private INettyMessageHandler nettyMessageHandler = new HttpNettyMessageHandler();

	@Override
	protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
		out.add((INettyRequest) nettyMessageHandler.handlerForMsg(msg, HttpNettyRequest.class));
	}

}
