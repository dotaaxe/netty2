package com.cn.netty.netty.service;

/**
 * 服务Handler 处理
 * @author xzy.ajiu
 * Created by on @date 2020/12/24 9:45
 */

import java.net.InetSocketAddress;

import com.cn.netty.netty.model.SmartIotProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务Handler 处理
 * @author xzy.ajiu
 * Created by on @date 2020/12/24 9:45
 */

public class SmartIotHandler extends SimpleChannelInboundHandler<SmartIotProtocol> {


    private static final Logger log = LoggerFactory.getLogger(SmartIotHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SmartIotProtocol iot)
            throws Exception {
        log.info("收到设备数据包: " + iot.getFlowid());
        iot.printDebugInfo();
        ctx.write("ok");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socket.getAddress().getHostAddress();
        log.info("收到客户端IP: " + ip);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
