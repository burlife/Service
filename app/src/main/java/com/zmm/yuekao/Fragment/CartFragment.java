package com.zmm.yuekao.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zmm.yuekao.R;
import com.zmm.yuekao.adapter.CartAdapter;
import com.zmm.yuekao.bean.CartBean;
import com.zmm.yuekao.presenter.CartPresetnerImpl;
import com.zmm.yuekao.utils.CustomExpandableListView;
import com.zmm.yuekao.view.ICartView;

import java.util.List;

/**
 * 购物车页面
 */
public class CartFragment extends Fragment implements ICartView {
    private static final String TAG = "CartFragment";
    private View view;
    private ImageView left_jiantou;
    private CustomExpandableListView expandable;
    private CheckBox cart_frag_checkbox;
    private TextView cart_frag_price;
    private TextView cart_frag_xiadan;
    private TextView cart_frag_sum;
    private RecyclerView cart_frag_recycler;
    private TextView cart_frag_xiaoji;
    private CartPresetnerImpl cartPresetner;
    private List<CartBean.DataBean> list;
    private CartAdapter adapter;
    private int counts;
    private boolean frag_checked;
    private boolean check_flag;
    private boolean childflag;
    boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment_layout, container, false);
        initView();
        return view;
    }

    //初始化控件
    private void initView() {
        left_jiantou = view.findViewById(R.id.left_jiantou);
        expandable = view.findViewById(R.id.cartlayout_cart_expandable);
        cart_frag_checkbox = view.findViewById(R.id.cart_frag_checkbox);
        cart_frag_price = view.findViewById(R.id.cart_frag_price);
        cart_frag_xiadan = view.findViewById(R.id.cart_frag_xiadan);
        cart_frag_sum = view.findViewById(R.id.cart_frag_sum);
        cart_frag_recycler = view.findViewById(R.id.cart_frag_recycler);
        cart_frag_xiaoji = view.findViewById(R.id.cart_frag_xiaoji);
        //设置父条目不可点击
        expandable.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true,表示不可点击
            }
        });
        //全选
        cart_frag_checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "getCount: 全选框onClick: " + check_flag);

                frag_checked = cart_frag_checkbox.isChecked();
                for (int i = 0; i < list.size(); i++) {

                    List<CartBean.ChildDataBean> childList = CartFragment.this.list.get(i).getList();
                    counts += childList.size();
                    for (int j = 0; j < childList.size(); j++) {

                        //遍历子条目数据里面的复选框状态，有一个为false,那么父条目数据复选框状态就为false
                        /*boolean childFlag = childList.get(j).isChildChecked();
                        if (frag_checked == false) {
                            flag = false;
                        }*/
                        childList.get(j).setChildChecked(frag_checked);

                        adapter.formCartJisuan();
                        adapter.notifyDataSetChanged();
                    }
                }
                Log.d(TAG, "getCount: 全选框onClick: " + childflag);
            }
        });


        //返回的点击事件
        left_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "返回", Toast.LENGTH_SHORT).show();
            }
        });
        cartPresetner = new CartPresetnerImpl(this);

    }

    @Override
    public void onSuccess(List<CartBean.DataBean> data) {
        Log.d(TAG, "onSuccess: " + data);
        this.list = data;
        adapter = new CartAdapter(getActivity(), data, this);
        expandable.setAdapter(adapter);
        //展开子条目
        int count = expandable.getCount();
        for (int i = 0; i < count; i++) {
            expandable.expandGroup(i);
        }
        adapter.setOnGetCount(new CartAdapter.OnGetCount() {

            @Override
            public void getCount(int count, boolean checked, boolean childChecked) {
                //count = counts;
                check_flag = checked;
                childflag = childChecked;
                Log.d(TAG, "getCount: childChecked = " + childChecked);
                Log.d(TAG, "getCount: checked = " + checked);
                Log.d(TAG, "getCount: frag_checked = " + frag_checked);
                // counts = count;
                /*if (counts == 0) {
                    count = 0;
                } else {
                    counts = count;
                }*/
               /* if (checked) {
                    cart_frag_sum.setText("已选（" + count + "）");
                } else {
                    cart_frag_sum.setText("已选（0）");
                }*/

            }
        });

        adapter.setFromJiSuan(new CartAdapter.FromJiSuanData() {
            @Override
            public void formJisuan(double sum) {
                //Log.d(TAG, "formJisuan: " + sum);
                cart_frag_xiaoji.setText("小计：￥" + sum);
                cart_frag_price.setText("￥" + sum);
            }
        });

        adapter.setChildIsFlag(new CartAdapter.ChildIsFlag() {
            @Override
            public void isFlag(boolean flag) {
                Log.d(TAG, "isFlag: " + flag);
                if (flag) {
                    cart_frag_sum.setText("已选（" + counts + "）");
                } else {
                    counts = 0;
                    cart_frag_sum.setText("已选（" + counts + "）");
                }
            }
        });
    }

    @Override
    public void onFailed(String err) {
        Log.d(TAG, "onFailed: " + err);

    }
}
