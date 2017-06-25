package com.example.mcs.user;

import com.example.mcs.Global;
import com.example.mcs.io.IOStream;
import com.example.mcs.socket.TCPSocket;

/**
 * Created by towel on 2017/6/23.
 */

public class CustomerQuery {
    //任务查询
    public static byte[] taskQuery() throws Exception {
        //// TODO: 2017/6/25 Replace with your own logic
        byte[] buf = new byte[Global.BUFSIZE];
        TCPSocket tcpSocket = new TCPSocket(Global.IPaddr, Global.port);
        tcpSocket.connect();
        IOStream ioStream = new IOStream(tcpSocket.getSocketHandler());

        //// TODO: 2017/6/25 客户端与服务器约定通信协议，采用协议查询信息

        //以下模拟任务查询
        ioStream.queryOutput("getTaskByUserLocation");
        buf = ioStream.queryInput();
        tcpSocket.disconnect();
        return buf;
    }
    //// TODO: 2017/6/25  信息查询():Create a method-infoQuery() with your own logic
}

