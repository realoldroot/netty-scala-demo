package com.artemis.socket.server

import com.artemis.socket.module.Message
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

/**
  * Created by zhengenshen on 2017/10/19.
  */
class MessageHandler extends SimpleChannelInboundHandler[Message]{

  override def channelRead0(ctx: ChannelHandlerContext, msg: Message): Unit = {
    val message = msg
    println(s"message消息是:$message")
  }
}
