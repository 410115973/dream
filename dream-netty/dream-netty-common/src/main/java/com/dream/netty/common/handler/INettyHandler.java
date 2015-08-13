package com.dream.netty.common.handler;

import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyData;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:31:48 类说明
 */
public interface INettyHandler<Request extends INettyData, Response extends INettyData> {
	Response execute(INettyContext context) throws NettyRunTimeException;
}
