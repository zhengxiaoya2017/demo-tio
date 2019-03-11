package com.example.demo.server;

import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;

import java.io.IOException;

/**
 * 概述：
 * 功能：tio-server
 * 作者：郑肖亚
 * 创建时间：2019/3/8 11:05
 */
public class server {
    public static void main (String[] args) throws IOException {
        ServerGroupContext serverGroupContext = new ServerGroupContext("tio-server", new MServerAioHandler(), null);
        TioServer server=new TioServer(serverGroupContext);
        server.start("127.0.0.1",8999);
    }
}









