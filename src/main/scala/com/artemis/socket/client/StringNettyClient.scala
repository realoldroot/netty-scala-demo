package com.artemis.socket.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}

/**
  * Created by zhengenshen on 2017/10/18.
  */
class StringNettyClient {

  def connect(host: String, port: Int): Unit = {
    //创建客户端NIO线程组
    val eventGroup = new NioEventLoopGroup
    //创建客户端辅助启动类
    val bootStrap = new Bootstrap

    try {
      bootStrap.group(eventGroup)
        //创建NioSocketChannel
        .channel(classOf[NioSocketChannel])
        //绑定I/O事件处理类
        .handler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline().addLast(new StringEncoder)
          ch.pipeline().addLast(new StringDecoder)
          ch.pipeline().addLast(new ClientStringHandler)
        }
      })
      //发起异步连接操作
      val channelFuture = bootStrap.connect(host, port).sync()
      //等待服务关闭
      channelFuture.channel().closeFuture().sync()

    } finally {
      //优雅的退出，释放线程池资源
      eventGroup.shutdownGracefully()
    }
  }
}

object StringNettyClient {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8083
    val client = new StringNettyClient
    client.connect(host, port)
  }

}
