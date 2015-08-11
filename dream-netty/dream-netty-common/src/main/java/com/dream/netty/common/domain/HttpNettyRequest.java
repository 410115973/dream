package com.dream.netty.common.domain;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.dream.netty.common.utils.ReflectionUtil;

public class HttpNettyRequest implements INettyRequest, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 626376548432437323L;

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpNettyRequest.class);

	private CommandHeader commandheader;

	private Channel channel;

	private Object data;

	private BaseRequestHeader baseRequestHeader;

	public Channel channel() {
		return channel;
	}

	public void setCommandHeader(CommandHeader commandheader) {
		this.commandheader = commandheader;
	}

	public long commandId() {
		if (commandheader != null) {
			return commandheader.getId();
		} else {
			return -1;
		}
	}

	@Override
	public CommandHeader getCommandHeader() {
		return commandheader;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void channel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public <T extends INettyData> T dataConverter(Class<T> targetClass) {
		try {
			Map<String, List<String>> params = (Map<String, List<String>>) data;
			BeanInfo beanInfo = Introspector.getBeanInfo(targetClass);
			T obj = targetClass.newInstance();
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (params.containsKey(key)) {
					Object targetValue = null;
					List<String> value = params.get(key);
					if (!CollectionUtils.isEmpty(value) && value.size() == 1) {
						String valueStr = value.get(0);
						Class<?> paramType = property.getPropertyType();
						targetValue = ReflectionUtil.parseValue(paramType, valueStr);
					} else {
						targetValue = value;
					}
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, targetValue);
				}

			}
			return obj;

		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public BaseRequestHeader getBaseRequestHeader() {
		return baseRequestHeader;
	}

	public void setBaseRequestHeader(BaseRequestHeader baseRequestHeader) {
		this.baseRequestHeader = baseRequestHeader;
	}

}
