package com.artemis.socket.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}

/**
  * Created by zhengenshen on 2017/10/18.
  */
class StringNettyServer {


  def bind(host: String, port: Int): Unit = {
    //配置服务端线程池组
    //用于服务器接收客户端连接
    val bossGroup = new NioEventLoopGroup
    //用户进行SocketChannel的网络读写
    val workerGroup = new NioEventLoopGroup

    try {
      //是Netty用户启动NIO服务端的辅助启动类，降低服务端的开发复杂度
      val bootStrap = new ServerBootstrap
      //将两个NIO线程组作为参数传入到ServerBootstrap
      bootStrap.group(bossGroup, workerGroup)
        //创建NioServerSocketChannel
        .channel(classOf[NioServerSocketChannel])
        //绑定I/O事件处理类
        .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          //解码器
          ch.pipeline().addLast(new StringDecoder)
          ch.pipeline().addLast(new StringEncoder)
          ch.pipeline().addLast(new ServerHandler)
        }
      })
      //绑定端口，调用sync方法等待绑定操作完成
      val channelFuture = bootStrap.bind(host, port).sync()
      println("服务已开启")
      //等待服务关闭
      channelFuture.channel().closeFuture().sync()

    } finally {
      bossGroup.shutdownGracefully()
      workerGroup.shutdownGracefully()
    }
  }
}

object StringNettyServer {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8083
    val server = new StringNettyServer
    println(s"IP$host,端口号$port")
    server.bind(host, port)
  }
}
