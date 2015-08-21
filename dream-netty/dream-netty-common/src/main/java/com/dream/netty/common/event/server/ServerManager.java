package com.dream.netty.common.event.server;

import java.util.List;
import java.util.Map;

import com.dream.netty.common.protocol.enums.TransmissionProtocol;

/**
 * 服务构建器
 * 
 * @author mobangwei
 *
 */
public interface ServerManager {

	public void startServer(Integer port, TransmissionProtocol protocol) throws Exception;

	public void startServers(Map<Integer, TransmissionProtocol> serverConfigs) throws Exception;

	/**
	 * Used to stop the server and manage cleanup of resources.
	 * 
	 */
	public void stopServers(List<Integer> ports) throws Exception;
}
