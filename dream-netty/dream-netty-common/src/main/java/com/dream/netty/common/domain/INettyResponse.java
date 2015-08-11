package com.dream.netty.common.domain;


/**
 * 作者:chenpeng E-mail:46731706@qq.com 创建时间：2012-7-12 上午11:26:59 类说明
 */
public interface INettyResponse {

	CommandHeader getHeader();

	Object getData();

	void setData(Object data);
}
