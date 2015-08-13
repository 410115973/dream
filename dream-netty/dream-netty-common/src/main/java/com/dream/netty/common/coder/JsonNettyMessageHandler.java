package com.dream.netty.common.coder;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dream.netty.common.constants.NettyConstants;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;
import com.dream.netty.common.utils.EncryptUtils;
import com.dream.netty.common.utils.JsonUtils;

@Component
public class JsonNettyMessageHandler implements INettyMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyMessageHandler.class);

	@Override
	public <T> T handlerForMsg(Object msg, Class<T> targetClass) {
		if (msg instanceof String) {
			try {
				T targetObject = JsonUtils.fromStr((String) msg, targetClass);
				if (targetObject instanceof INettyRequest) {
					INettyRequest request = (INettyRequest) targetObject;
					Object data = request.getData();
					Object targetData = EncryptUtils.decrypt((String) data, NettyConstants.ENCRIPT_KEY);
					request.setData(targetData);
				}
				return targetObject;
			} catch (Exception e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public <T> Object handlerToMsg(T sourceObject) {
		try {
			if (sourceObject instanceof INettyResponse) {
				INettyResponse response = (INettyResponse) sourceObject;
				String data = EncryptUtils.encrypt((Serializable) response.getData(), NettyConstants.ENCRIPT_KEY);
				response.setData(data);
			}
			return JsonUtils.toStr(sourceObject);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		// TODO Auto-generated method stub
		return null;
	}
}
