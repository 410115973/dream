package com.dream.netty.common.domain;

import java.util.Map;

/**
 * 统一请求
 * 
 * @author mobangwei
 *
 */
public interface INettyCommand {

	CommandHeader header();

	Object body();

	Map<String, Object> extra();
}
