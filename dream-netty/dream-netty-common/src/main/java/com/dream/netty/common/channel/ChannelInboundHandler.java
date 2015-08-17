package com.dream.netty.common.channel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.handler.dispatcher.ChannelGroupHolder;
import com.dream.netty.common.handler.dispatcher.HandlerDispatcher;
import com.dream.netty.common.utils.SpringLocator;

/**
 * 经过各种协议的拆包后生成一个INettyRequest抽象类，所有的请求最终到达后台的属性都是一致的
 * 
 * @author mobangwei
 *
 */
@Sharable
public class ChannelInboundHandler extends SimpleChannelInboundHandler<INettyRequest> {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private HandlerDispatcher handlerDispatcher;

	public ChannelInboundHandler() {
		handlerDispatcher = SpringLocator.getBean(HandlerDispatcher.class);
		new Thread(handlerDispatcher).start();
	}

	/**
	 * Do nothing by default, sub-classes may override this method.
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		handlerDispatcher.getChannelMessageHolder().registerChannel(ctx.channel());
		ChannelGroupHolder.addChannel(ctx.channel());
	}

	/**
	 * Do nothing by default, sub-classes may override this method.
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("关闭一个channel：{}", channelId);
		handlerDispatcher.getChannelMessageHolder().removeChannel(ctx.channel());
		ChannelGroupHolder.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("出异常啦……{}", channelId, e);
		handlerDispatcher.getChannelMessageHolder().removeChannel(ctx.channel());
		ChannelGroupHolder.remove(ctx.channel());
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, INettyRequest msg) throws Exception {
		msg.channel(ctx.channel());
		// 通知回调协议
		handlerDispatcher.getChannelMessageHolder().addMessage(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		super.channelReadComplete(ctx);
	}
}
