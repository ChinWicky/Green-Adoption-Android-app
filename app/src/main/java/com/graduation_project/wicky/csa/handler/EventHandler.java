package com.graduation_project.wicky.csa.handler;

import android.content.Context;
import android.view.View;

import com.graduation_project.wicky.csa.R;

import com.graduation_project.wicky.csa.activity.ManageOrderActivity;
import com.graduation_project.wicky.csa.activity.UserInfoActivity;


public abstract class EventHandler {

    private Context mContext;

    public EventHandler() {

    }

    public EventHandler(Context context) {
        mContext = context;
    }

    public abstract void onClick(View view) ;
}


