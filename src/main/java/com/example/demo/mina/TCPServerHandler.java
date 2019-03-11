package com.example.demo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;


/**
 * 概述：
 * 功能：MinaServer-handler
 * 作者：郑肖亚
 * 创建时间：2019/3/4 11:18
 */
public class TCPServerHandler extends IoHandlerAdapter{
    // 使用SLF4J作为日志输出。
    //private final static Logger log = LoggerFactory.getLogger(TCPServerHandler.class);

    //重写消息获取方法
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception{
        //String str=(String) message;
        //System.out.println("服务端接收到的消息是["+str+"]");
        MyMsg msg=(MyMsg) message;
        System.out.println("服务端接收消息成功："+msg);
         //MyMsg msg=new MyMsg();
         Date date=new Date();
         msg.setContent("这是来自服务器的应答"+date);
         //通过session.write方法向客户端发数据
         session.write(msg);

/*        if(str.endsWith("quit")){
            session.close(true);
            return;
        }*/
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("服务端会话已经创建...");
        super.sessionCreated(session);
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("服务端会话已经打开...");
        super.sessionOpened(session);
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        MyMsg msg=new MyMsg();
        session.write(msg);
        System.out.println("服务端会话已经关闭...");
        super.sessionClosed(session);
    }
    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        System.out.println("服务端进入空闲状态... " + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("exceptionCaught"+cause);
    }
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("messageSent");
    }
}





















