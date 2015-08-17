package com.dream.netty.common.handler.dispatcher;

import java.util.Queue;

import com.dream.netty.common.domain.INettyRequest;

public final class MessageQueue {

	private Queue<INettyRequest> requestQueue;
	private boolean running = false;

	public MessageQueue(Queue<INettyRequest> requestQueue) {
		this.requestQueue = requestQueue;
	}

	public Queue<INettyRequest> getRequestQueue() {
		return requestQueue;
	}

	/**
	 * 清除消息队列
	 */
	public void clear() {
		requestQueue.clear();
		requestQueue = null;
	}

	/**
	 * 获取消息队列长度
	 * 
	 * @return
	 */
	public int size() {
		return requestQueue != null ? requestQueue.size() : 0;
	}

	/**
	 * 向消息队列中添加请求消息
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(INettyRequest request) {
		return this.requestQueue.add(request);
	}

	/**
	 * 设置消息队列运行状态
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 消息队列是否正在被轮询
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

}