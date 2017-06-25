package com.example.mcs.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.mcs.Global;
import com.example.mcs.user.CustomerQuery;

/**
 * Created by towel on 2017/6/23.
 */

public class QueryThread implements Runnable {
    private Handler handler;
    private Message message;
    private Bundle bundle;
    public QueryThread(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run(){
        bundle=new Bundle();
        message=new Message();
        byte[] buf;
        try {
            buf=CustomerQuery.taskQuery();
            bundle.putString("task",new String(buf,0,buf.length));
            message.what= Global.QUERY;
            message.setData(bundle);
            handler.sendMessage(message);
        }catch(Exception e){
            //// TODO: 2017/6/25 提示用户任务查询失败
        }
    }
}
