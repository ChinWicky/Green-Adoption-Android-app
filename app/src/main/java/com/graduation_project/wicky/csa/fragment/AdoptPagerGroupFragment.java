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

public class AdoptPagerGroupFragment extends BasePagerFragment {
    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("position", i);
            MyAdoptFragment myAdoptFragment = new MyAdoptFragment();
            myAdoptFragment.setArguments(mBundle);
            //ToastUtils.showShort(String.valueOf(mySharingFragment.getArguments().getInt("position")));
            list.add(myAdoptFragment);
        }
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("待付款");
        list.add("认养中");
        list.add("已完成");
        return list;
    }

    @Override
    public void initBar() {
        binding.mainTitle.tvTitle.setText("我的认养");
        binding.mainTitle.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
