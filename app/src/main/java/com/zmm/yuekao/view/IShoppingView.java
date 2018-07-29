package com.zmm.yuekao.view;

import com.zmm.yuekao.bean.ShoppingBean;

import java.util.List;


public interface IShoppingView {
    void ShowShoppingToViews(List<ShoppingBean.DataBean> data);

    //输入框内容
    String getName();

    //页数
    int getPage();

    //排序参数
    String getSort();
}
