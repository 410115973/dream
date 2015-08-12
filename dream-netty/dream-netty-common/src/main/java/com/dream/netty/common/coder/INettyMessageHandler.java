package com.dream.netty.common.coder;


/**
 * 将获取的消息转化成我们需要的nettyRequest
 * 
 * @author mobangwei
 *
 */
public interface INettyMessageHandler {

	<T> T handlerForMsg(Object msg, Class<T> targetClass);

	<T> Object handlerToMsg(T sourceObject);
}
