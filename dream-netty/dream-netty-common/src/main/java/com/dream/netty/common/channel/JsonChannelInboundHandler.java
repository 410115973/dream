package com.dream.netty.common.channel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

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
@Sharable
public class JsonChannelInboundHandler extends SimpleChannelInboundHandler<String> {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource
	public HandlerDispatcher handlerDispatcher;

	@Resource
	private INettyMessageHandler nettyRequestHandler;

	public void init() {
		new Thread(handlerDispatcher).start();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		Integer channelId = ctx.channel().hashCode();
		LOGGER.debug("进来一个channel：{}", channelId);
		MessageQueue messageQueue = new MessageQueue(new ConcurrentLinkedQueue<INettyRequest>());
		handlerDispatcher.addMessageQueue(channelId, messageQueue);
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward
	 * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelUnregistered();
		Integer channelId = ctx.channel().hashCode();
		LOGGER.debug("关闭一个channel：{}", channelId);
		handlerDispatcher.removeMessageQueue(channelId);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.error("出异常啦……{}", channelId, e);
		handlerDispatcher.removeMessageQueue(channelId);
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		INettyRequest nettyRequest = nettyRequestHandler.handlerForMsg(msg);
		nettyRequest.channel(ctx.channel());
		// 通知回调协议
		handlerDispatcher.addMessage(nettyRequest);
	}
}
