package com.dream.netty.common.handler;

import org.springframework.stereotype.Component;

import com.dream.netty.common.utils.SpringLocator;

/**
 * 
 * @author mobangwei
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class DefaultRequestMappingHandler implements IRequestMappingHandler {

	@Override
	public INettyHandler getHandler(String commandKey) {
		return (INettyHandler) SpringLocator.getApplicationContext().getBean(commandKey, INettyHandler.class);
	}

}
