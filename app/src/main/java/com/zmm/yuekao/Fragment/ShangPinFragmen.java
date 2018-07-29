package com.zmm.yuekao.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.zmm.yuekao.MyGlide;
import com.zmm.yuekao.R;
import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;
import com.zmm.yuekao.presenter.DetailPresenter;
import com.zmm.yuekao.view.IDetailView;

import java.util.ArrayList;
import java.util.List;


//商品界面
public class ShangPinFragmen extends Fragment implements IDetailView {
    private static final String TAG = "ShangPinFragmen";
    private View view;
    private Banner mDetailBn;
    /**
     * naoisd
     */
    private TextView mDetailTitel;
    /**
     * asda
     */
    private TextView mDetailPrice;
  //用户ID
    String uid="71";
    String pid="21";
    //P层的调取
    private DetailPresenter spXQpresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.sp_fragment, null);

        initView(view);
        //获取传过来的pid
       // Intent intent = getActivity().getIntent();
     // int pid = intent.getIntExtra("pid",0);
        spXQpresenter = new DetailPresenter(this);
        //spXQpresenter.getDetai("21" );
        //获取pid
        spXQpresenter.getDetai(pid);
        //spXQpresenter.getadds("71","","");
        return view;
    }


    private void initView(View view) {
        mDetailBn = (Banner) view.findViewById(R.id.detail_bn);
        mDetailTitel = (TextView) view.findViewById(R.id.detail_titel);
        mDetailPrice = (TextView) view.findViewById(R.id.detail_price);

    }

    @Override
    public void detailshow(DetailBean detailBean) {
        //获取数据
        Log.d(TAG, "detailshow: "+detailBean);
        //标题
        mDetailTitel.setText(detailBean.getData().getSubhead());
        //价格
        mDetailPrice.setText("¥" + detailBean.getData().getPrice());
      //轮播图
        List<String> list = new ArrayList<>();
        String images = detailBean.getData().getImages();
        String[] split = images.split("\\|");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        //获取图片
        mDetailBn.setImages(list)
                .setImageLoader(new MyGlide())
                .isAutoPlay(true)
                .start();
    }

    @Override
    public void addshow(AddCartBean addCartBean) {

    }


}
