package com.zhongjh.nestingrecyclerView.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongjh.nestingrecyclerView.MainActivity;
import com.zhongjh.nestingrecyclerView.R;
import com.zhongjh.nestingrecyclerView.bean.ChildInfo;
import com.zhongjh.nestingrecyclerView.bean.ParentInfo;

import java.util.List;


/**
 * 父适配器
 *
 * @author zhongjh
 * @date 2018/7/26
 */
public class ParentInfoAdapter extends RecyclerView.Adapter<ParentInfoAdapter.ViewHolder> {

    private final Context context;
    private final List<ParentInfo> list;
    /***
     * 从MainActivity传递过来的共享池
     */
    private final RecyclerView.RecycledViewPool recycledViewPool;

    public ParentInfoAdapter(Context context, List<ParentInfo> list, RecyclerView.RecycledViewPool recycledViewPool) {
        this.context = context;
        this.list = list;
        this.recycledViewPool = recycledViewPool;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_parent, parent, false);
        Log.d("onCreateViewHolder","创建父item");
        return new ViewHolder(view, recycledViewPool);
    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.PARENT_VIEW_TYPE;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText("第 " + position + "个");
        // 获取当前最新数据，防止数据错乱
        List<ChildInfo> childInfos = list.get(position).getMenuList();
        // 先判断一下是不是已经设置了Adapter
        if (holder.mRecyclerView.getAdapter() == null) {
            holder.mRecyclerView.setAdapter(new ChildInfoAdapter(context, childInfos));
        } else {
            ((ChildInfoAdapter) holder.mRecyclerView.getAdapter()).setData(childInfos);
            holder.mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;//标题
        RecyclerView mRecyclerView; //子RecyclerView

        ViewHolder(View itemView, RecyclerView.RecycledViewPool recycledViewPool) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvParentTitle);
            mRecyclerView = itemView.findViewById(R.id.rlChild);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
            manager.setRecycleChildrenOnDetach(true);
            // 嵌套的子RecyclerView,需要将LinearLayoutManager设置setAutoMeasureEnabled(true)成自适应高度
            manager.setAutoMeasureEnabled(true);
            // 子RecyclerView没必要滚动本身
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setLayoutManager(manager);
            // 子RecyclerView现在和父RecyclerView在同一个共享池了
            mRecyclerView.setRecycledViewPool(recycledViewPool);
        }
    }
}
