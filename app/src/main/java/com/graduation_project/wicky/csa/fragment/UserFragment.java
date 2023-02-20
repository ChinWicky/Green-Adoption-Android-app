package com.graduation_project.wicky.csa.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.FragmentUserBinding;
import com.graduation_project.wicky.csa.viewModel.UserViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;


public class UserFragment extends BaseFragment<FragmentUserBinding, UserViewModel> {


    @Override
    public void initParam() {
        super.initParam();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return R.layout.fragment_user;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.getOption();
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initBar() {
        binding.mainTitle.tvTitle.setText("用户信息");
        binding.mainTitle.ivBack.setVisibility(View.GONE);
    }

}
