package com.dream.netty.common.channel.initiializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import com.dream.netty.common.channel.ChannelInboundHandler;
import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;
import com.dream.netty.common.coder.WebSocketDecoder;
import com.dream.netty.common.coder.WebSocketEncoder;
import com.dream.netty.common.coder.WebSocketHttpRequestHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeLine = ch.pipeline();
		pipeLine.addLast("http-codec", new HttpServerCodec());
		pipeLine.addLast("http-object-aggregator", new HttpObjectAggregator(64 * 1024));
		pipeLine.addLast("chunkedWrite", new ChunkedWriteHandler());
		pipeLine.addLast("websocket-request-handler", new WebSocketHttpRequestHandler("/ws"));
		pipeLine.addLast("websocket-protocol-handler", new WebSocketServerProtocolHandler("/ws"));
		pipeLine.addLast("websocket-decoder", new WebSocketDecoder());
		pipeLine.addLast("bizDecoder", new JsonNettyDecoder());
		pipeLine.addLast("handler", new ChannelInboundHandler());
		pipeLine.addLast("websocket-encoder", new WebSocketEncoder());
		pipeLine.addLast("bizEecoder", new JsonNettyEncoder());
	}
}
