package com.zmm.yuekao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zmm.yuekao.adapter.ShoppingRecycleAdapter;
import com.zmm.yuekao.bean.ShoppingBean;
import com.zmm.yuekao.model.ModelImpel;
import com.zmm.yuekao.presenter.PresenterImpel;
import com.zmm.yuekao.view.IShoppingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/7/29.
 */

public class ShopActivity extends AppCompatActivity implements View.OnClickListener,IShoppingView{
    private static final String TAG = "ShopActivity====";
    private ImageView jiantou;
    private ImageView sousuo;
    private EditText  etName;
    private String tvName;
    private ImageView cuohao;
    private ImageView qiehuan;
    private Button button01,button02,button03;
    private String name;
    private String sort="0";
    private int page=1;
   private XRecyclerView recyclerView;
   private ShoppingRecycleAdapter shoppingRecycleAdapter;
  private PresenterImpel presenterImpel;
    private boolean b=false;
    //private List<PhoneBean.DataBean>data=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.shop_layout);
         initViews();
        //得到Intent
        Intent intent=getIntent();
        //判断
        if (intent!=null){
            tvName=intent.getStringExtra("name");
        }
        //String name=intent.getStringExtra("name");
        presenterImpel=new PresenterImpel();
        etName.setText(tvName);
       recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
           @Override
           public void onRefresh() {
               page++;
               presenterImpel.getShop(new ModelImpel(),ShopActivity.this);
              recyclerView.refreshComplete();
           }

           @Override
           public void onLoadMore() {
             page++;
             presenterImpel.getShop(new ModelImpel(),ShopActivity.this);
             recyclerView.loadMoreComplete();
           }
       });
    }
    private void initViews() {
       //phonePresenter.getData(page);
        recyclerView=findViewById(R.id.my_recyView);
        jiantou=findViewById(R.id.i_fan);
        sousuo=findViewById(R.id.t_sou);
        etName=findViewById(R.id.t_name);
        cuohao=findViewById(R.id.t_cuo);
        qiehuan=findViewById(R.id.qie);
        button01=findViewById(R.id.b_zong);
        button02=findViewById(R.id.b_xiao);
        button03=findViewById(R.id.b_price);

        jiantou.setOnClickListener(this);
        cuohao.setOnClickListener(this);
        sousuo.setOnClickListener(this);
        qiehuan.setOnClickListener(this);
        button01.setOnClickListener(this);
        button02.setOnClickListener(this);
        button03.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.i_fan:
                finish();
                break;
            case  R.id.t_sou:
                name=etName.getText().toString();
                presenterImpel.getShop (new ModelImpel (),ShopActivity.this);
                break;
            case R.id.t_cuo:
                etName.getText().clear();
                break;
            case R.id.qie:
                if (b==false){
                    recyclerView.setLayoutManager(new GridLayoutManager(ShopActivity.this,2));
                    b=true;
                }else if (b==true){
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShopActivity.this));
                    b=false;
                    qiehuan.setImageResource(R.drawable.linear);
                }
                break;
            case R.id.b_zong:
                   sort="0";
                  presenterImpel.getShop(new ModelImpel(),ShopActivity.this);
                break;
            case R.id.b_xiao:
                    sort="1";
                presenterImpel.getShop(new ModelImpel(),ShopActivity.this);
                break;
            case R.id.b_price:
                    sort="2";
                presenterImpel.getShop(new ModelImpel(),ShopActivity.this);
                break;
        }
    }

    @Override
    public void ShowShoppingToViews(List<ShoppingBean.DataBean> data) {
        Log.d(TAG, "数据信息---"+data);
        shoppingRecycleAdapter=new ShoppingRecycleAdapter(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shoppingRecycleAdapter);
        shoppingRecycleAdapter.setOnClickItemListener(new ShoppingRecycleAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View v, int position) {
                startActivity(new Intent(ShopActivity.this,ThreeActivity.class));
            }
        });
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int getPage() {

        return page;
    }

    @Override
    public String getSort() {

        return sort;
    }
}
