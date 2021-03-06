package com.dream.netty.common.coder;

import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.HttpNettyRequest;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;

public class HttpNettyMessageHandler implements INettyMessageHandler<HttpRequest, HttpResponse> {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpNettyMessageHandler.class);

	@Override
	public INettyRequest handlerForMsg(HttpRequest msg) {
		try {
			HttpRequest request = (HttpRequest) msg;
			String url = request.getUri();
			HttpMethod method = request.getMethod();
			if (method.equals(HttpMethod.PUT)) {
				QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
				Map<String, List<String>> params = decoder.parameters();
				INettyRequest targetObject = new HttpNettyRequest();
				if (targetObject instanceof INettyRequest) {
					INettyRequest nettyRequest = (INettyRequest) targetObject;
					CommandHeader header = new CommandHeader();
					HttpHeaders headers = request.headers();
					header.setId(Long.parseLong(headers.get("id")));
					header.setMapping(url);
					header.setTime(Long.parseLong(headers.get("time")));
					header.setCommandType(Integer.parseInt(headers.get("commandType")));
					nettyRequest.setCommandHeader(header);
					nettyRequest.setData(params);
					nettyRequest.setData(decoder);
					return targetObject;
				}
			} else {
				if (method.equals(HttpMethod.POST)) {
					HttpContentCompressor decoder = new HttpContentCompressor();

				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public HttpResponse handlerToMsg(INettyResponse sourceObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
