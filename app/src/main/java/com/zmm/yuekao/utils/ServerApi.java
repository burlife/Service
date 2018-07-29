package com.zmm.yuekao.utils;

import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {

    //详情
    @GET("product/getProductDetail")
    Observable<DetailBean> detail(@Query("pid") String pid, @Query("source") String source);

    //加入购物车
    @POST("product/addCart")

    Observable<AddCartBean> addcata(@Query("uid") String uid, @Query("pid") String pid, @Query("source") String source);


}