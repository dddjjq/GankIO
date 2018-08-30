package com.dingyl.gankio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingyl.gankio.R;
import com.dingyl.gankio.entity.AndroidCategory;

import java.util.ArrayList;

public class ContentListAdapter extends BaseAdapter{

    private ArrayList<AndroidCategory.AndroidBean> androidBeanArrayList;
    private LayoutInflater inflater;

    public ContentListAdapter(Context context,ArrayList<AndroidCategory.AndroidBean> androidBeanArrayList){
        this.androidBeanArrayList  = androidBeanArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return androidBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return androidBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.content_list_item,parent,false);
            holder = new ViewHolder();
            holder.contentText = convertView.findViewById(R.id.content_list_item);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.contentText.setText(androidBeanArrayList.get(position).getDesc());
        return convertView;
    }

    class ViewHolder{
        private TextView contentText;
    }
}
