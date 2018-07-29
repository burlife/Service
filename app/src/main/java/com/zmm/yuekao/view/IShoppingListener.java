package com.zmm.yuekao.view;

public interface IShoppingListener {
    //成功
    void onSuccess(String json);

    //失败
    void onError(String error);
}
