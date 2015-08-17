package com.dream.netty.common.handler;

import java.util.List;

import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyData;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:31:48 类说明
 */
public interface INettyHandler<Request extends INettyData, Response extends INettyData> {
	Response execute(INettyContext context) throws NettyRunTimeException;

	/**
	 * 将返回的消息返回给哪些组，即消息的触达范围
	 * 
	 * @return
	 */
	List<String> getTargetGroup(INettyContext context) throws NettyRunTimeException;
}
