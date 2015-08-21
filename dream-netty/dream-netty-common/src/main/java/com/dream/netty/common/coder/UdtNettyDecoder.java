package com.dream.netty.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.udt.UdtMessage;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This decoder will convert a Netty {@link ChannelBuffer} to a
 * {@link NettyMessageBuffer}. It will also convert
 * {@link Events#NETWORK_MESSAGE} events to {@link Events#SESSION_MESSAGE}
 * event.
 * 
 * @author Abraham Menacherry
 * 
 */
@Sharable
public class UdtNettyDecoder extends MessageToMessageDecoder<UdtMessage> {
	private static final Logger LOG = LoggerFactory.getLogger(UdtNettyDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, UdtMessage msg, List<Object> out) throws Exception {
		if (null == msg) {
			LOG.error("Null message received in MessageBufferEventDecoder");
			return;
		}
		ByteBuf msgByte = msg.content();
		out.add(msgByte);

	}
}
