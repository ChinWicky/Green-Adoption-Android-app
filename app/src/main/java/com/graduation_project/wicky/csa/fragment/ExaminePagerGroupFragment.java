package com.graduation_project.wicky.csa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：ViewPager+Fragment的实现
 */

public class ExaminePagerGroupFragment extends BasePagerFragment {
    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("position", i);
            ExamineFragment examineFragment = new ExamineFragment();
            examineFragment.setArguments(mBundle);
            //ToastUtils.showShort(String.valueOf(mySharingFragment.getArguments().getInt("position")));
            list.add(examineFragment);
        }
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        list.add("待审核");
        list.add("已审核");
        //list.add("page3");
        //list.add("page4");
        return list;
    }

    @Override
    public void initBar() {
        binding.mainTitle.tvTitle.setText("产品审核");
        binding.mainTitle.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
