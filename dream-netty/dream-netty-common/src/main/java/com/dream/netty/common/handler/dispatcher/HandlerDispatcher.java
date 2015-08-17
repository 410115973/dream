package com.dream.netty.common.handler.dispatcher;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.dream.netty.common.domain.CommandHeader;
import com.dream.netty.common.domain.INettyContext;
import com.dream.netty.common.domain.INettyData;
import com.dream.netty.common.domain.INettyRequest;
import com.dream.netty.common.domain.NettyResponse;
import com.dream.netty.common.handler.INettyContextBuilder;
import com.dream.netty.common.handler.INettyHandler;
import com.dream.netty.common.handler.IRequestMappingHandler;
import com.dream.netty.common.utils.JsonUtils;

/**
 * 逻辑处理器
 * 
 * @author mobangwei
 *
 */
public class HandlerDispatcher implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandlerDispatcher.class);
	private Executor messageExecutor;

	private ChannelMessageHolder channelMessageHolder;

	@Resource
	private IRequestMappingHandler requestMappingHandler;

	@Resource(name = "contextBuilder")
	private INettyContextBuilder contextBuilder;

	private boolean running = false;
	private long sleepTime = 200;

	public ChannelMessageHolder getChannelMessageHolder() {
		return channelMessageHolder;
	}

	public void init() {
		if (!running) {
			running = true;
			channelMessageHolder = new ChannelMessageHolder();
		}
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			Set<Integer> keySet = channelMessageHolder.getMessageQueueHolder().keySet();
			for (Integer key : keySet) {
				MessageQueue messageQueue = channelMessageHolder.getMessageQueueHolder().get(key);
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

	/**
	 * 消息队列处理线程实现
	 * 
	 * @author bangwei.mo
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
			} catch (Exception e) {
				LOGGER.error("handler MessageQueue error");
			} finally {
				messageQueue.setRunning(false);
			}
		}

		/**
		 * 处理消息队列
		 * 
		 * @throws IOException
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private synchronized void handMessageQueue() throws IOException {
			INettyRequest request = messageQueue.getRequestQueue().poll();
			if (null != request) {
				CommandHeader header = request.getCommandHeader();
				Channel channel = request.channel();
				INettyHandler handler = requestMappingHandler.getHandler(header.getMapping());
				if (handler != null) {
					INettyContext nettyContext = contextBuilder.build(request, null);
					INettyData responseData = handler.execute(nettyContext);

					// 封装response对象
					NettyResponse response = new NettyResponse();
					response.setCommandHeader(header);
					response.setData(responseData);
					// 广播单播的判断
					switch (request.getCommandHeader().getCommandType()) {
					case 0:
						channel.writeAndFlush(response);
						break;
					case 1:
						LOGGER.info("commandtype 1:responseItem:{}", new Object[] { JsonUtils.toStr(response) });
						List<String> targetGroups = handler.getTargetGroup(nettyContext);
						if (!CollectionUtils.isEmpty(targetGroups)) {
							for (String targetGroup : targetGroups) {
								ChannelGroup channelGroup = ChannelGroupHolder.getChannelGroup(targetGroup);
								if (null != channelGroup) {
									channelGroup.writeAndFlush(response);
								}
							}
						}
						break;
					}
				} else {
					LOGGER.error("not find handler:{}", request);
				}
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
