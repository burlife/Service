package com.zmm.yuekao.view;


import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;

public interface IDetailView {
    void detailshow(DetailBean detailBean);
    void addshow(AddCartBean addCartBean);
}
