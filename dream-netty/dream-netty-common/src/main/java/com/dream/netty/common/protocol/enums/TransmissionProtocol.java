package com.dream.netty.common.protocol.enums;

/**
 * 支持的协议类型，根据不同的协议类型，生成相对应的server,直接的编解码和客户端的实现不同
 * 
 * @author mobangwei
 *
 */
public enum TransmissionProtocol {
	TCP, UDP, WEB_SOCKET, HTTP, CUSTOMER;
}
