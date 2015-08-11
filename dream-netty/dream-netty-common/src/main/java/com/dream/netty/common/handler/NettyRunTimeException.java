/**
 * Jan 14, 2009 6:34:50 PM
 */
package com.dream.netty.common.handler;

import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.INettyResponse;

/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:25:51 类说明
 */
public class NettyRunTimeException extends RuntimeException {
	private static final long serialVersionUID = 3848407576445825258L;
	private INettyRequest request;
	private INettyResponse response;

	/**
	 * @return the request
	 */
	public INettyRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	public INettyResponse getResponse() {
		return response;
	}

	/**
	 * @param e
	 * @param response
	 * @param request
	 */
	public NettyRunTimeException(Throwable e, INettyRequest request, INettyResponse response) {
		this(null, e, request, response);

	}

	/**
	 * @param string
	 */
	public NettyRunTimeException(String msg, INettyRequest request, INettyResponse response) {
		this(msg, null, request, response);
	}

	public NettyRunTimeException(String msg, Throwable e, INettyRequest request, INettyResponse response) {
		super(msg, e);
		this.request = request;
		this.response = response;
	}
}
