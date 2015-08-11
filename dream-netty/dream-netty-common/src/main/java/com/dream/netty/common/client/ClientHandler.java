package com.dream.netty.common.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.dream.netty.common.biz.handler.NameInfoRequest;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.JsonNettyRequest;
import com.dream.netty.common.utils.JsonUtils;

@Component
public class ClientHandler extends ChannelOutboundHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

}
