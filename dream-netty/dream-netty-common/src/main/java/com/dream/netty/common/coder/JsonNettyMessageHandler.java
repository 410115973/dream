package com.dream.netty.common.coder;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dream.netty.common.constants.NettyConstants;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;
import com.dream.netty.common.domain.JsonNettyRequest;
import com.dream.netty.common.domain.NettyResponse;
import com.dream.netty.common.utils.EncryptUtils;
import com.dream.netty.common.utils.JsonUtils;

@Component
public class JsonNettyMessageHandler implements INettyMessageHandler<String, String> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonNettyMessageHandler.class);

	@Override
	public INettyRequest handlerForMsg(String msg) {
		if (msg instanceof String) {
			try {
				JsonNettyRequest targetObject = JsonUtils.fromStr((String) msg, JsonNettyRequest.class);
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
	public String handlerToMsg(INettyResponse sourceObject) {
		try {
			INettyResponse response = new NettyResponse();
			response.setCommandHeader(sourceObject.getCommandHeader());
			String data = EncryptUtils.encrypt((Serializable) sourceObject.getData(), NettyConstants.ENCRIPT_KEY);
			response.setData(data);
			return JsonUtils.toStr(response);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return null;
	}
}
