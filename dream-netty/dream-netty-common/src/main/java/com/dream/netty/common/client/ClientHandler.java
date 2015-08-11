package com.dream.netty.common.client;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.dream.netty.common.biz.handler.NameInfoRequest;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.NettyRequest;
import com.dream.netty.common.utils.JsonUtils;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 下午12:27:04 类说明
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {

		Channel c = e.getChannel();
		CommandHeader header = new CommandHeader();
		header.setId(UUID.randomUUID().getMostSignificantBits());
		header.setMapping("nameInfo");
		header.setTime(System.currentTimeMillis());
		NettyRequest request = new NettyRequest(header);
		NameInfoRequest a = new NameInfoRequest();
		a.setName("莫邦伟");
		request.setData(a);
		try {
			String msg = JsonUtils.toStr(request);
			c.write(msg);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		// Close the connection when an exception is raised.
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();
		String info = acceptBuff.toString(Charset.defaultCharset());
		System.out.println("客户端接收到消息:" + info);
		e.getChannel().close();
	}

}
