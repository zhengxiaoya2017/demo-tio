package com.example.demo.tio.server;

import org.tio.core.intf.Packet;

/**
 * 概述：
 * 功能：
 * 作者：郑肖亚
 * 创建时间：2019/3/8 16:04
 */
public class ServerPacket extends Packet {
    public static final Integer PACKET_HEADER_LENGTH=4;
    public static final Integer PORT=8999;

    byte[] body; //数据

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }


}
