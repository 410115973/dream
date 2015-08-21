package com.dream.common.server.spring.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dream.netty.common.config.NettyServerConfig;
import com.dream.netty.common.main.ServerStartTask;

/**
 * 加载spring 中netty服务的声明
 * 
 * @author mobangwei
 *
 */
public class NettySpringServletContextListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(NettySpringServletContextListener.class);
	private ServerStartTask serverStartTask;
	// 获取spring注入的bean对象
	private WebApplicationContext springContext;

	/*
	 * 这个方法在Web应用服务被移除，没有能力再接受请求的时候被调用。
	 */
	public void contextDestroyed(ServletContextEvent event) {
		serverStartTask.stopServer();
	}

	// 这个方法在Web应用服务做好接受请求的时候被调用。
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		serverStartTask = new ServerStartTask(loadConfig());
		serverStartTask.startServer();
	}

	private NettyServerConfig loadConfig() throws Error {
		NettyServerConfig config = (NettyServerConfig) BeanFactoryUtils.beanOfType(springContext, NettyServerConfig.class);
		if (null == config) {
			LOGGER.error("null netty server defined on the spring xml file");
			throw new Error("null netty server defined on the spring xml file");
		}
		return config;
	}
}