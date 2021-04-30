package com.zhongjh.nestingrecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    /**
     * 数据源
     */
    private List<ParentInfo> dataInfoList = new ArrayList<>();

    /**
     * 其实RecycledViewPool的内部维护了一个Map，里面以不同的viewType为Key存储了各自对应的ViewHolder集合，所以这边设置了常量防止父适配器和子适配器的ViewType冲突
     */
    public static final int PARENT_VIEW_TYPE = 0;
    public static final int CHILD_VIEW_TYPE = 1;

    /**
     * 为何做个全局，是为了Activity销毁时clear
     */
    RecyclerView.RecycledViewPool mRecycledViewPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        mRecycledViewPool.clear();
        super.onDestroy();
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
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool() {
            @Override
            public RecyclerView.ViewHolder getRecycledView(int viewType) {
                if (viewType == CHILD_VIEW_TYPE) {
                    Log.d("recycledViewPool", "getRecycledView: " + viewType + " 剩余缓存 " + getRecycledViewCount(CHILD_VIEW_TYPE));
                }
                return super.getRecycledView(viewType);
            }
        };
        RecyclerView recyclerView = findViewById(R.id.rlParent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRecycledViewPool(recycledViewPool);
        // 传递RecycledViewPool共享池进父适配器，让父适配器里面的子适配器也共用同一个共享池
        ParentInfoAdapter adapter = new ParentInfoAdapter(this, dataInfoList, recycledViewPool);
        // 1. 有同学提到为何设置后，还是会跑onCreateViewHolder呢？
        // 2. 是因为recycledViewPool默认为5的最大存储，当超出缓存后不会缓存，所以我们稍微改大点，设置20
        // 3. 但是设置20以后为什么还是会跑onCreateViewHolder呢，很简单，因为列表可能你只看到屏幕上的10条，实际上在内存上Android已经取出20条出来了，多余的，还是会跑onCreateViewHolder
        // 4. 设置到25设置30就不会看到onCreateViewHolder了，但是手机如果一直存在30个view甚至更多，真的合适吗，这个需要更多的场景合理决定
        recycledViewPool.setMaxRecycledViews(CHILD_VIEW_TYPE, 20);
        recycledViewPool.clear();
        recyclerView.setAdapter(adapter);
    }
}
