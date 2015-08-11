package com.dream.netty.common.handler;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.coder.INettyMessageHandler;
import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.INettyData;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.MessageQueue;
import com.dream.netty.common.domain.NettyResponse;

/**
 * 逻辑处理器
 * 
 * @author mobangwei
 *
 */
public class HandlerDispatcher implements Runnable {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private Executor messageExecutor;
	private Map<Integer, MessageQueue> sessionMsgQ;

	@Resource
	private IRequestMappingHandler requestMappingHandler;

	@Resource
	private INettyMessageHandler nettyMessageHandler;
	private boolean running = false;
	private long sleepTime = 200;

	public void init() {
		if (!running) {
			running = true;
			sessionMsgQ = new ConcurrentHashMap<Integer, MessageQueue>();
		}
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			Set<Integer> keySet = sessionMsgQ.keySet();
			for (Integer key : keySet) {
				MessageQueue messageQueue = sessionMsgQ.get(key);
				if (messageQueue == null || messageQueue.size() <= 0 || messageQueue.isRunning())
					continue;
				MessageWorker messageWorker = new MessageWorker(messageQueue);
				this.messageExecutor.execute(messageWorker);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				LOGGER.error("", e);
			}
		}
	}

	public void addMessageQueue(Integer channelId, MessageQueue messageQueue) {
		sessionMsgQ.put(channelId, messageQueue);
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
		MessageQueue messageQueue = sessionMsgQ.get(channelId);
		if (messageQueue == null) {
			request.channel().close();
			LOGGER.error("", new IllegalStateException());
		} else {
			added = messageQueue.add(request);
		}
		return added;
	}

	public boolean checkMessageQueue(String key) {
		return sessionMsgQ.containsKey(key);
	}

	/**
	 * @param session
	 */
	public void removeMessageQueue(Integer key) {
		MessageQueue queue = sessionMsgQ.remove(key);
		if (queue != null) {
			queue.clear();
		}
	}

	/**
	 * 消息队列处理线程实现
	 * 
	 * @author liliang
	 * 
	 */
	private final class MessageWorker implements Runnable {
		private MessageQueue messageQueue;

		private MessageWorker(MessageQueue messageQueue) {
			messageQueue.setRunning(true);
			this.messageQueue = messageQueue;
		}

		public void run() {
			try {
				handMessageQueue();
			} finally {
				messageQueue.setRunning(false);
			}
		}

		/**
		 * 处理消息队列
		 */
		@SuppressWarnings({ "rawtypes" })
		private void handMessageQueue() {
			INettyRequest request = messageQueue.getRequestQueue().poll();
			CommandHeader header = request.getCommandHeader();
			Channel channel = request.channel();
			INettyHandler handler = requestMappingHandler.getHandler(header.getMapping());
			if (handler != null) {
				INettyData responseData = handler.execute(request);
				// 封装response对象
				NettyResponse response = new NettyResponse(header);
				response.setData(responseData);
				channel.write(nettyMessageHandler.handlerToMsg(response));
			} else {
				LOGGER.error("not find handler:{}", request);
			}
		}
	}

	public void setMessageExecutor(Executor messageExecutor) {
		this.messageExecutor = messageExecutor;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
}
