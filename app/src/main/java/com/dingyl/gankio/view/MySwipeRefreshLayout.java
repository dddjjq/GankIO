package com.dingyl.gankio.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private static final String TAG = "MySwipeRefreshLayout";
    private int mScaledTouchSlop ;
    private RecyclerView recyclerView;
    private float downY,upY;
    private LoadDataListener loadDataListener;

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public void onLayout(boolean changed,int left,int top,int right,int bottom){
        super.onLayout(changed,left,top,right,bottom);
        setRecyclerViewScroll();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent mv){
        switch (mv.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY  = mv.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(canLoadMore()){
                    loadDataListener.loadData();
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = mv.getY();
                break;
        }
        return super.dispatchTouchEvent(mv);
    }

    private boolean canLoadMore(){
        Log.d(TAG,"canLoadMore");
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }

    private void setRecyclerViewScroll(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(canLoadMore())
                    loadDataListener.loadData();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setContentRecycler(RecyclerView recycler){
        this.recyclerView = recycler;
    }

    public interface LoadDataListener{
        void loadData();
    }

    public void setLoadDataListener(LoadDataListener loadDataListener){
        this.loadDataListener = loadDataListener;
    }
}
