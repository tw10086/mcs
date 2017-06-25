package com.example.mcs.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by towel on 2017/6/23.
 */

public class TCPSocket {
    private String IPAddr;
    private Socket socket;
    private int port;
    public TCPSocket(String IPAddr,int port){
        this.IPAddr=IPAddr;
        this.port=port;
    }
    public void connect() throws IOException{
        InetAddress serverAddr=InetAddress.getByName(IPAddr);
        socket=new Socket(serverAddr,port);
    }

    public void disconnect() throws Exception{
        socket.close();
    }
    public Socket getSocketHandler(){
        return socket;
    }
}
