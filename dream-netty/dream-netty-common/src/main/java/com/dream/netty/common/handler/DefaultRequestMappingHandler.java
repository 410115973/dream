package com.dream.netty.common.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dream.netty.common.utils.SpringLocator;

/**
 * 
 * @author mobangwei
 *
 */
@Component
public class DefaultRequestMappingHandler implements IRequestMappingHandler {

	private static Map<String, INettyHandler> handlers = SpringLocator.getApplicationContext().getBeansOfType(INettyHandler.class);

	@Override
	public INettyHandler getHandler(String commandKey) {
		return handlers.get(commandKey);
	}

}
