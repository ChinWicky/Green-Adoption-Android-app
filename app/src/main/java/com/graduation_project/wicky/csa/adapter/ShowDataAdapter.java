package com.graduation_project.wicky.csa.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.ItemSelectBinding;

import java.util.List;


/**
 * Author：SimGa
 * CoryRight：2018/11/19
 * PackageName：com.ewhale.imissyou.userside.ui.user.mall.adapter
 */
public class ShowDataAdapter extends MBaseAdapter<String> {
    ItemSelectBinding itemSelectBinding;

    public ShowDataAdapter(Context context, List<String> data) {
        super(context, data, R.layout.item_select);
    }

    @Override
    protected void newView(View convertView, int position) {
        ViewHolder holder = new ViewHolder(convertView);
        convertView.setTag(holder);
    }

    @Override
    protected void holderView(View convertView, String itemObject, int position) {
       ViewHolder holder = (ViewHolder) convertView.getTag();
       holder.Tvname.setText(itemObject);

    }

    class ViewHolder{


        //private  ItemSelectBinding itemSelectBinding;
        TextView Tvname = itemSelectBinding.Tvname;
        ViewHolder(View view){
           // ButterKnife.bind(this,view);
        }
    }
}