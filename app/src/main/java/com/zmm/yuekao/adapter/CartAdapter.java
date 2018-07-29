package com.zmm.yuekao.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zmm.yuekao.R;
import com.zmm.yuekao.bean.CartBean;
import com.zmm.yuekao.view.ICartView;
import com.zmm.yuekao.view.SumLayout;

import java.util.List;



/**
 * 购物车主体适配器
 * ExpandableListView
 * 一种用于垂直滚动展示两级列表的视图，
 * 和 ListView 的不同之处就是它可以展示两级列表，
 * 分组可以单独展开显示子选项。这
 * 些选项的数据是通过 ExpandableListAdapter 关联的。
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CartAdapter";
    private int count = 0;
    List<CartBean.DataBean> list;
    Context context;
    OnGetCount onGetCount;
    FromJiSuanData fromJiSuanData;
    ICartView iCartView;
    private boolean checked;
    ChildIsFlag childIsFlag;

    public CartAdapter(Context context, List<CartBean.DataBean> data, ICartView iCartView) {
        Log.d(TAG, "CartAdapter: " + data);
        this.context = context;
        this.list = data;
        this.iCartView = iCartView;
    }

    //获取父级分组的个数
    @Override
    public int getGroupCount() {
        return list.size();
    }

    /**
     * 获取指定父级分组中的子选项的个数
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    /**
     * 获取指定的分组数据
     *
     * @param groupPosition
     * @return
     */

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    /**
     * 获取指定分组中的指定子选项数据
     *
     * @param groupPosition 指针，表示当前赈灾显示的第X条父级数据
     * @param childPosition 指针，表示当前赈灾显示的第X条子级数据
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    /**
     * 获取指定分组的ID, 这个ID必须是唯一的
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取子选项的ID, 这个ID必须是唯一的
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定父级分组的视图
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MyGroupView myGroupView = null;
        //复用convertView优化条目
        if (convertView == null) {
            myGroupView = new MyGroupView();
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_group_item, parent, false);
            myGroupView.textView = convertView.findViewById(R.id.cart_group_item_title);
            convertView.setTag(myGroupView);
        } else {
            myGroupView = (MyGroupView) convertView.getTag();
        }
        //给父条目赋值
        myGroupView.textView.setText(list.get(groupPosition).getSellerName());
        return convertView;
    }

    /**
     * 获取显示指定分组中的指定子选项条目的视图
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyChildView myChildView = null;
        //复用convertView优化条目
        if (convertView == null) {
            myChildView = new MyChildView();
            //加载子选项条目视图
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_child_item, parent, false);
            myChildView.checkBox = convertView.findViewById(R.id.cart_child_item_cb);
            myChildView.imageView = convertView.findViewById(R.id.cart_child_item_simple);
            myChildView.title = convertView.findViewById(R.id.cart_child_item_title);
            myChildView.price = convertView.findViewById(R.id.cart_child_item_price);
            myChildView.bargainPrice = convertView.findViewById(R.id.cart_child_item_bargainPrice);
            myChildView.sumLayout = convertView.findViewById(R.id.cart_childe_item_sumlayout);
            convertView.setTag(myChildView);
        } else {
            myChildView = (MyChildView) convertView.getTag();
        }
        //得到子选项条目数据
        List<CartBean.ChildDataBean> childList = this.list.get(groupPosition).getList();
        CartBean.ChildDataBean childDataBean = childList.get(childPosition);

        //给子条目赋值
        myChildView.checkBox.setChecked(childDataBean.isChildChecked());
        String urls= childDataBean.getImages().split("\\|")[0];
        //使用Glide加载图片
       // Glide.with(context).load(split[childPosition]).into(myChildView.imageView);
        myChildView.imageView.setImageURI(urls);

        //赋值
        myChildView.title.setText(childDataBean.getTitle());
        myChildView.price.setText("￥" + childDataBean.getPrice());
        myChildView.bargainPrice.setText("￥"+childDataBean.getBargainPrice());
        //textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线（删除线
        myChildView.bargainPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);//设置删除线
        myChildView.sumLayout.setCount(childDataBean.getNum() + "");

        final MyChildView finalMyChildView = myChildView;
        /**
         * 自定义加减框控件点击事件
         */
        myChildView.sumLayout.setOnDownSumLayoutListener(new SumLayout.OnDownSumLayouListener() {
            @Override
            public void onDownSumLayout() {
                //得到改变后的购买数量
                String count = finalMyChildView.sumLayout.getCount();
                //转为int型
                int i = Integer.parseInt(count);
                //并重新给子条目数据的购买数量赋值
                list.get(groupPosition).getList().get(childPosition).setNum(i);
                formCartJisuan();
                //刷新适配器，使显示器上的值改变
                notifyDataSetChanged();

            }
        });
        checked = myChildView.checkBox.isChecked();
        childIsFlag.isFlag(checked);
        final MyChildView finalMyChildView1 = myChildView;
        myChildView.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复选框的选中状态
                boolean checked = finalMyChildView1.checkBox.isChecked();

                Log.d(TAG, "onClick: " + checked);
                //给Bean类赋值
                list.get(groupPosition).getList().get(childPosition).setChildChecked(checked);

                //判断当为选中状态时选中数量加一
                if (checked) {
                    count++;

                } else {//否则减一
                    if (count > 0) {
                        count--;

                    }
                }


                formCartJisuan();
                boolean childChecked = list.get(groupPosition).getList().get(childPosition).isChildChecked();
                //使用自定义接口把获取到的条目选中数量传出去
                onGetCount.getCount(count,checked,childChecked);
                //调用刷新适配器的方法
                //重新请求网络数据
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    /**
     * 指定位置上的子元素是否可选中
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //复选框选中个数的内部接口
    public interface OnGetCount {
        void getCount(int count, boolean b, boolean checked);
    }

    //访问接口的放发
    public void setOnGetCount(OnGetCount onGetCount) {
        this.onGetCount = onGetCount;
    }

    public double formCartJisuan() {
        //初始值为0  记录总价
        double sum = 0;
        //遍历父条目数据集合
        for (int i = 0; i < list.size(); i++) {
            //得到子条目数据集合
            List<CartBean.ChildDataBean> childList = list.get(i).getList();
            //遍历子条目数据集合
            for (int j = 0; j < childList.size(); j++) {
                //如果，子条目复选框为选中状态时，则累加总价
                if (childList.get(j).isChildChecked()) {
                    //得到总价sum  总价=数量*单价
                    sum += childList.get(j).getNum() * childList.get(j).getPrice();
                }
            }
        }
        //Log.d(TAG, "formCartJisuan: " + sum);
        //把得到的总价传到Presenter层
        fromJiSuanData.formJisuan(sum);
        return sum;
    }

    public interface FromJiSuanData {
        void formJisuan(double sum);
    }

    public void setFromJiSuan(FromJiSuanData fromJiSuanData) {
        this.fromJiSuanData = fromJiSuanData;
    }

    public interface ChildIsFlag{
        void isFlag(boolean flag);
    }

    public void setChildIsFlag(ChildIsFlag childIsFlag){
        this.childIsFlag = childIsFlag;
    }

    //父条目ViewHolder
    class MyGroupView {
        //父条目商家name
        TextView textView;
    }

    //子条目ViewHolder
    class MyChildView {
        //子条目复选框
        CheckBox checkBox;
        //子条目商品图片
        SimpleDraweeView imageView;
        //子条目商品标题
        TextView title;
        //字条目商品价格
        TextView price;
        //子条目
        TextView bargainPrice;
        //自定义加减框控件
        SumLayout sumLayout;
    }
}
