package com.example.mcs.io;

import com.example.mcs.Global;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by towel on 2017/6/23.
 */

public class IOStream {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public IOStream(){
        //// TODO: 2017/6/25 ...
    }
    public IOStream(Socket socket) {
        this.socket = socket;
    }
    public void LoginOutput(String str1,String str2) throws IOException{
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeBytes(str1+"\n"+str2);
        dataOutputStream.flush();
        socket.shutdownOutput();
    }

    public byte[] LoginInput() throws IOException{
        byte[] buf=new byte[Global.BUFSIZE];
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataInputStream.read(buf);
        return buf;
    }

    public void queryOutput(String taskStr)throws IOException{
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeBytes(taskStr);
        dataOutputStream.flush();
        socket.shutdownOutput();
    }

    public byte[] queryInput()throws IOException{
        byte[] buf=new byte[Global.BUFSIZE];
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataInputStream.read(buf);
        return buf;
    }
}
