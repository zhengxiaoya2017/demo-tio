package com.example.demo.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 概述：
 * 功能：Mina客户端类
 * 作者：郑肖亚
 * 创建时间：2019/3/4 12:58
 */
public class TCPClient{
    private static String host="127.0.0.1";
    private static int port=9124;
    public static void main(String[] args) {
            testClient();
    }
    public static void testClient(){

        LoggingFilter lf = new LoggingFilter();
       // lf.setSessionOpenedLogLevel(LogLevel.INFO);

        //acceptor.getFilterChain().addLast("logger", lf);


        IoConnector connector=new NioSocketConnector();


        //connector.getFilterChain().addLast("logger", lf);

        connector.setConnectTimeoutMillis(30000);

/*        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                .forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
                LineDelimiter.WINDOWS.getValue())));*/
        connector.getFilterChain().addLast("myCoder",new ProtocolCodecFilter(new CustomProtocolCodecFactory
                (Charset.forName("UTF-8")))
        );

        connector.setHandler(new TCPClientHandler());
        ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
        // 采用监听的方式获取session
        future.addListener(new IoFutureListener<IoFuture>() {
            // 当链接创建完成
            @Override
            public void operationComplete(IoFuture ioFuture) {
                IoSession session=ioFuture.getSession();
                sendData(session);
            }
        });




        //等待是否连接成功，转异步操作为同步操作。
        future.awaitUninterruptibly();

        //连接成功后获取会话对象。
        //future.getSession().write("你好！msg");
        future.getSession().getCloseFuture().awaitUninterruptibly(); //等待关闭连接
        //	等待28000毫秒后连接断开
        /*future.getSession().getCloseFuture().awaitUninterruptibly(28000);*/
        //	关闭连接
        connector.dispose();
        //System.out.println(session);
        System.out.println("执行中========>");
    }


    public static void sendData(IoSession session){
        System.out.println("----------------------------------测试数据准备发送----------------");
        // 模拟3次数据发送
        for(int i=0;i<4;i++){
            String content="测试数据123456："+ i;
            MyMsg pack=new MyMsg( (byte) i,content);
            System.out.println(pack);
            session.write(pack);
        }
        System.out.println("----------------------------------测试数据发送完毕-----------------");
    }





}



















