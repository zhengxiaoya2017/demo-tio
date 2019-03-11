package com.example.demo.tio.client;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * 概述：
 * 功能：
 * 作者：郑肖亚
 * 创建时间：2019/3/10 11:14
 */
public class MClientAioHandler implements ClientAioHandler{

    @Override
    public Packet heartbeatPacket() {
        return new ClientPacket();
    }

    @Override
    public ClientPacket decode(ByteBuffer byteBuffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {

        //
        if(readableLength<ClientPacket.PACKET_HEADER_LENGTH){
           return null;
        }

        int bodyLength=byteBuffer.getInt();
        if(bodyLength<0){
            throw new AioDecodeException("body length is invalid.remote:" + channelContext.getServerNode());
        }

        int usefulLength=ClientPacket.PACKET_HEADER_LENGTH+bodyLength;
        if(usefulLength>readableLength){
            return null;
        }else{
            ClientPacket packet=new ClientPacket();
            if(bodyLength>0){
                byte[] body=new byte[bodyLength];
                byteBuffer.get(body);
                packet.setBody(body);
            }
            return packet;
        }
    }

    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        ClientPacket clientPacket=(ClientPacket) packet;
        byte[] body=clientPacket.getBody();
        int bodyLength=0;
        if(body !=null){
            bodyLength=body.length;
        }
        int len= ClientPacket.PACKET_HEADER_LENGTH+bodyLength;
        ByteBuffer byteBuffer=ByteBuffer.allocate(len);

        byteBuffer.order(groupContext.getByteOrder());

        byteBuffer.putInt(bodyLength);
        if(body !=null){
            byteBuffer.put(body);
        }
        return byteBuffer;
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        ClientPacket clientPacket=(ClientPacket) packet;
        byte[] body=clientPacket.getBody();
        if(body != null){
            String bodyStr=new String(body,"utf-8");
            System.out.println("客户端收到消息："+bodyStr);
        }









    }
}
