package com.dream.netty.common.channel;

import javax.annotation.Resource;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.springframework.stereotype.Component;

import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:28:56
 * channelPipeline是一系列channelHandler的集合，他参照J2ee中的Intercepting Filter模式来实现的，
 * 让用户完全掌握如果在一个handler中处理事件，同时让pipeline里面的多个handler可以相互交互
 */
@Component
public class UdpPipelineFactory implements ChannelPipelineFactory {
	@Resource(name = "serverHandler")
	public ChannelHandler serverHandler;

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeLine = Channels.pipeline();
		pipeLine.addLast("decoder", new JsonNettyDecoder(Integer.MAX_VALUE, 0, 4));
		pipeLine.addLast("encoder", new JsonNettyEncoder(4));
		pipeLine.addLast("handler", serverHandler);
		return pipeLine;
	}

}
