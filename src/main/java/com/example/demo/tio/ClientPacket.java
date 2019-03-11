package com.example.demo.tio;

import org.tio.core.intf.Packet;

/**
 * 概述：
 * 功能：
 * 作者：郑肖亚
 * 创建时间：2019/3/10 10:59
 */
public class ClientPacket extends Packet {
    public static final Integer PACKET_HEADER_LENGTH=4;
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
