package com.example.demo.tio.client;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.Node;
import org.tio.core.Tio;

/**
 * 概述：
 * 功能：
 * 作者：郑肖亚
 * 创建时间：2019/3/10 10:54
 */
public class client {

    /**
     * 断链后自动连接的，不想自动连接请设为null
     */
    private static ReconnConf reconnConf = new ReconnConf(5000L);


    //public static ClientGroupContext clientGroupContext = new ClientGroupContext(new MClientAioHandler(), null, reconnConf);

    public static void main(String[] args) throws Exception {
        //ClientGroupContext clientGroupContext = new ClientGroupContext(new MClientAioHandler(),null);
        ClientGroupContext clientGroupContext = new ClientGroupContext(new MClientAioHandler(), null, reconnConf);

        TioClient tioClient=new TioClient(clientGroupContext);
        ClientChannelContext clientChannelContext=tioClient.connect(new Node("127.0.0.1",8999));
        ClientPacket clientPacket=new ClientPacket();
        clientPacket.setBody("hello,t-tio".getBytes("utf-8"));
        Tio.send(clientChannelContext,clientPacket);
    }
}

















