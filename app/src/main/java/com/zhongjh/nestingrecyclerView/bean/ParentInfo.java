package com.zhongjh.nestingrecyclerView.bean;

import java.util.List;

/**
 * 外层的info
 * Created by zhongjh on 2018/7/26.
 */
public class ParentInfo {

    private String title;

    private List<ChildInfo> menuList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<ChildInfo> menuList) {
        this.menuList = menuList;
    }

}
