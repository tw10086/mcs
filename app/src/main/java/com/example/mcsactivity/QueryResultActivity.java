package com.example.mcsactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class QueryResultActivity extends AppCompatActivity {
    private List<String> list=new ArrayList<>();
    private Intent intent;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);
        //任务类别
        list.add("take_photo");
        list.add("send_message");
        list.add("take_video");
        //// TODO: 2017/6/25 任务类别[]：replace with yours
        //获取任务
        //// TODO: 2017/6/25 获取任务(): replace with yours
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                QueryResultActivity.this,android.R.layout.simple_list_item_1,list);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("take_photo")){
                    //拍照
                    //// TODO: 2017/6/25 Replace with your own logic
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }else if(list.get(position).equals("send_message")){
                    //发送消息
                    //TODO: sendMessage()
                }else if(list.get(position).equals("take_video")){
                    //拍摄视频
                    //TODO: takeVideo()
                }
            }
        });
    }
}
