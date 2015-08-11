package com.dream.netty.common.coder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;
import com.dream.netty.common.domain.JsonNettyRequest;
import com.dream.netty.common.utils.JsonUtils;

@Component
public class JsonNettyMessageHandler implements INettyMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyMessageHandler.class);

	@Override
	public INettyRequest handlerForMsg(Object msg) {
		if (msg instanceof String) {
			try {
				JsonNettyRequest nettyRequest = JsonUtils.fromStr((String) msg, JsonNettyRequest.class);
				return nettyRequest;
			} catch (IOException e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public Object handlerToMsg(INettyResponse response) {
		try {
			return JsonUtils.toStr(response);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		// TODO Auto-generated method stub
		return null;
	}
}
