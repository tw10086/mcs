package com.example.mcsactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcs.Global;
import com.example.mcs.HandleImage;
import com.example.mcs.thread.UploadThread;

/**
 * Created by towel on 2017/6/25.
 */

public class MainActivity extends AppCompatActivity {
    //public static final int TAKE_PHOTO = 1;
    //public static final int UPLOAD_PHOTO = 2;
    private Uri imageUri;
    private TextView tvInformation;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            int arg1=msg.arg1;
            int arg2=msg.arg2;
            int what=msg.what;
            Object obj=msg.obj;
            String task;
            switch (what){
                case Global.CONNECTED:
                    tvInformation.append("已连接服务器...\n");
                    break;
                case Global.DISCONNECTED:
                    tvInformation.append("连接失败...\n");
                    break;
                case Global.LOGIN:
                    tvInformation.append("登陆成功...\n");
                    String str=msg.getData().getString("login").toString();
                    tvInformation.append(str+"\n");
                    break;
                case Global.LOGINFAILED:
                    tvInformation.append("登陆失败...\n");
                case Global.QUERY:
                    tvInformation.append("任务:");
                    task=msg.getData().getString("task").toString();
                    tvInformation.append(task+"\n");
                    break;
                case Global.QUERYFAILED:
                    tvInformation.append("查询任务失败...\n");break;
                case Global.UPLOAD:
                    tvInformation.append("上传成功...\n");break;
                case Global.UPLOADFAILED:
                    tvInformation.append("上传失败...\n");break;
                case Global.UNKNOWHOST:
                    //// TODO: 2017/6/25  ...
                    break;
                case Global.TIMEOUT:
                    //// TODO: 2017/6/25  ...
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button btUpload = (Button) findViewById(R.id.upload);
        Button btlogin=(Button)findViewById(R.id.login);
        Button btquery=(Button)findViewById(R.id.query);
        tvInformation=(TextView)findViewById(R.id.tvInformation);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/6/25 take_photo:Replace with your own logic
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, Global.TAKEPHOTO);
            }
        });
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,Global.LOGIN);
            }
        });
        btquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,QueryResultActivity.class);
                startActivityForResult(intent,Global.QUERY);
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, Global.UPLOADPHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Global.TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    //// TODO: 2017/6/25  take_photo:Replace with your own logic
                }
                break;
            case Global.UPLOADPHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        uploadImage(HandleImage.handleImageOnKitKat(this,data));
                    } else {
                        uploadImage(HandleImage.handleImageBeforeKitKat(data));
                    }
                }
                break;
            case Global.LOGIN:
                if(resultCode==RESULT_OK){
                    String str=data.getStringExtra("login");
                    Bundle bundle=new Bundle();
                    bundle.putString("login",str);
                    Message message=new Message();
                    message.what=Global.LOGIN;
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
                break;
            case Global.QUERY:
                break;
            default:
                break;
        }
    }

    private void uploadImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            new Thread(new UploadThread(bitmap)).start();
        } else {
            //// TODO: 2017/6/25 上传失败:Replace with your own logic
        }
    }



}
