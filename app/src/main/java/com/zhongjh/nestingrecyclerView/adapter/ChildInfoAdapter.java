package com.zhongjh.nestingrecyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongjh.nestingrecyclerView.R;
import com.zhongjh.nestingrecyclerView.bean.ChildInfo;

import java.util.List;


/**
 * 内部的RecyclerView
 * 内容为：
 * imageView + textView
 * Created by gaoshiwei on 2017/9/19.
 */

public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoAdapter.ViewHolder> {

    private Context context;
    private List<ChildInfo> list; // List 集合（里面是image+text）

    /**
     * 构造函数
     * @param context
     * @param list
     */
    public ChildInfoAdapter(Context context, List<ChildInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChildInfo info = list.get(position);
        holder.textInfo.setText(info.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * static ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageInfo;
        TextView textInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            imageInfo = (ImageView) itemView.findViewById(R.id.image_info);
            textInfo = (TextView) itemView.findViewById(R.id.text_info);
        }
    }


}
