package com.zmm.yuekao.model;

import android.util.Log;

import com.google.gson.Gson;
import com.zmm.yuekao.bean.CartBean;
import com.zmm.yuekao.presenter.CartPresetnerImpl;
import com.zmm.yuekao.utils.HttpUtils;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 购物车Modle
 */
public class CartModelImpl {
    CartPresetnerImpl cartPresetner;
    private static final String TAG = "CartModelImpl";

    public CartModelImpl(CartPresetnerImpl cartPresetner) {
        this.cartPresetner = cartPresetner;
    }

    public void showCart(String url) {
        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.okGet(url);
        httpUtils.setOkLoadListener(new HttpUtils.OkLoadListener() {
            @Override
            public void okLoadSuccess(String success) {
                Gson gson = new Gson();
                CartBean cartBean = gson.fromJson(success, CartBean.class);
                String code = cartBean.getCode();
                Log.d(TAG, "okLoadSuccess: " + code);
                if (code.equals("0")) {
                    Log.d(TAG, "okLoadSuccess: " + cartBean.getMsg());
                    cartPresetner.onFormSuccess(cartBean);
                } else {
                    cartPresetner.onFormFailed(cartBean.getMsg());
                }
            }

            @Override
            public void okLoadError(String error) {
                Log.d(TAG, "okLoadError: " + error);
                cartPresetner.onFormFailed(error);
            }
        });
    }

}
