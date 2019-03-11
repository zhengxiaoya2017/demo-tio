package com.example.demo.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * 概述：编码器类
 * 功能：将java对象转换成二进制流  实现：通过继承ProtocolEncoderAdapter类或实现ProtocolEncoder接口
 * 作者：郑肖亚
 * 创建时间：2019/3/5 21:50
 */
public class MyEncoder extends ProtocolEncoderAdapter{

    private final Charset charset;

    public MyEncoder(){
        this.charset=Charset.defaultCharset();
    }


    //构造方法注入编码格式
    public MyEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        // 转为自定义协议包
        MyMsg msg=(MyMsg) message;

        // 初始化缓冲区
        IoBuffer buffer=IoBuffer.allocate(msg.getLength()).setAutoExpand(true);

        CharsetEncoder ce= Charset.forName("utf-8").newEncoder();
        // Mina IoBuffer
       // IoBuffer buffer=IoBuffer.allocate(100).setAutoExpand(true);

        //MyMsg msg=new MyMsg();
        // 设置长度，报头，内容

        buffer.putInt(msg.getLength());
        //buffer.putLong(msg.getSender());
        buffer.put(msg.getFlag());
        if(msg.getContent()!=null){
            buffer.putString(msg.getContent(),ce);
        }


        buffer.flip();
        protocolEncoderOutput.write(buffer);


    }

    @Override
    public void dispose(IoSession session) throws Exception{

    }
}





















