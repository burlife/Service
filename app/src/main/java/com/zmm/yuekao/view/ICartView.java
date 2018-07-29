package com.zmm.yuekao.view;

import com.zmm.yuekao.bean.CartBean;

import java.util.List;


public interface ICartView {
    void onSuccess(List<CartBean.DataBean> data);

    void onFailed(String err);
}
