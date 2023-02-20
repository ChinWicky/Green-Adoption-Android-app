package com.graduation_project.wicky.csa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.databinding.ActivityGoodExamineBinding;
import com.graduation_project.wicky.csa.utils.GlideImageLoader;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.graduation_project.wicky.csa.viewModel.ExamineDetailViewModel;
import com.orhanobut.hawk.Hawk;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Created by Wicky on 2019/2/13.
 */

public class ExamineDetailActivity extends BaseActivity<ActivityGoodExamineBinding, ExamineDetailViewModel> {


    private Good good;
    private Order order;
    private User suppler;
    private boolean isExamined;
    private String examineIn;
    private User user = Hawk.get(HawkKey.USER);

    @Override
    public void initParam() {
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if (mBundle != null) {
            good = mBundle.getParcelable("good");
            isExamined = mBundle.getBoolean("isExamined");
            examineIn = mBundle.getString("examineIn");
            order = mBundle.getParcelable("order");
            suppler = mBundle.getParcelable("suppler");
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_good_examine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setEntity(good);
        viewModel.setOrder(order);
        viewModel.setSuppler(suppler);
        initBanner();
    }

    @Override
    public void initViewObservable() {
        switch (examineIn) {
            case "sharing":
                binding.btnReject.setVisibility(View.GONE);
                binding.btnExamine.setText("取消共享");
                binding.btnExamine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.cancelGood(good);
                    }
                });
                break;
            case "examine":
                binding.btnExamine.setText("审核通过");
                binding.btnExamine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.agreeGood(good);;
                    }
                });
                if (isExamined)
                    binding.btnExamine.setVisibility(View.GONE);
                break;
            default:
                break;
        }


//        if(!user.isAdmin()){
//            binding.btnReject.setVisibility(View.GONE);
//            binding.btnExamine.setText("取消共享");
//
//        }
//        if(user.isAdmin()){
//            binding.btnExamine.setText("审核通过");
//            if(isExamined)
//            binding.btnExamine.setVisibility(View.GONE);
//
//        }
    }

    public void initBanner() {
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        binding.banner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器，图片加载器在下方
        binding.banner.setImageLoader(glideImageLoader);
        //设置轮播图的标题集合
        //binding.banner.setBannerTitles(list_title);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        binding.banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置指示器的位置，小点点，左中右。
        binding.banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置轮播间隔时间
        binding.banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        binding.banner.isAutoPlay(true);
        //设置图片网址或地址的集合
        binding.banner.setImages(viewModel.getBannerImage())
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                // .setOnBannerListener(this)
                .start();
    }


    @Override
    public void initBar() {
        binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (isExamined) {
            binding.titleBar.tvTitle.setText("已审核");
        } else {
            binding.titleBar.tvTitle.setText("待审核");
        }
    }
}
