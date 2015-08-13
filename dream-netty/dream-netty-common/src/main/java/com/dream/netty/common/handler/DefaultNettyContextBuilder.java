package com.dream.netty.common.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dream.netty.common.domain.DefaultNettyContext;
import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyRequest;
@Component("contextBuilder")
public class DefaultNettyContextBuilder implements INettyContextBuilder{

	@Override
	public INettyContext build(INettyRequest request, Map<String, Object> extras) {
		return new DefaultNettyContext(request,extras);
	}

}
