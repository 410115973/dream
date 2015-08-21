package com.dream.netty.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.udt.UdtMessage;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

@Sharable
public class UdtNettyEncoder extends MessageToMessageEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		out.add(new UdtMessage(msg));
	}

}
