//package com.dream.netty.common.channel;
//
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelPipeline;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpRequestEncoder;
//
//import java.nio.channels.Channels;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class HttpPipelineFactory implements ChannelPipelineFactory {
//	@Resource(name = "httpServerHandler")
//	public ChannelHandler httpServerHandler;
//
//	public ChannelPipeline getPipeline() throws Exception {
//		
//		ChannelPipeline pipeLine = Channels.newChannel(in)();
//
//		pipeLine.addLast("decoder", new HttpRequestDecoder());
//		pipeLine.addLast("encoder", new HttpRequestEncoder());
//		pipeLine.addLast("handler", new HttpServerHandler());
//		return pipeLine;
//	}
//
//}
