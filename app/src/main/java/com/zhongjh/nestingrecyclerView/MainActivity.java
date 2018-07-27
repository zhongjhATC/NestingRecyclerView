package com.zhongjh.nestingrecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhongjh.nestingrecyclerView.adapter.ParentInfoAdapter;
import com.zhongjh.nestingrecyclerView.bean.ChildInfo;
import com.zhongjh.nestingrecyclerView.bean.ParentInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 嵌套RecyclerView
 * 分为第一层的RecyclerView,每个Item包含一个RecyclerView
 * 第一层RecyclerView这里称为父RecyclerView，包含的RecyclerView则叫子RecyclerView
 * Created by zhongjh on 2018/7/26.
 */
public class MainActivity extends AppCompatActivity {

    private List<ParentInfo> dataInfoList = new ArrayList<>(); // 数据源

    // 其实RecycledViewPool的内部维护了一个Map，里面以不同的viewType为Key存储了各自对应的ViewHolder集合，所以这边设置了常量防止父适配器和子适配器的ViewType冲突
    public static final int PARENT_VIEW_TYPE = 0;
    public static final int CHILD_VIEW_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
    }

    /**
     * 初始化 适配器数据
     */
    private void initAdapter() {
        for (int i = 0; i < 50; i++) {
            // 创建父实体
            ParentInfo parentInfo = new ParentInfo();
            List<ChildInfo> childInfoList = new ArrayList<>();
            parentInfo.setTitle("大标题");
            parentInfo.setMenuList(childInfoList);
            dataInfoList.add(parentInfo);
            // 创建子实体，每个父实体添加多个子实体
            for (int j = 0; j < 20; j++) {
                ChildInfo childInfo = new ChildInfo();
                childInfo.setName(i + "-" + j);
                childInfoList.add(childInfo);
            }
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        // 创建 ViewHolder的缓存共享池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        RecyclerView recyclerView = findViewById(R.id.rlParent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRecycledViewPool(recycledViewPool);
        // 传递RecycledViewPool共享池进父适配器，让父适配器里面的子适配器也共用同一个共享池
        ParentInfoAdapter adapter = new ParentInfoAdapter(this, dataInfoList, recycledViewPool);
        recyclerView.setAdapter(adapter);
    }
}
