package com.dream.netty.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import com.dream.netty.common.constants.NettyConstants;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:43:11 类说明
 */
public class JsonNettyEncoder extends LengthFieldPrepender {

	public JsonNettyEncoder(int lengthFieldLength) {
		super(lengthFieldLength);
	}

	@Override
	protected void encode(ChannelHandlerContext cxt, ByteBuf msg, ByteBuf out) throws Exception {
		super.encode(cxt, msg, out);
		if (null != out) {
			String msgStr = msg.toString();
			byte[] bytes = msgStr.getBytes(NettyConstants.CHARSET);
			out.writeInt(bytes.length);
			out.writeBytes(bytes);
		}

	}
}
