package com.dream.netty.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

import com.dream.netty.common.constants.NettyConstants;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:22:14 协议解码器
 */
public class JsonNettyDecoder extends LengthFieldBasedFrameDecoder {
	// 第一个参数为信息最大长度，超过这个长度回报异常，
	// 第二参数为长度属性的起始（偏移）位，我们的协议中长度是0到第3个字节，所以这里写0，
	// 第三个参数为“长度属性”的长度，我们是4个字节，所以写4，
	// 第四个参数为长度调节值，在总长被定义为包含包头长度时，修正信息长度，
	// 第五个参数为跳过的字节数，根据需要我们跳过前4个字节，以便接收端直接接受到不含“长度属性”的内容。

	public JsonNettyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		ByteBuf bufferMessage = (ByteBuf) super.decode(ctx, buffer);
		if (null != bufferMessage) {
			bufferMessage.skipBytes(4);// 越过dataLength的字节
			ByteBuffer byteBuffer = ByteBuffer.allocate(bufferMessage.readableBytes());
			bufferMessage.readBytes(byteBuffer);
			String obj = new String(byteBuffer.array(), NettyConstants.CHARSET);
			return obj;
		}
		return null;
	}

}
