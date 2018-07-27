package com.zhongjh.nestingrecyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongjh.nestingrecyclerView.MainActivity;
import com.zhongjh.nestingrecyclerView.R;
import com.zhongjh.nestingrecyclerView.bean.ChildInfo;

import java.util.List;


/**
 * 子适配器
 * Created by zhongjh on 2018/7/26.
 */
public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoAdapter.ViewHolder> {

    private Context context;
    private List<ChildInfo> list; // 数据源

    ChildInfoAdapter(Context context, List<ChildInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.CHILD_VIEW_TYPE;
    }

    public void setData(List<ChildInfo> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_child, parent, false);
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
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textInfo;

        ViewHolder(View itemView) {
            super(itemView);
            textInfo = itemView.findViewById(R.id.tvChildTitle);
        }
    }


}
