package com.dream.netty.common.handler;

import java.util.Map;

import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyRequest;

public interface INettyContextBuilder {

	INettyContext build(INettyRequest request, Map<String, Object> extras);
}
