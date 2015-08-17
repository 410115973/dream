package com.dream.netty.common.handler.dispatcher;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelGroupHolder {
	private static Map<String, ChannelGroup> channelGroupHolder = new ConcurrentHashMap<String, ChannelGroup>();

	/**
	 * 注册一个channel组
	 * 
	 * @param groupName
	 */
	public static void registerChannelGroup(String groupName) {
		channelGroupHolder.put(groupName, new DefaultChannelGroup(groupName, GlobalEventExecutor.INSTANCE));
	}

	public static ChannelGroup getChannelGroup(String groupName) {
		return channelGroupHolder.get(groupName);
	}

	public static void addChannel(String groupName, Channel channel) {
		ChannelGroup channelGroup = getChannelGroup(groupName);
		if (null != channelGroup) {
			channelGroup.add(channel);
		}
	}

	public static void addChannel(Channel channel) {
		for (String groupName : channelGroupHolder.keySet()) {
			addChannel(groupName, channel);
		}
	}

	public static void remove(String groupName, Channel channel) {
		ChannelGroup channelGroup = getChannelGroup(groupName);
		if (null != channelGroup) {
			channelGroup.remove(channel);
		}
	}

	/**
	 * 删除所有的channel
	 * 
	 * @param channel
	 */
	public static void remove(Channel channel) {
		for (String groupName : channelGroupHolder.keySet()) {
			channelGroupHolder.get(groupName).remove(channel);
		}
	}

	public static void disRegisterChannelGroup(String groupName) {
		channelGroupHolder.remove(groupName);
	}
}
