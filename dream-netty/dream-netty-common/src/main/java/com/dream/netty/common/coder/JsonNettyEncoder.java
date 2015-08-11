package com.dream.netty.common.coder;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

import com.dream.netty.common.constants.NettyConstants;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:43:11 类说明
 */
public class JsonNettyEncoder extends LengthFieldPrepender {

	public JsonNettyEncoder(int lengthFieldLength) {
		super(lengthFieldLength);
	}

	@Override
	protected Object encode(ChannelHandlerContext cxt, Channel channel, Object msg) throws Exception {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(channel.getConfig().getBufferFactory());
		if (null != msg) {
			String msgStr = msg.toString();
			byte[] bytes = msgStr.getBytes(NettyConstants.CHARSET);
			buffer.writeInt(bytes.length);
			buffer.writeBytes(bytes);
		}
		return buffer;

	}
}
