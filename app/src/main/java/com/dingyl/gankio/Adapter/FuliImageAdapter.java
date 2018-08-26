package com.dingyl.gankio.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingyl.gankio.R;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.utils.Tools;

import java.util.List;

public class FuliImageAdapter extends RecyclerView.Adapter<FuliImageAdapter.FuliViewHolder> {

    private final static String TAG = "FuliImageAdapter";
    private List<FuliCategory.FuliBeans> fuliCategoryList;
    private Context context;

    public FuliImageAdapter(List<FuliCategory.FuliBeans> fuliCategoryList,Context context){
        this.fuliCategoryList = fuliCategoryList;
        this.context = context;
    }

    @Override
    public FuliViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuli_item,parent,false);
        FuliViewHolder viewHolder = new FuliViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FuliViewHolder holder, int position) {
        Tools.loadImage(context,fuliCategoryList.get(position).getUrl(),holder.fuliItemImage);
        Log.d(TAG,"fuliCategoryList.get(position).getUrl()" + fuliCategoryList.get(position).getUrl());
        holder.fuliItemDate.setText(fuliCategoryList.get(position).getDesc());
        holder.fuliItemContent.setText(fuliCategoryList.get(position).getDesc());
        //TODO to set content summary
    }

    @Override
    public int getItemCount() {
        return fuliCategoryList.size();
    }

    class FuliViewHolder extends RecyclerView.ViewHolder{

        ImageView fuliItemImage;
        TextView fuliItemDate;
        TextView fuliItemContent;

        public FuliViewHolder(View itemView) {
            super(itemView);
            fuliItemImage = itemView.findViewById(R.id.fuli_item_image);
            fuliItemDate = itemView.findViewById(R.id.fuli_item_date);
            fuliItemContent = itemView.findViewById(R.id.fuli_item_content);
        }
    }
}
