package com.example.demo.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

/**
 * 概述：
 * 功能：自定义编解码工厂类
 * 作者：郑肖亚
 * 创建时间：2019/3/6 15:49
 */
public class CustomProtocolCodecFactory  implements ProtocolCodecFactory{
    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    public CustomProtocolCodecFactory(){
        this(Charset.forName("UTF-8"));
    }

    // 构造方法注入编解码器
    public CustomProtocolCodecFactory(Charset charset) {
        this.encoder = new MyEncoder(charset);
        this.decoder = new MyDecoder(charset);
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
