package com.dream.netty.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author danson.liu
 *
 */
public class SpringLocator implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) BeanFactoryUtils.beanOfType(applicationContext, clazz);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringLocator.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
