package com.zmm.yuekao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zmm.yuekao.Fragment.CartFragment;
import com.zmm.yuekao.Fragment.PingLunFragment;
import com.zmm.yuekao.Fragment.ShangPinFragmen;
import com.zmm.yuekao.Fragment.XiangQingFragment;
import com.zmm.yuekao.adapter.TitleFragmentPagerAdapter;
import com.zmm.yuekao.bean.AddCartBean;
import com.zmm.yuekao.bean.DetailBean;
import com.zmm.yuekao.presenter.DetailPresenter;
import com.zmm.yuekao.view.IDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/7/29.
 */

public class ThreeActivity extends AppCompatActivity implements IDetailView,View.OnClickListener{
    private ImageView mIvFanhui;
    private TabLayout mTabview;
    private ImageView mIvFenxiang;
    private ImageView mIvGengduo;
    private ViewPager mPager;
    private LinearLayout mLlSupplier;
    private LinearLayout mLlShop;
    private LinearLayout mLlAttention;
    private LinearLayout mLlCard;
    private TextView mTvAddCard;
    String uid="71";
    String pid="21";
    DetailPresenter detailPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deta_activity);
        initView();
        detailPresenter = new DetailPresenter(this);
        //获取传过来的pid
      Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
//
        detailPresenter.getDetai(pid);
    }

    private void initView() {
        mIvFanhui = (ImageView) findViewById(R.id.iv_fanhui);
        mIvFanhui.setOnClickListener(this);
        mTabview = (TabLayout) findViewById(R.id.tabview);
        mIvFenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        mIvFenxiang.setOnClickListener(this);
        mIvGengduo = (ImageView) findViewById(R.id.iv_gengduo);
        mIvGengduo.setOnClickListener(this);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOnClickListener(this);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ShangPinFragmen());
        fragments.add(new XiangQingFragment());
        fragments.add(new PingLunFragment());
        //头部导航的适配器
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"商品", "详情", "评论"});
        mPager.setAdapter(adapter);
        mTabview.setupWithViewPager(mPager);
        mLlSupplier = (LinearLayout) findViewById(R.id.llSupplier);
        mLlShop = (LinearLayout) findViewById(R.id.llShop);
        mLlAttention = (LinearLayout) findViewById(R.id.llAttention);
        mLlCard = (LinearLayout) findViewById(R.id.llCard);
        mLlCard.setOnClickListener(this);
        mTvAddCard = (TextView) findViewById(R.id.tvAddCard);
        mTvAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_fanhui:
                finish();
                break;
            case R.id.iv_fenxiang:

                break;
            case R.id.iv_gengduo:
                break;
            case R.id.pager:
                break;
            case R.id.llCard:
                break;
            case R.id.tvAddCard:
                //添加购物车
                detailPresenter.getadds(uid, pid);
              // Intent intent=new Intent(ThreeActivity.this, CartFragment.class);
              // startActivity(intent);
                break;
        }
    }
    @Override
    public void detailshow(DetailBean detailBean) {

    }

    @Override
    public void addshow(AddCartBean addCartBean) {
        Toast.makeText(this, addCartBean.getMsg() + "", Toast.LENGTH_LONG).show();

    }
}
