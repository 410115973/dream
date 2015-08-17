package com.dream.netty.common.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyData;
import com.dream.netty.common.domain.INettyRequest;

/**
 * 基础类型，所有的handler都继承该类型做返回
 * 
 * @author mobangwei
 *
 * @param <Request>
 * @param <Response>
 */
public abstract class ServerCommonHandler<Request extends INettyData, Response extends INettyData> implements INettyHandler<Request, Response> {
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public abstract Response execute(Request request, Map<String, Object> model) throws NettyRunTimeException;

	protected List<String> targetGroup(INettyContext context) throws NettyRunTimeException {
		return new ArrayList<String>();
	}

	protected void prepare(INettyContext nettyContext) throws NettyRunTimeException {
		return;
	}

	@Override
	public Response execute(INettyContext context) throws NettyRunTimeException {
		prepare(context);
		INettyRequest request = context.getRequest();
		return execute(request.dataConverter(getRequestGenericType()), context.getExtras());
	}

	@Override
	public List<String> getTargetGroup(INettyContext context) throws NettyRunTimeException {
		Integer commandType = context.getRequest().getCommandHeader().getCommandType();
		if (0 == commandType) {
			return null;
		} else {
			return this.targetGroup(context);
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<Request> getRequestGenericType() {
		Type t = this.getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			return (Class<Request>) p[0];
		}
		return null;
	}
}
