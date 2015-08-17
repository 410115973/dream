package com.dream.netty.common.handler.dispatcher;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.domain.INettyRequest;

/**
 * 所有的消息请求都被缓存于一个对象中，此次后期可以改成缓存存储
 * 
 * @author mobangwei
 *
 */
public class ChannelMessageHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelMessageHolder.class);
	private Map<Integer, MessageQueue> messageQueueHolder;

	public ChannelMessageHolder() {
		messageQueueHolder = new ConcurrentHashMap<Integer, MessageQueue>();
	}

	public Map<Integer, MessageQueue> getMessageQueueHolder() {
		return messageQueueHolder;
	}

	public void registerChannel(Channel channel) {
		MessageQueue initMessageQueue = new MessageQueue(new ConcurrentLinkedQueue<INettyRequest>());
		Integer channelId = channel.hashCode();
		messageQueueHolder.put(channelId, initMessageQueue);
	}

	/**
	 * 为当前session添加消息
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	public boolean addMessage(INettyRequest request) {
		boolean added = false;
		int channelId = request.channel().hashCode();
		MessageQueue messageQueue = messageQueueHolder.get(channelId);
		if (messageQueue == null) {
			request.channel().close();
			LOGGER.error("", new IllegalStateException());
		} else {
			added = messageQueue.add(request);
		}
		return added;
	}

	public boolean checkMessageQueue(String key) {
		return messageQueueHolder.containsKey(key);
	}

	/**
	 * @param session
	 */
	public void removeChannel(Channel channel) {
		Integer channelId = channel.hashCode();
		MessageQueue queue = messageQueueHolder.remove(channelId);
		if (queue != null) {
			queue.clear();
		}
	}

}
