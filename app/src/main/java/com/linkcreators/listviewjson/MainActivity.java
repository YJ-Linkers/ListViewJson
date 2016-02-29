package com.linkcreators.listviewjson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linkcreators.listviewjson.adapter.MainListViewAdapter;
import com.linkcreators.listviewjson.bean.JSONTools;
import com.linkcreators.listviewjson.bean.JsonBean;
import com.linkcreators.listviewjson.bean.URLs;
import com.linkcreators.listviewjson.listview.MyListView;
import com.linkcreators.listviewjson.utils.HttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyListView listView;
    private List<JsonBean> jsonBeanList = new ArrayList<>();
    private MainListViewAdapter mainListViewAdapter;

    private ProgressDialog selectorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (MyListView) findViewById(R.id.myListView);
        selectorDialog = ProgressDialog.show(this, null, "正在加载，请稍候...", true,
                false);

        initData();
    }

    private void initData() {
        selectorDialog.show();
        new Thread(){
            @Override
            public void run()
            {
                Message msg = new Message();
         //       boolean isRefresh = false;
                String jsonString = HttpUtils.getJsonContent(URLs.NEWS_LIST);
                Log.e("===========", jsonString);
                try {
                    jsonBeanList = JSONTools.getJsonObject(jsonString);
                    Log.e("++++++++++", jsonBeanList.get(1).getTitle());
                    if(jsonBeanList.size() > 0){
                        Log.e("size", String.valueOf(jsonBeanList.size()));
                        msg.what = 1;
               //         msg.obj = jsonBeanList;
                    }else{
                        msg.what = -1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = -2;
                    msg.obj = e;
                }
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            selectorDialog.cancel();
            if(msg.what == 1){
                mainListViewAdapter = new MainListViewAdapter(MainActivity.this, jsonBeanList);
                setListViewHeightBasedOnChildren(listView);
                listView.setAdapter(mainListViewAdapter);
                //        xListView.setAdapter(mainListViewAdapter);

            }else if(msg.what == -1){
                Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_LONG).show();
            }else if(msg.what == -2){
                Toast.makeText(getApplicationContext(), "解析失败", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void setListViewHeightBasedOnChildren(MyListView myListView) {
        // 获取ListView对应的Adapter
        MainListViewAdapter mainListViewAdapter = (MainListViewAdapter) myListView.getAdapter();
        if (mainListViewAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = mainListViewAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = mainListViewAdapter.getView(i, null, myListView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight+ (myListView.getDividerHeight() * (mainListViewAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        myListView.setLayoutParams(params);
    }
}
