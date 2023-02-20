package com.graduation_project.wicky.csa.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.ItemImageSelectBinding;
import com.graduation_project.wicky.csa.utils.GlideUtil;

import java.util.List;


/**
 * PackageName : cn.ewhale.xinnongtong.ui.mine.adapter
 * Author : SimGa Liu
 * Date : 2017/09/23
 * Time : 10:46
 */
public class PublicPicAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;
    private DeleteCallBack callBack;
    private int picNum;
    private ItemImageSelectBinding imageSelectBinding;


    //private ItemIm itemSelectBinding;

    public void setCallBack(DeleteCallBack callBack) {
        this.callBack = callBack;
    }

    public PublicPicAdapter(Context context, List<String> data, int picNum) {
        this.context = context;
        this.data = data;
        this.picNum = picNum;
    }

    @Override
    public int getCount() {
        if (data.size() == picNum) {
            return data.size();
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //convertView = LayoutInflater.from(context).inflate(R.layout.item_image_select, null, false);
        imageSelectBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_image_select, parent, false);

        ViewHolder holder = new ViewHolder(imageSelectBinding.getRoot());
        if (position == data.size()) {
            holder.delete.setVisibility(View.GONE);
            holder.image.setImageResource(R.mipmap.btn_pic_add);
        } else {
            holder.delete.setVisibility(View.VISIBLE);
            String item = data.get(position);
            GlideUtil.loadPicture(item, holder.image);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onDeleteListener(position);
                }
            }
        });
        return imageSelectBinding.getRoot();
    }

    class ViewHolder {

        ImageView image = imageSelectBinding.ivImage;
        ImageView delete = imageSelectBinding.ivDelete;

        ViewHolder(View view) {
            //ButterKnife.bind(this, view);
        }
    }

    public interface DeleteCallBack {
        void onDeleteListener(int position);
    }

}
