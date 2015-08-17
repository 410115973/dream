package com.dream.netty.common.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.biz.handler.NameInfoResponse;
import com.dream.netty.common.constants.NettyConstants;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.INettyResponse;
import com.dream.netty.common.domain.NettyResponse;
import com.dream.netty.common.utils.EncryptUtils;
import com.dream.netty.common.utils.JsonUtils;

public class ClientChannelInboundHandler extends SimpleChannelInboundHandler<String> {
	public Logger LOGGER = LoggerFactory.getLogger(ClientChannelInboundHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		LOGGER.info("reader msg:{}", msg);
		INettyResponse nettyResponse = JsonUtils.fromStr(msg, NettyResponse.class);
		Object response = nettyResponse.getData();
		Object targetData = EncryptUtils.decrypt((String) response, NettyConstants.ENCRIPT_KEY);
		LOGGER.info("targetObject is:{}", targetData);
		if (!(targetData instanceof NameInfoResponse)) {
			LOGGER.error("error decrypt");
			throw new IllegalArgumentException("error decrypte");
		}
		nettyResponse.setData(targetData);
		CommandHeader header = nettyResponse.getCommandHeader();
		LOGGER.info("response is :{},{}", JsonUtils.toStr(nettyResponse.getData()), header);
	}
}
