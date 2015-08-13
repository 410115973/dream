package com.dream.netty.common.domain;

import java.util.Map;

/**
 * netty的上下文，将数据都在上下文中
 * 
 * @author mobangwei
 *
 */
public interface INettyContext {
	INettyRequest getRequest();

	Map<String, Object> getExtras();

	Object getExtra(String key);

	void setExtra(String key, Object obj);
}
