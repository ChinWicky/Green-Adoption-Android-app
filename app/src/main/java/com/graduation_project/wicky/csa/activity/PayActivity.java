package com.graduation_project.wicky.csa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.databinding.ActivityPayBinding;
import com.graduation_project.wicky.csa.viewModel.PayViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;


public class PayActivity extends BaseActivity<ActivityPayBinding, PayViewModel> {

    private Order order;


    @Override
    public void initParam() {
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if (mBundle != null) {
            order = mBundle.getParcelable("order");
        }
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_pay;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.initData(order);
    }


    @Override
    public void initViewObservable() {

    }

    @Override
    public void initBar() {
        binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.titleBar.tvTitle.setText("支付");
    }


}
