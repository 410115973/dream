package com.dream.netty.common.biz.handler;

import java.util.Map;

import com.dream.netty.common.annotation.HandlerMapping;
import com.dream.netty.common.handler.NettyRunTimeException;
import com.dream.netty.common.handler.ServerCommonHandler;

@HandlerMapping("nameInfo")
public class NameInfoHandler extends ServerCommonHandler<NameInfoRequest, NameInfoResponse> {
	@Override
	public NameInfoResponse execute(NameInfoRequest request, Map<String, Object> model) throws NettyRunTimeException {
		LOGGER.info(request.getName());
		NameInfoResponse response = new NameInfoResponse();
		response.setAge(10);
		return response;
	}

}
