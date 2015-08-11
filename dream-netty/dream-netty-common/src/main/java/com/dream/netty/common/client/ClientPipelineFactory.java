package com.dream.netty.common.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

import com.dream.netty.common.coder.JsonNettyDecoder;
import com.dream.netty.common.coder.JsonNettyEncoder;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 下午12:25:03 类说明
 */
public class ClientPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeLine = Channels.pipeline();
		pipeLine.addLast("decoder", new JsonNettyDecoder(Integer.MAX_VALUE, 0, 4));
		pipeLine.addLast("encoder", new JsonNettyEncoder(4));
		pipeLine.addLast("handler", new ClientHandler());

		return pipeLine;

	}

}
