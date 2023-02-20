package com.graduation_project.wicky.csa.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.widget.MPopuWindow;


public class PopuWinUtil {

    @SuppressLint("InflateParams")
    public MPopuWindow listPopuWindow(Context cxt, BaseAdapter adapter,
                                      OnItemClickListener listener, float height) {
        View view = LayoutInflater.from(cxt).inflate(R.layout.popu_list_bg, null);
        final MPopuWindow popuWindow = new MPopuWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        // 这里设置显示PopuWindow之后在外面点击是否有效。如果为false的话，那么点击PopuWindow外面并不会关闭PopuWindow。
        popuWindow.setOutsideTouchable(true);// 不能在没有焦点的时候使用
        ListView lv = view.findViewById(R.id.popu_lv_select);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(listener);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lv.getLayoutParams();
        lp.height = (int) Dp2PxUtil.dip2px(cxt, height);
        lv.setLayoutParams(lp);

        ColorDrawable cd = new ColorDrawable(0x00000000);
        popuWindow.setBackgroundDrawable(cd);
        popuWindow.setFocusable(true); //这句很重要，当显示时点击外部，事件不会传到下面
        view.findViewById(R.id.popu_ll).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popuWindow.dismiss();
            }
        });
        return popuWindow;
    }

    @SuppressLint("InflateParams")
    public MPopuWindow listPopuSearch(Context cxt, BaseAdapter adapter, OnItemClickListener listener) {
        View view = LayoutInflater.from(cxt).inflate(R.layout.pop_search, null);
        final MPopuWindow popuWindow = new MPopuWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popuWindow.setOutsideTouchable(true);
        popuWindow.setFocusable(true);
        popuWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        ListView lv = view.findViewById(R.id.popu_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(listener);
        view.findViewById(R.id.popu_bg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popuWindow.dismiss();
            }
        });
        return popuWindow;
    }


}
