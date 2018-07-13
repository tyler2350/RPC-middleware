package com.xuzhong.rpctest.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * @author bird
 * 发送心跳包
 * 基于WRITER_IDLE状态
 */
@Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
	private final static ByteBuf HEARTBEATPACKAGE = Unpooled.copiedBuffer("heartbeat".getBytes());

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		IdleState state = null;
		if (evt instanceof IdleStateEvent)
			state = ((IdleStateEvent) evt).state();

		if (state == IdleState.WRITER_IDLE) {
			ctx.writeAndFlush(HEARTBEATPACKAGE.copy());
			return;
		}
		
		//当UserEventTriggered事件未处理完时执行
		ctx.fireUserEventTriggered(evt);
	}

}