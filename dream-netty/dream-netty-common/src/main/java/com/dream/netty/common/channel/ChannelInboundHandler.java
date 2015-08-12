package com.dream.netty.common.channel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.MessageQueue;
import com.dream.netty.common.handler.HandlerDispatcher;

/**
 * 
 * @author mobangwei
 *
 */
@Sharable
public class ChannelInboundHandler extends SimpleChannelInboundHandler<INettyRequest> {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public HandlerDispatcher handlerDispatcher = new HandlerDispatcher();

	public void init() {
		new Thread(handlerDispatcher).start();
	}

	/**
	 * Do nothing by default, sub-classes may override this method.
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("进来一个channel：{}", channelId);
		MessageQueue messageQueue = new MessageQueue(new ConcurrentLinkedQueue<INettyRequest>());
		handlerDispatcher.addMessageQueue(channelId, messageQueue);
	}

	/**
	 * Do nothing by default, sub-classes may override this method.
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("关闭一个channel：{}", channelId);
		handlerDispatcher.removeMessageQueue(channelId);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("出异常啦……{}", channelId, e);
		handlerDispatcher.removeMessageQueue(channelId);
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, INettyRequest msg) throws Exception {
		msg.channel(ctx.channel());
		// 通知回调协议
		handlerDispatcher.addMessage(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		super.channelReadComplete(ctx);
	}
}
