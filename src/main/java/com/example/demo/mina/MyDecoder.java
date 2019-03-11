package com.example.demo.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

/**
 * 概述：自定义解码器
 * 功能：将二进制流转换成java对象  实现： 实现ProtocolDecoder接口或继承ProtocolDecoderAdapter类(难以解决半包，粘包问题)
 *       继承CumulativeProtocolDecoder类，重写doDecode方法。(可解决半包，粘包)
 * 作者：郑肖亚
 * 创建时间：2019/3/6 15:21
 */
public class MyDecoder extends CumulativeProtocolDecoder{
    private final Charset charset;

    public MyDecoder(){
        this.charset=Charset.defaultCharset();
    }

    // 构造方法注入编码格式
    public MyDecoder(Charset charset) {
        this.charset = charset;
    }

    /**
     * 此方法作用：如果读取到的数据长度已经够解码，就返回true.否则返回false.数据长度不够解码就累积到下一次调用。
     * @param ioSession
     * @param ioBuffer
     * @param protocolDecoderOutput
     * @return
     * @throws Exception
     */

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        // 包头的长度
        final int PACK_HEAD_LEN=5;
        // 拆包时如果可读数据的长度小于包头的长度，就不进行操作
        if(ioBuffer.remaining()<PACK_HEAD_LEN){
            return false;
        }
        if(ioBuffer.remaining()>1){
            // 标记设为当前
            ioBuffer.mark();
            // 获取总长度
            //int length=ioBuffer.getInt(ioBuffer.position());
            int length=ioBuffer.getInt(ioBuffer.position());

            // 如果可读取的数据长度 小于 总长度-包头的长度，则结束拆包，等待下一次
            if(ioBuffer.remaining()<(length - PACK_HEAD_LEN)){
                ioBuffer.reset();
                return false;
            }else{
                // 重置，并读取一条完整记录
                ioBuffer.reset();
                byte[] bytes=new byte[length];

                // 获取长度4个字节，版本一个字节，内容
                ioBuffer.get(bytes,0,length);
                byte flag=bytes[4];
                String content=new String(bytes,PACK_HEAD_LEN,length - PACK_HEAD_LEN,charset);
                // 封装自定义的java对象
                MyMsg pack=new MyMsg(flag,content);
                protocolDecoderOutput.write(pack);
                // 如果读取一条时记录后，还存在数据(粘包)，则再次进行调用
                return ioBuffer.remaining()>0;
            }


        }

        return false;
    }
}


























