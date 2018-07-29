package com.zmm.yuekao.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zmm.yuekao.R;
import com.zmm.yuekao.bean.ShoppingBean;

import java.util.List;



public class ShoppingRecycleAdapter extends XRecyclerView.Adapter<ShoppingRecycleAdapter.MyViewHolder> {
    private final Context context;
    private final List<ShoppingBean.DataBean> list;
    private OnClickItemListener onClickItemListener;
    private MyViewHolder holder;

    public ShoppingRecycleAdapter(Context context, List<ShoppingBean.DataBean> list){
        this.context = context;

        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate (context, R.layout.shopping_item_adapter, null);
        holder = new MyViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       MyViewHolder viewHolder = holder;

       //设置数据
       viewHolder.goodName.setText (list.get (position).getTitle ());

       viewHolder.goodPrice.setText (list.get (position).getBargainPrice ()+"元");

        String images = list.get (position).getImages ();

        String s = images.split ("\\|")[0];

        Uri uri = Uri.parse (s);
        viewHolder.icon.setImageURI(uri);
       Glide.with (context).load (uri).into (viewHolder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onItemClick(v,position);
            }
        });
    }

    public interface OnClickItemListener{
        void onItemClick(View v, int position);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener){
        this.onClickItemListener = onClickItemListener;
    }

    @Override
    public int getItemCount() {//得到集合的数值长度
        return list.size ();
    }


    public static class MyViewHolder extends XRecyclerView.ViewHolder{

        private final TextView goodName;
         private ImageView icon;
        private final TextView goodPrice;

        public MyViewHolder(View itemView) {
            super (itemView);

            goodName = itemView.findViewById (R.id.goodName);

            icon = itemView.findViewById (R.id.icon);

            goodPrice = itemView.findViewById (R.id.goodPrice);
        }
    }
}
