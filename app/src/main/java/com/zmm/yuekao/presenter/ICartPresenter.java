package com.zmm.yuekao.presenter;


import com.zmm.yuekao.bean.CartBean;

public interface ICartPresenter {
    void onFormSuccess(CartBean cartBean);
    void onFormFailed(String error);
}
