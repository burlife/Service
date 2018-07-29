package com.zmm.yuekao.utils;

/**
 * Created by dell on 2018/4/10.
 */

public interface NetListenter<T> {
    //成功
     void onSccess(T t);
    //失败
     void onFailuer(Exception e);
}
