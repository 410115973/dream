package com.dream.netty.common.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.INettyData;
import com.dream.netty.common.domain.INettyRequest;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 下午12:10:33 类说明
 */
public abstract class ServerCommonHandler<Request extends INettyData, Response extends INettyData> implements INettyHandler<Request, Response> {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public abstract Response execute(Request request, Map<String, Object> model) throws NettyRunTimeException;

	@Override
	public Response execute(INettyRequest request) throws NettyRunTimeException {
		Map<String, Object> model = new HashMap<String, Object>();
		return execute(request.dataConverter(getGenericType()), model);
	}

	@SuppressWarnings("unchecked")
	protected Class<Request> getGenericType() {
		Type t = this.getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			return (Class<Request>) p[0];
		}
		return null;
	}
}
