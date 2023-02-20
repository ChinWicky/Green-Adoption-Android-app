package com.graduation_project.wicky.csa.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.ActivitySettingBinding;
import com.graduation_project.wicky.csa.viewModel.SettingViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**

 */
public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SettingViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        return ViewModelProviders.of(this).get(SettingViewModel.class);
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
        binding.titleBar.tvTitle.setText("设置");
    }
}
