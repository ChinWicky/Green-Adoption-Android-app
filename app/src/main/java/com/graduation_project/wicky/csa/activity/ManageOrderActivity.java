package com.graduation_project.wicky.csa.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.graduation_project.wicky.csa.R;

import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.databinding.ActivityManageOrderBinding;
import com.graduation_project.wicky.csa.fragment.OrderFragment;

import com.graduation_project.wicky.csa.widget.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//
public class ManageOrderActivity extends MBaseActivity {

    private ActivityManageOrderBinding manageOrderBinding;
    private List<Good> dataList = new ArrayList<>();
    private int position;
    //private int type;//0卖家的订单管理1为买家的订单管理
    @Override
    protected void setBaseTitle(TextView titleView) {

    }
    @Override
    protected void onGetBundle(Bundle bundle) {
        position = bundle.getInt("position");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            onGetBundle(getIntent().getExtras());
        }
        manageOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_manage_order);
        init(savedInstanceState);
        initViewPager();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        manageOrderBinding.orderTitle.tvTitle.setText("认养情况");
        manageOrderBinding.orderTitle.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    private void initViewPager() {
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(OrderFragment.getInstance("全部", 0));
            fragments.add(OrderFragment.getInstance("待付款", 1));
            fragments.add(OrderFragment.getInstance("待发货", 2));
            fragments.add(OrderFragment.getInstance("待收货", 3));
            fragments.add(OrderFragment.getInstance("待评价", 4));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        adapter.addTitle("全部");
        adapter.addTitle("待付款");
        adapter.addTitle("待发货");
        adapter.addTitle("待收货");
        adapter.addTitle("待评价");

        manageOrderBinding.viewpager.setAdapter(adapter);
        manageOrderBinding.indicator.setViewPager(manageOrderBinding.viewpager);
        manageOrderBinding.indicator.setCurrentTab(position);
    }

    public static void open(MBaseActivity mContext, int position, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        mContext.startActivity(bundle,ManageOrderActivity.class);
    }



}
