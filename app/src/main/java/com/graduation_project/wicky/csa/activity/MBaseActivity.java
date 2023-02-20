package com.graduation_project.wicky.csa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


//
public abstract class MBaseActivity extends AppCompatActivity {

    protected MBaseActivity mContext;

    protected abstract void setBaseTitle(TextView titleView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            onGetBundle(getIntent().getExtras());
        }
        //setApplication(new BaseApplication());
    }

    protected abstract void init(Bundle savedInstanceState);

    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected abstract void onGetBundle(Bundle bundle);


}
