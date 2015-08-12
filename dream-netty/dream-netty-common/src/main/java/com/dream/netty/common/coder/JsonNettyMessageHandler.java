package com.dream.netty.common.coder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dream.netty.common.utils.JsonUtils;

@Component
public class JsonNettyMessageHandler implements INettyMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyMessageHandler.class);

	@Override
	public <T> T handlerForMsg(Object msg, Class<T> targetClass) {
		if (msg instanceof String) {
			try {
				T targetObject = JsonUtils.fromStr((String) msg, targetClass);
				return targetObject;
			} catch (IOException e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public <T> Object handlerToMsg(T sourceObject) {
		try {
			return JsonUtils.toStr(sourceObject);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		// TODO Auto-generated method stub
		return null;
	}
}
