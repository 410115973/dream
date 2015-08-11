package com.dream.netty.common.biz.handler;

import com.dream.netty.common.domain.INettyData;

public class NameInfoResponse implements INettyData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1440182349651896863L;

	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
