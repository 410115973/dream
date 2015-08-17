package com.dream.netty.common.biz.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dream.netty.common.annotation.NettyAction;
import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.handler.NettyRunTimeException;
import com.dream.netty.common.handler.ServerCommonHandler;

@NettyAction("nameInfo")
public class NameInfoHandler extends ServerCommonHandler<NameInfoRequest, NameInfoResponse> {
	@Override
	public NameInfoResponse execute(NameInfoRequest request, Map<String, Object> model) throws NettyRunTimeException {
		LOGGER.info(request.getName());
		NameInfoResponse response = new NameInfoResponse();
		response.setAge(10);
		return response;
	}

	public List<String> targetGroup(INettyContext context) throws NettyRunTimeException {
		return Arrays.asList("chat");
	}
}
