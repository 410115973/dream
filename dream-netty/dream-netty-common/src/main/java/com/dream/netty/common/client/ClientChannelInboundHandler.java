package com.dream.netty.common.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dream.netty.common.coder.INettyMessageHandler;
import com.dream.netty.common.constants.NettyConstants;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.INettyResponse;
import com.dream.netty.common.domain.NettyResponse;
import com.dream.netty.common.utils.EncryptUtils;

@Component
public class ClientChannelInboundHandler extends SimpleChannelInboundHandler<String> {
	public Logger LOGGER = LoggerFactory.getLogger(ClientChannelInboundHandler.class);
	@Resource
	private INettyMessageHandler nettyRequestHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		INettyResponse nettyResponse = nettyRequestHandler.handlerForMsg(msg, NettyResponse.class);
		Object response = nettyResponse.getData();
		Object targetData = EncryptUtils.decrypt((String) response, NettyConstants.ENCRIPT_KEY);
		nettyResponse.setData(targetData);
		CommandHeader header = nettyResponse.getCommandHeader();
		LOGGER.info("response is :{},{}", nettyResponse.getData(), header);
	}
}
