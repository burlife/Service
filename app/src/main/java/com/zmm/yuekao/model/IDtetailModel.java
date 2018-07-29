package com.zmm.yuekao.model;


import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;
import com.zmm.yuekao.utils.NetListenter;

public interface IDtetailModel {
    void getAdd(String uid, String pid, String source, NetListenter<AddCartBean> onNetListner);

    void getDetail(String pid, String source, NetListenter<DetailBean> onNetListner);

}
