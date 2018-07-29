package com.zmm.yuekao.model;


import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;
import com.zmm.yuekao.utils.NetListenter;
import com.zmm.yuekao.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailModel implements IDtetailModel {
    @Override
    public void getAdd(String uid, String pid, String source, final NetListenter<AddCartBean> onNetListner) {
        Observable<AddCartBean> addcata = RetrofitUtils.getServerApi().addcata(uid, pid,source);
        addcata.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AddCartBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddCartBean addCartBean) {
                        onNetListner.onSccess(addCartBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetListner.onFailuer((Exception) e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    @Override
    public void getDetail(String pid, String source, final NetListenter<DetailBean> onNetListner) {
        Observable<DetailBean> detail = RetrofitUtils.getServerApi().detail(pid,source);
        detail.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DetailBean detailBean) {
                        onNetListner.onSccess(detailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetListner.onFailuer((Exception) e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
