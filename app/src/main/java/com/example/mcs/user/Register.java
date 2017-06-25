package com.example.mcs.user;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by towel on 2017/6/24.
 */

public class Register {
    public static boolean exist(String email,Socket socket)throws IOException{
        ////TODO: 判断用户是否存在——replace with your own logic
        socket.close();
        return true;
    }
    public static void register(String email,String passwd,Socket socket){
        ////TODO:注册用户——replace with your own logic
    }
    public static Boolean registerAndlogin(String email,String passwd,Socket socket)throws IOException{
        register(email,passwd,socket);
        return Login.customerLogin(email,passwd,socket);
    }
}
