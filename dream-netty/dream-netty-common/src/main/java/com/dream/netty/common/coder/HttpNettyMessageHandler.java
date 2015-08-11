package com.dream.netty.common.coder;

import java.util.List;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.HttpNettyRequest;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;

public class HttpNettyMessageHandler implements INettyMessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpNettyMessageHandler.class);

	@Override
	public INettyRequest handlerForMsg(Object msg) {
		HttpRequest request = (HttpRequest) msg;
		String url = request.getUri();
		HttpMethod method = request.getMethod();
		if (method.equals(HttpMethod.PUT)) {
			QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> params = decoder.getParameters();
			HttpNettyRequest nettyRequest = new HttpNettyRequest();
			CommandHeader header = new CommandHeader();
			header.setId(Long.parseLong(request.getHeader("id")));
			header.setMapping(url);
			header.setTime(Long.parseLong(request.getHeader("time")));
			header.setCommandType(Integer.parseInt(request.getHeader("commandType")));
			nettyRequest.setCommandHeader(header);
			nettyRequest.setData(params);
		} else {
			if (method.equals(HttpMethod.POST)) {
				
			}
		}

		
		return null;
	}

	@Override
	public Object handlerToMsg(INettyResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
