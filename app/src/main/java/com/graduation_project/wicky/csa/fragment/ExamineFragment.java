package com.graduation_project.wicky.csa.fragment;

import android.content.pm.ActivityInfo;
import android.databinding.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.databinding.FragmentExamineBinding;
import com.graduation_project.wicky.csa.viewModel.ExamineViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by goldze on 2017/7/17.
 * 网络请求列表界面
 */

public class ExamineFragment extends BaseFragment<FragmentExamineBinding, ExamineViewModel> {


    private int position;

    @Override
    public void initParam() {
        super.initParam();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        position = this.getArguments().getInt("position");
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_examine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //请求网络数据
        viewModel.setPosition(position);
        //viewModel.requestNetWork();
        viewModel.requestData(position);

    }

    @Override
    public void initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
//        //监听删除条目
//        viewModel.deleteItemLiveData.observe(this, new Observer<MarketItemViewModel>() {
//            @Override
//            public void onChanged(@Nullable final MarketItemViewModel marketItemViewModel) {
//                int index = viewModel.getPosition(marketItemViewModel);
//                //删除选择对话框
//                MaterialDialogUtils.showBasicDialog(getContext(), "提示", "是否删除【" + marketItemViewModel.entity.get().getName() + "】？ position：" + index)
//                        .onNegative(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                ToastUtils.showShort("取消");
//                            }
//                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        viewModel.deleteItem(marketItemViewModel);
//                    }
//                }).show();
//            }
//        });
    }


}
