package com.zmm.yuekao.model;

import com.zmm.yuekao.view.IShoppingListener;

import java.util.Map;


public interface IModel {
    //商品详情
    void shop(String url, Map<String, String> map, IShoppingListener iShoppingListener);
}
