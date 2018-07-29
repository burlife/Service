package com.zmm.yuekao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private List<String>list;
private AutoFlowLayout auto_flow;
private ImageView select;
private EditText edname;
private TextView quxiao;
private ImageView delete;
private String name;
private TextView auto_tv;
private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
    }
    private void initViews() {
    //创建一个集合
        list=new ArrayList<>();
        auto_tv=findViewById(R.id.auto_tv);
        auto_flow=findViewById(R.id.auto_flow);
        select=findViewById(R.id.sou);
        edname=findViewById(R.id.ed_name);
        quxiao=findViewById(R.id.t_quxiao);
        delete=findViewById(R.id.delete);

        select.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sou:
                //搜索
                name=edname.getText().toString();
                list.add(name);
                play();
                break;
            case  R.id.t_quxiao:
                //取消
                edname.getText().clear();
                list.clear();
            break;
            case R.id.delete:
                 //删除
                edname.getText().clear();
                 list.clear();
                 auto_flow.removeAllViews();
                break;
        }
    }

    private void play() {
        auto_flow.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(int i) {
                if (list!=null){
                    view=View.inflate(MainActivity.this,R.layout.auto_layout,null);
                    final  TextView auto_tv=view.findViewById(R.id.auto_tv);
                    auto_tv.setText(list.get(i));
                    list.clear();
                }
                auto_flow.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(int i, View view) {
                        Intent intent=new Intent(MainActivity.this,ShopActivity.class);
                        String name=edname.getText().toString();
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                });
                return view;
            }

        });
    }
}
