package com.artemis.socket.client

import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * Created by zhengenshen on 2017/10/17.
  */
class ClientStringHandler extends ChannelInboundHandlerAdapter {
  /**
    * 有客户端建立连接后调用
    */
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("有客户端建立连接后调用")
    val content = "你好,我是客户端"
    ctx.writeAndFlush(content)
  }


  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println("读取服务端的消息" + msg)
  }
}
