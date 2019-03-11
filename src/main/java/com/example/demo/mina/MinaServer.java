package com.example.demo.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 概述：
 * 功能：Mina服务端类
 * 作者：郑肖亚
 * 创建时间：2019/3/4 12:58
 */
public class MinaServer {
    public static void main(String[] args) throws IOException {

        try {

            int PORT=9124;
            //1 初始化服务端的TCP/IP的基于NIO的套接字
            IoAcceptor acceptor=new NioSocketAcceptor();
            //调用IoSessionConfig设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            //读写通道在10秒内无任何操作后就进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,30);

            //2 编写过滤器(测试处理最简单的字符串传输，使用mina提供的TextLineCodecFactory编码器工厂对字符串进行编码处理)
    /*        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory
                    (Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue()))
            );*/
            LoggingFilter lf = new LoggingFilter();
/*            lf.setMessageSentLogLevel(LogLevel.WARN);
            lf.setMessageReceivedLogLevel(LogLevel.WARN);
            lf.setSessionCreatedLogLevel(LogLevel.WARN);*/
           // acceptor.getFilterChain().addLast("logger",lf);
            //
            acceptor.getFilterChain().addLast("myCoder",new ProtocolCodecFilter(new CustomProtocolCodecFactory
                    (Charset.forName("UTF-8")))
            );






            //3 设置handler 编写IoHandler
            acceptor.setHandler(new TCPServerHandler());
            //绑定端口
            acceptor.bind(new InetSocketAddress(PORT));
            System.out.println("Mina-Server Running on port: 9124");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
