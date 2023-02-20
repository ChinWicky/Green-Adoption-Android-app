package com.graduation_project.wicky.csa.fragment;

import android.arch.lifecycle.Observer;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.adapter.MListAdapter;
import com.graduation_project.wicky.csa.databinding.FragmentMarketBinding;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.utils.PopuWinUtil;
import com.graduation_project.wicky.csa.viewModel.MarketViewModel;
import com.graduation_project.wicky.csa.widget.MPopuWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by goldze on 2017/7/17.
 * 网络请求列表界面
 */

public class MarketFragment extends BaseFragment<FragmentMarketBinding, MarketViewModel> implements AdapterView.OnItemClickListener {

    private MPopuWindow categPopu, levelPopu;
    private int cateId = -1;
    private MListAdapter<String> popuAdapter;
    private List<String> cateStr = new ArrayList<>();

    @Override
    public void initParam() {
        super.initParam();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_market;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.observableList.clear();
        viewModel.initMarket();
        //请求网络数据
        viewModel.requestNetWork(viewModel.pageSize,1);
        //viewModel.requestData();
        cateStr.addAll(Arrays.asList(getResources().getStringArray(R.array.agri_type)));
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

        viewModel.uc.filterArrow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (viewModel.uc.filterArrow.getValue()) {
                    binding.ivCate.setImageResource(R.mipmap.ic_select_s);
                    if (categPopu == null) {
                        popuAdapter = new MListAdapter<String>(getActivity(), cateStr, R.layout.item_select, BR.string);
                        categPopu = new PopuWinUtil().listPopuWindow(getContext(),
                                popuAdapter, MarketFragment.this, 124.172f);
                    }
                    categPopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            binding.ivCate.setImageResource(R.mipmap.ic_select_n);
                        }
                    });
                    categPopu.showAsDropDown(binding.llScreen, getContext().getResources().getDimensionPixelSize(
                            R.dimen.popmenu_xoff),
                            getContext().getResources().getDimensionPixelSize(
                                    R.dimen.popmenu_yoff));
                } else {
                    binding.ivCate.setImageResource(R.mipmap.ic_select_n);
                }
            }
        });
    }

    @Override
    public void initBar() {
        binding.mainTitle.tvTitle.setText("认养市场");
        binding.mainTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.popu_lv_select:
                if (categPopu != null && categPopu.isShowing()) {
                    categPopu.dismiss();
                    binding.tvCate.setText(cateStr.get(i));
                    int[] typeId = getContext().getResources().getIntArray(R.array.reminder_methods_values3);
                    cateId = typeId[i];
                    //  presenter.getGoodsList(pageNumber, 10, cateId, level, areaId);
                    viewModel.requestProduct(cateId);
                } else if (levelPopu != null && levelPopu.isShowing()) {
//                levelPopu.dismiss();
//                marketBinding.tvLevel.setText(levelStr.get(position));
//                level = position - 1;
//                pageNumber = 1;
                    // presenter.getGoodsList(pageNumber, 10, cateId, level, areaId);
                }
                break;
        }
    }

}
