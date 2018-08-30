package com.dingyl.gankio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingyl.gankio.R;
import com.dingyl.gankio.activity.ContentActivity;
import com.dingyl.gankio.activity.FuliImageActivity;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.entity.GankBeanData;
import com.dingyl.gankio.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class FuliImageAdapter extends RecyclerView.Adapter<FuliImageAdapter.FuliViewHolder> {

    private final static String TAG = "FuliImageAdapter";
    private List<FuliCategory.FuliBeans> fuliBeansList;
    private List<AndroidCategory.AndroidBean> androidBeanList;
    private ArrayList<AndroidCategory.AndroidBean> androidBeansListContent;

    private Context context;
    private String publishAt;

    public FuliImageAdapter(GankBeanData gankBeanData, Context context){
        this.fuliBeansList = gankBeanData.getFuliBeans();
        this.androidBeanList = gankBeanData.getAndroidBeans();
        androidBeansListContent = new ArrayList<>();
        this.context = context;
    }

    @Override
    public FuliViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuli_item,parent,false);
        FuliViewHolder holder = new FuliViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FuliViewHolder holder, final int position) {
        Tools.loadImage(context,fuliBeansList.get(position).getUrl(),holder.fuliItemImage);
        //Log.d(TAG,"fuliCategoryList.get(position).getUrl()" + fuliBeansList.get(position).getUrl());
        publishAt = fuliBeansList.get(position).getPublishedAt().substring(0,10);
        holder.fuliItemDate.setText(fuliBeansList.get(position).getDesc());
        for(AndroidCategory.AndroidBean bean:androidBeanList){
            if(bean.getPublishedAt().contains(publishAt)){
                holder.fuliItemContent.setText(bean.getDesc());
                Log.d(TAG,"contains publishAt,publishAt is : " + publishAt);
                break;
            }
        }
        for(AndroidCategory.AndroidBean bean:androidBeanList){
            if(bean.getPublishedAt().contains(publishAt)){
                androidBeansListContent.add(bean);
            }
        }
        //TODO to set content summary
        holder.fuliItemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.setAction("intent_action_content");
                intent.putExtra("content_list",androidBeansListContent);
                context.startActivity(intent);
            }
        });
        holder.fuliItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FuliImageActivity.class);
                intent.putExtra("fuli_url",fuliBeansList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuliBeansList.size();
    }

    class FuliViewHolder extends RecyclerView.ViewHolder{

        ImageView fuliItemImage;
        TextView fuliItemDate;
        TextView fuliItemContent;
        LinearLayout contentLayout;

        public FuliViewHolder(View itemView) {
            super(itemView);
            fuliItemImage = itemView.findViewById(R.id.fuli_item_image);
            fuliItemDate = itemView.findViewById(R.id.fuli_item_date);
            fuliItemContent = itemView.findViewById(R.id.fuli_item_content);
            contentLayout = itemView.findViewById(R.id.android_content_layout);
        }
    }
}
