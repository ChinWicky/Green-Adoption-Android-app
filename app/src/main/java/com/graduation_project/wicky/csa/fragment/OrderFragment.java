package com.graduation_project.wicky.csa.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.adapter.LoadMoreWrapper;
import com.graduation_project.wicky.csa.adapter.RecyclerViewAdapter;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.databinding.FragmentOrderBinding;
import com.graduation_project.wicky.csa.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wicky on 2019/2/15.
 */

public class OrderFragment extends BaseFragment {
    FragmentOrderBinding orderBinding;
    List<Order> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;


    public static OrderFragment getInstance(String title, int orderStatus) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("orderStatus", orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        orderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        firstInit(savedInstanceState);
        initRecyclerView(savedInstanceState);
        return orderBinding.getRoot();
    }

    @Override
    protected void firstInit(Bundle savedInstanceState) {

    }

    protected void initRecyclerView(Bundle savedInstanceState) {
        orderBinding.recyclerView.setHasFixedSize(true);
        orderBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置刷新控件颜色
        orderBinding.swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        //getData();
        RecyclerViewAdapter<Order> marketAdapter = new RecyclerViewAdapter<Order>(dataList,R.layout.item_order, BR.item){
//            @Override
//            public void addListener(View root, final Order itemData, final int position) {
//
//                root.findViewById(R.id.tv_goods).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), itemData.getName(), Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getActivity(),GoodsDetailActivity.class));
//                    }
//                });
//            }
        };


        loadMoreWrapper = new LoadMoreWrapper(marketAdapter);
        orderBinding.recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        orderBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                //getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                orderBinding.swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (orderBinding.swipeRefreshLayout != null && orderBinding.swipeRefreshLayout.isRefreshing()) {
                            orderBinding.swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        orderBinding.recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }



}
