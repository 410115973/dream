package com.dream.netty.common.coder;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHttpRequestHandler.class);
	private final String wsUri;
	private static final File INDEX;

	// static HTTP request handling operation.
	static {
		URL location = WebSocketHttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			String path = location.toURI() + "WebSocketClient.html";
			path = !path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
		}
	}

	// constructor function call for current class
	public WebSocketHttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (wsUri.equalsIgnoreCase(request.getUri())) {
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}

			RandomAccessFile file = new RandomAccessFile(INDEX, "r");
			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			if (keepAlive) {
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			ctx.write(response);

			if (ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			} else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if (!keepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}

			file.close();
		}
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		Integer channelId = ctx.channel().hashCode();
		LOGGER.info("出异常啦……{}", channelId, e);
		ctx.channel().close();
	}

}
