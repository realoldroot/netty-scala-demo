package com.artemis.socket.server

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * Created by zhengenshen on 2017/10/17.
  */
class ServerHandler extends ChannelInboundHandlerAdapter {

  /**
    * 有客户端建立连接后调用
    */
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("有客户端建立连接后调用")
  }

  /**
    * 接受客户端发送来的消息
    */
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println("接受客户端发送来的消息" + msg)

    val back = "connection success"
    println("返回消息" + back)
    ctx.writeAndFlush(back)
  }
}
