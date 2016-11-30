package com.wenoun.based.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenoun.based.JObject;

import java.util.ArrayList;

/**
 * Created by JeyHoon on 2016. 11. 28..
 */

public abstract class JRecyclerAdapter<VH extends JRecyclerAdapter.JViewHolder, O extends JObject> extends RecyclerView.Adapter<VH> {
    protected Context ctx=null;
    private ArrayList<O> dataList=new ArrayList<>();
    private JRecyclerAdapterInterface.OnItemClickListener onItemClickListener=new JRecyclerAdapterInterface.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

        }
    };
    private JRecyclerAdapterInterface.OnItemLongClickListener onItemLongClickListener=new JRecyclerAdapterInterface.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(View view, int position) {
            return false;
        }
    };

    public JRecyclerAdapter(Context ctx, ArrayList<O> dataList, JRecyclerAdapterInterface.OnItemClickListener onItemClickListener, JRecyclerAdapterInterface.OnItemLongClickListener onItemLongClickListener) {
        if (null == dataList) {
            throw new IllegalArgumentException(
                    "DataList must not be null");
        }
        this.ctx = ctx;
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public JRecyclerAdapter(Context ctx, ArrayList<O> dataList, JRecyclerAdapterInterface.OnItemClickListener onItemClickListener) {
        if (null == dataList) {
            throw new IllegalArgumentException(
                    "DataList must not be null");
        }
        this.ctx = ctx;
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    public JRecyclerAdapter(Context ctx, ArrayList<O> dataList) {
        if (null == dataList) {
            throw new IllegalArgumentException(
                    "DataList must not be null");
        }
        this.ctx = ctx;
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(VH holder,int position) {
        final int itemPosition=position;
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,itemPosition);
            }
        });
        holder.getRootView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onItemLongClickListener.onItemLongClick(view,itemPosition);
            }
        });
        onBindJViewHolder(holder, position);
    }
    public abstract void onBindJViewHolder(VH holder,int position);

    public O getItem(final int position){
        return dataList.get(position);
    }
//
//    @Override
//    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class JViewHolder extends RecyclerView.ViewHolder{
        View itemView=null;
        View rootView=null;
        public JViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            rootView=itemView;
        }

        public View getRootView(){return rootView;}

        public View findViewById(final int viewId){return itemView.findViewById(viewId);}

    }
    public void setOnItemClickListener(JRecyclerAdapterInterface.OnItemClickListener itemClickListener){this.onItemClickListener=itemClickListener;}
    public void setOnItemLongClickListener(JRecyclerAdapterInterface.OnItemLongClickListener itemLongClickListener){this.onItemLongClickListener=itemLongClickListener;}
}
