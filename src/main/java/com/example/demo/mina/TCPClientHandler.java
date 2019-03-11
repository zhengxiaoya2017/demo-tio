package com.example.demo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 概述：
 * 功能：MinaClient-handler
 * 作者：郑肖亚
 * 创建时间：2019/3/4 13:04
 */
public class TCPClientHandler extends IoHandlerAdapter{
    private final static Logger logger= LoggerFactory.getLogger(TCPClientHandler.class);
/*    private final String values;
    public TCPClientHandler(String values){
       this.values=values;
    }*/
    @Override
    public void sessionOpened(IoSession session){
        System.out.println("客户端已经连接到了服务器...");
        //session.write(values);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }
    @Override
    public void messageReceived(IoSession session,Object message) throws Exception {
        super.messageReceived(session,message);
        System.out.println(String.format("来自Server[%s]的消息:%s",session.getRemoteAddress(),message));
    }

/*    private String parseMessage(Object message){
        IoBuffer buf = (IoBuffer) message;
        IoBuffer.allocate(1024);
        //长度超过会自动翻倍增长
        buf.setAutoExpand(true);
        ByteBuffer bf = buf.buf();
        byte[] tmpBuffer = new byte[bf.limit()];
        bf.get(tmpBuffer);
        String result = "";
        for(int   i=0;   i <tmpBuffer.length;   i++)   {
            //转换16进制
            String getM = Integer.toHexString(tmpBuffer[i] & 0xFF)+"";
            if(getM.length()<2){
                getM="0"+getM;
            }
            result+=getM+" ";
        }
        return result;

    }*/









}
