package com.graduation_project.wicky.csa.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.MainActivity;
import com.graduation_project.wicky.csa.bean.Headlines;
import com.graduation_project.wicky.csa.databinding.FragmentHomeBinding;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.utils.GlideImageLoader;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Wicky on 2019/2/15.
 */

public class HomeFragment extends BaseFragment {
    public ArrayList<String> imagePath = new ArrayList<>();
    FragmentHomeBinding binding;
    private List<Headlines> mNewsList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        firstInit(savedInstanceState);
        initBanner();
       // initNotice();
        return binding.getRoot();
    }

    @Override
    protected void firstInit(Bundle savedInstanceState) {
        binding.mainTitle.ivBack.setVisibility(View.GONE);
        binding.mainTitle.tvRight.setText("联系客服");
        binding.mainTitle.tvRight.setTextColor(Color.WHITE);
        binding.mainTitle.tvRight.setVisibility(View.VISIBLE);
        binding.mainTitle.tvTitle.setText("首页");
        //binding.tvNotice.setmTexts();
    }


    public void initBanner() {
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        //binding.homeBanner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器，图片加载器在下方
        binding.homeBanner.setImageLoader(glideImageLoader);


        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        binding.homeBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置指示器的位置，小点点，左中右。
        binding.homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置轮播间隔时间
        binding.homeBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        binding.homeBanner.isAutoPlay(true);
        //设置图片网址或地址的集合
        binding.homeBanner.setImages(getBannerImage());

        //设置轮播图的标题集合
        binding.homeBanner.setBannerTitles(titleList)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                // .setOnBannerListener(this)
                .start();
    }

    public void initNotice(){
        binding.tvNotice.setFrontColor(Color.parseColor("#B40000"));
        binding.tvNotice.setmTexts(mNewsList);

    }

    public ArrayList<String> getBannerImage() {
        imagePath.clear();
        mNewsList.clear();
        titleList.clear();
        SQLiteDatabase db = new DatabaseHelper(getActivity(),"greenAdopt",null,1).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Photo where type = 1", null);
        cursor.moveToLast();
        for(int i=0; i<5 && i<cursor.getCount(); i++){
            String photoUrl = cursor.getString(cursor.getColumnIndex("photoUrl"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            imagePath.add(photoUrl);
            titleList.add(title);
            Headlines headlines = new Headlines();
            headlines.setText(title);
            mNewsList.add(headlines);
            cursor.moveToPrevious();
        }
        db.close();
        return imagePath;
    }

}
