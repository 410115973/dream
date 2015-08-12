package com.dream.netty.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class PluginLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {

	public PluginLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		ByteBuf result = (ByteBuf) super.decode(ctx, msg);
		if (null != result) {
			result.skipBytes(4);
		}
		return result;
	}

}
