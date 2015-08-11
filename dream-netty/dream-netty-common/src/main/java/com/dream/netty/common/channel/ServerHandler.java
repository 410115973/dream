package com.dream.netty.common.channel;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.coder.INettyMessageHandler;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.MessageQueue;
import com.dream.netty.common.handler.HandlerDispatcher;

/**
 * 
 * @author mobangwei
 *
 */
public class ServerHandler extends SimpleChannelUpstreamHandler {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource
	public HandlerDispatcher handlerDispatcher;

	@Resource
	private INettyMessageHandler nettyRequestHandler;

	public void init() {
		new Thread(handlerDispatcher).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected
	 * (org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelStateEvent) 建立一个新channel
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		LOGGER.debug("进来一个channel：" + ctx.getChannel().getId());
		MessageQueue messageQueue = new MessageQueue(new ConcurrentLinkedQueue<INettyRequest>());
		handlerDispatcher.addMessageQueue(ctx.getChannel().getId(), messageQueue);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected
	 * (org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelStateEvent) 玩家主动关闭channel
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		LOGGER.error("关掉一个channel：" + ctx.getChannel().getId());
		handlerDispatcher.removeMessageQueue(e.getChannel().getId().toString());
		e.getChannel().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(
	 * org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ExceptionEvent) 玩家被动关闭channel
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		LOGGER.error("出异常啦……" + ctx.getChannel().getId());
		e.getCause().printStackTrace();
		handlerDispatcher.removeMessageQueue(e.getChannel().getId().toString());
		e.getChannel().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(
	 * org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.MessageEvent) 消息接收处理器
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		INettyRequest nettyRequest = nettyRequestHandler.handlerForMsg(e.getMessage());
		nettyRequest.channel(ctx.getChannel());
		// 通知回调协议
		handlerDispatcher.addMessage(nettyRequest);
	}
}
