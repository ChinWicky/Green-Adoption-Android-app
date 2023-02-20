package com.graduation_project.wicky.csa.activity;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.ActivityRegorforgetBinding;
import com.graduation_project.wicky.csa.viewModel.RegOrForgViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class RegOrForgActivity extends BaseActivity<ActivityRegorforgetBinding, RegOrForgViewModel> {


    private CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            binding.btnGetCode.setEnabled(false);
            binding.btnGetCode.setNormalSolid(Color.parseColor("#ffcccccc"));
            binding.btnGetCode.setText(millisUntilFinished / 1000 + "s后获取");
        }

        @Override
        public void onFinish() {
            binding.btnGetCode.setEnabled(true);
            binding.btnGetCode.setNormalSolid(Color.parseColor("#ffb40000"));
            binding.btnGetCode.setText("重新获取验证码");
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_regorforget;
    }
    @Override
    public void initData(){
        viewModel.setBinding(binding);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pGetCode.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                ToastUtils.showShort("验证码:0000");
                timer.start();
            }
        });
    }

    @Override
    public void initBar() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
