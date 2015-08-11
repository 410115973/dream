package com.dream.netty.common.biz.handler;

import com.dream.netty.common.domain.INettyData;

public class NameInfoRequest implements INettyData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8826960667969892779L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
