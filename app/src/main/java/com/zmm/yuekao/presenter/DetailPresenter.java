package com.zmm.yuekao.presenter;


import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;
import com.zmm.yuekao.model.DetailModel;
import com.zmm.yuekao.model.IDtetailModel;
import com.zmm.yuekao.utils.NetListenter;
import com.zmm.yuekao.view.IDetailView;

public class DetailPresenter {
    private IDetailView activity;
    private final IDtetailModel model;

    public DetailPresenter(IDetailView activity) {
        this.activity = activity;
        model = new DetailModel();
    }

    public void onDestroys() {
        if (activity != null) {
            activity = null;
        }
    }

    public void getDetai(String pid) {
        model.getDetail(pid, "android", new NetListenter<DetailBean>() {
            @Override
            public void onSccess(DetailBean detailBean) {
                if (activity != null) {

                    activity.detailshow(detailBean);
                }
            }

            @Override
            public void onFailuer(Exception e) {

            }


        });
    }

    public void getadds(String uid, String pid) {
        model.getAdd(uid, pid, "android", new NetListenter<AddCartBean>() {
            @Override
            public void onSccess(AddCartBean addCartBean) {
                if (activity != null) {
                    activity.addshow(addCartBean);
                }
            }

            @Override
            public void onFailuer(Exception e) {

            }


        });

    }


}
