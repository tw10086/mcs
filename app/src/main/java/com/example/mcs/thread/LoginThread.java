package com.example.mcs.thread;

import com.example.mcs.user.Login;

/**
 * Created by towel on 2017/6/23.
 */

public class LoginThread implements Runnable {
    private final String username;
    private final String passwd;
    public LoginThread(){
        //默认用户及密码
        this.username="admin@gmail.com";
        this.passwd="admin";
    }
    public LoginThread(String name,String passwd){
        this.username=name;
        this.passwd=passwd;
    }
    @Override
    public void run(){
        try {
            Login.userLogin(username, passwd);
        }catch(Exception e){
            //// TODO: 2017/6/25 提示用户登录失败/异常
        }
    }
}
