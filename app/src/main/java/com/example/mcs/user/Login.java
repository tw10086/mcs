package com.example.mcs.user;
import com.example.mcs.Global;
import com.example.mcs.io.IOStream;
import com.example.mcs.socket.TCPSocket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by towel on 2017/6/23.
 */

public class Login{
    private static final String IPaddr= Global.IPaddr;
    private static final int port= Global.port;
    /*
     *TODO: Replace this with your own logic
     *
     */
    public static void userLogin(String name,String passwd) throws Exception{
        //以下模拟用户登陆
        TCPSocket tcpSocket=new TCPSocket(IPaddr,port);
        byte[] buf;
        tcpSocket.connect();
        IOStream ioStream=new IOStream(tcpSocket.getSocketHandler());
        ioStream.LoginOutput(name,passwd);
        buf=ioStream.LoginInput();
        //check(buf)
        //验证是否登陆成功
        //if(登陆成功)
        Global.islogin=true;
        tcpSocket.disconnect();
    }
    public static boolean customerLogin(String email,String passwd,Socket socket)throws IOException{
        //验证用户是否存在
        //提交用户名及密码
        //验证是否登陆成功
        //TODO:Replace with your own logic
        /*for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(mPassword);
            }
        }*/
        socket.close();
        //if登陆成功
        return true;
    }
}
