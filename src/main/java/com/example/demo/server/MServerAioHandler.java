package com.example.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;

/**
 * 概述：
 * 功能：
 * 作者：郑肖亚
 * 创建时间：2019/3/8 15:59
 */
public class MServerAioHandler implements ServerAioHandler {
    private static final Logger logger= LoggerFactory.getLogger(MServerAioHandler.class);
    @Override
    public ServerPacket decode(ByteBuffer byteBuffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
        logger.debug("inside decode...");
        if(readableLength < ServerPacket.PACKET_HEADER_LENGTH){
           return null;
        }

        int bodyLength=byteBuffer.getInt();
        if(bodyLength<0){
            throw new AioDecodeException("body length[" + bodyLength + "] is invalid. romote: " + channelContext.getServerNode());
        }

        int len=bodyLength+ServerPacket.PACKET_HEADER_LENGTH;
        if(len>readableLength){
            return null;
        }else{
            byte[] bytes=new byte[len];
            int i=0;
            while(true){
                if (byteBuffer.remaining() ==0){
                    break;
                }
                byte b=byteBuffer.get();
                bytes[i++]=b;
            }

            ServerPacket serverPacket=new ServerPacket();
            serverPacket.setBody(bytes);
            return serverPacket;
        }
    }

    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        logger.info("inside encode...");
        ServerPacket serverPacket=(ServerPacket) packet;
        byte[] body=serverPacket.getBody();
         int bodyLength=0;
         if(body !=null){
             bodyLength=body.length;
         }

         ByteBuffer byteBuffer=ByteBuffer.allocate(bodyLength+ServerPacket.PACKET_HEADER_LENGTH);
         byteBuffer.order(groupContext.getByteOrder());
         byteBuffer.putInt(bodyLength);
         if(body !=null){
             byteBuffer.put(body);
         }


        return byteBuffer;
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
              logger.debug("inside handler...");
              channelContext.setServerNode(new Node("127.0.0.1",ServerPacket.PORT));

              ServerPacket serverPacket=(ServerPacket) packet;

              byte[] body=serverPacket.getBody();
              if(body !=null){
                  String bodyStr=new String (body,"utf-8");
                  bodyStr=bodyStr.replaceAll("[\u0000]", "");
                  if(bodyStr==null||bodyStr.equals("")){
                    return;
                  }else{
                      ServerPacket serverPacket1=new ServerPacket();
                      serverPacket1.setBody(("receive from ["+channelContext.getClientNode()+"]:"+bodyStr).getBytes("utf-8"));
                      Tio.send(channelContext,serverPacket1);
                  }

              }




    }

















}
