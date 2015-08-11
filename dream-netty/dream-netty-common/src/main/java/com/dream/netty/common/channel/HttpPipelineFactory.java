package com.dream.netty.common.channel;

import javax.annotation.Resource;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpRequestEncoder;
import org.springframework.stereotype.Component;

@Component
public class HttpPipelineFactory implements ChannelPipelineFactory {
	@Resource(name = "httpServerHandler")
	public ChannelHandler httpServerHandler;

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeLine = Channels.pipeline();

		pipeLine.addLast("decoder", new HttpRequestDecoder());
		pipeLine.addLast("encoder", new HttpRequestEncoder());
		pipeLine.addLast("handler", new HttpServerHandler());
		return pipeLine;
	}

}
