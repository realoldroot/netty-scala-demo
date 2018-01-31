package com.artemis.socket.client

import com.artemis.socket.module.Message
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * Created by zhengenshen on 2017/10/19.
  */
class MessageClientHandler extends ChannelInboundHandlerAdapter{

  /**
    * 有客户端建立连接后调用
    */
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("channelActive")
    val content = Message("asdasd")
    ctx.writeAndFlush(content)
  }


  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    println("channelRead读取内容")
    /* val byteBuf = msg.asInstanceOf[ByteBuf]
     val bytes = new Array[Byte](byteBuf.readableBytes())
     byteBuf.readBytes(bytes)
     val message = new String(bytes, "UTF-8")*/
    println("message : " + msg)
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    println("channeReadComplete")
    ctx.flush()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    println("exceptionCaught")
    ctx.close()
  }
}
