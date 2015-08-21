package com.dream.netty.common.event.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dream.netty.common.event.server.Server;
import com.dream.netty.common.event.server.ServerManager;
import com.dream.netty.common.protocol.enums.TransmissionProtocol;

public class ServerManagerImpl implements ServerManager {
	private Map<Integer, ServerThread> servers;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerManagerImpl.class);

	public ServerManagerImpl() {
		servers = new HashMap<Integer, ServerThread>();
	}

	@Override
	public void startServer(Integer port, TransmissionProtocol protocol) throws Exception {
		ServerThread serverThread = new ServerThread(NettyServerFactory.instanceServer(protocol), port);
		servers.put(port, serverThread);
		serverThread.start();

	}

	@Override
	public void startServers(Map<Integer, TransmissionProtocol> serverConfigs) throws Exception {
		if (MapUtils.isNotEmpty(serverConfigs)) {
			for (Integer port : serverConfigs.keySet()) {
				startServer(port, serverConfigs.get(port));
			}
		}

	}

	@Override
	public void stopServers(List<Integer> ports) throws Exception {
		for (Integer port : ports) {
			servers.get(port).shutdown();
		}

	}

	public static class ServerThread extends Thread {
		private static final Logger LOGGER = LoggerFactory.getLogger(ServerThread.class);
		private Server server;

		private Integer port;

		public ServerThread(Server server, Integer port) {
			this.server = server;
			this.port = port;
		}

		public void run() {
			try {
				server.startServer(port);
				LOGGER.info("a {} server is started on host:{} port:{}",
						new Object[] { server.getTransmissionProtocol(), server.getSocketAddress().getHostName(), server.getSocketAddress().getPort() });
			} catch (Exception e) {
				LOGGER.warn("server started error,{}", e.getMessage(), e);
			}
		}

		@SuppressWarnings("deprecation")
		public void shutdown() {
			try {
				server.stopServer();
			} catch (Exception e) {
				LOGGER.warn("server stoped error,{}", e.getMessage(), e);
			} finally {
				super.stop();
			}
		}
	}

}
