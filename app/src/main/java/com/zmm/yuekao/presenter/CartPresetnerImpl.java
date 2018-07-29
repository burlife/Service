package com.zmm.yuekao.presenter;

import android.util.Log;

import com.zmm.yuekao.bean.CartBean;
import com.zmm.yuekao.model.CartModelImpl;
import com.zmm.yuekao.view.ICartView;

import java.util.List;


/**
 * 购物车Presenter
 */
public class CartPresetnerImpl implements ICartPresenter {
    String url = "http://www.zhaoapi.cn/product/getCarts?uid=71";
    ICartView iCartView;
    private final CartModelImpl cartModel;
    private static final String TAG = "CartPresetnerImpl";
    public CartPresetnerImpl(ICartView iCartView){
        this.iCartView = iCartView;
        cartModel = new CartModelImpl(this);

        cartModel.showCart(url);
    }

    public void getPrice(List<CartBean.DataBean> data){
        //cartModel.formCartJisuan(data,this);
    }

    @Override
    public void onFormSuccess(CartBean cartBean) {
        Log.d(TAG, "onFormSuccess: "+cartBean);
        List<CartBean.DataBean> data = cartBean.getData();
        iCartView.onSuccess(data);
    }

    @Override
    public void onFormFailed(String error) {
        Log.d(TAG, "onFormFailed: "+error);
        iCartView.onFailed(error);
    }
}
