package com.linkcreators.listviewjson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkcreators.listviewjson.R;
import com.linkcreators.listviewjson.bean.JsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun on 2015/11/25.
 */
public class MainListViewAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Context context;
    private ViewHolder viewHolder;
    private List<JsonBean> jsonBeanList = new ArrayList<>();

    public MainListViewAdapter(Context context, List<JsonBean> jsonBeanList) {
        this.context = context;
        this.jsonBeanList = jsonBeanList;
        inflater = LayoutInflater.from(context);
//        this.bitmapManager = new BitmapManager(BitmapFactory.decodeResource(
//                context.getResources(), R.drawable.umeng_socialize_share_pic));
    }

    @Override
    public int getCount() {
        return jsonBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.home_listview_title_tv);
            viewHolder.time = (TextView) convertView.findViewById(R.id.home_listview_time_tv);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.home_listview_thumb_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(jsonBeanList.get(position).getTitle());
        viewHolder.time.setText(jsonBeanList.get(position).getPublishTime());
        String imgURL = jsonBeanList.get(position).getFirstPicUrl();
        viewHolder.img.setImageResource(R.drawable.umeng_socialize_share_pic);

//        if(imgURL.endsWith("portrait.gif") || StringUtils.isEmpty(imgURL)){
//            viewHolder.img.setImageResource(R.drawable.umeng_socialize_share_pic);
//        }else{
//            if (!imgURL.contains("http")) {
//                imgURL = URLs.HTTP + URLs.HOST + "/" + imgURL;
//            }
//  //          bitmapManager.loadBitmap(imgURL, viewHolder.img);
//        }

        return convertView;
    }

    private class ViewHolder {
        private TextView title;
        private TextView time;
        private ImageView img;
    }
}
