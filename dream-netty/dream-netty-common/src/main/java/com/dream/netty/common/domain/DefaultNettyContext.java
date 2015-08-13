package com.dream.netty.common.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认上下文
 * 
 * @author mobangwei
 *
 */
public class DefaultNettyContext implements INettyContext {
	private INettyRequest request;

	private Map<String, Object> extras;

	public DefaultNettyContext(INettyRequest request, Map<String, Object> extras) {
		this.request = request;
		this.extras = extras == null ? new HashMap<String, Object>() : extras;
	}

	@Override
	public INettyRequest getRequest() {
		return request;
	}

	@Override
	public Map<String, Object> getExtras() {
		return extras;
	}

	@Override
	public Object getExtra(String key) {
		return extras.get(key);
	}

	@Override
	public void setExtra(String key, Object obj) {
		extras.put(key, obj);
	}

}
