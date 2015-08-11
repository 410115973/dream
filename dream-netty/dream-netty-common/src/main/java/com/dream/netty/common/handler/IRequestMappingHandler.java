package com.dream.netty.common.handler;

/**
 * 请求的映射
 * 
 * @author mobangwei
 *
 */
public interface IRequestMappingHandler {

	@SuppressWarnings("rawtypes")
	INettyHandler getHandler(String commandKey);
}
