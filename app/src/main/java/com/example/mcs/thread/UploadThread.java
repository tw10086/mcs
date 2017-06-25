package com.example.mcs.thread;

import android.graphics.Bitmap;

import com.example.mcs.Global;
import com.example.mcs.socket.TCPSocket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by towel on 2017/6/24.
 */

public class UploadThread implements Runnable {
    private Bitmap bitmap;
    public UploadThread(Bitmap bitmap){
        this.bitmap=bitmap;
    }
    @Override
    public void run(){
        TCPSocket tcpSocket=new TCPSocket(Global.IPaddr, Global.port);
        try{
            tcpSocket.connect();
            Socket socket=tcpSocket.getSocketHandler();
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(socket.getOutputStream());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            bufferedOutputStream.flush();
            tcpSocket.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
