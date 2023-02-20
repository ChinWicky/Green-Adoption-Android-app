package com.graduation_project.wicky.csa.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.graduation_project.wicky.csa.R;


/**
 * Summary ：加载网络图片使用的工具类，基于Glide
 * Created by zhangdm on 2016/2/20.
 */
public class GlideUtil {
    public static final String TAG = "GlideUtil";

    /**
     * Glide的请求管理器类
     */
    private static RequestManager mRequestManager;
    private static Context mContext;

    /**
     * 初始化Glide工具
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
    }

    /**
     * Glide工具类是否已经初始化
     *
     * @return 已初始化则返回true
     */
    public static boolean isInit() {
        if (mContext == null || mRequestManager == null) {
            Log.i(TAG, TAG + "not init");
            return false;
        }
        return true;
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadPicture(String url, ImageView imageView) {
        loadPicture(url, imageView, -1);
    }

    /**
     * 加载正方形的图片
     *
     * @param imageView 目标控件
     */
    public static void loadPicture(int res, ImageView imageView) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        mRequestManager
                .load(res).into(imageView);
    }


    /**
     * 加载正方形的网络图片
     *
     * @param url        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadPicture(String url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        RequestBuilder builder = mRequestManager
                .load(url);
        if (defaultImg == -1) {
            defaultImg = R.drawable.default_image;
        }
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(defaultImg)
                .error(defaultImg)
                .priority(Priority.HIGH);
        builder = builder.apply(requestOptions);
        builder.into(imageView);
    }


    public static void loadCirclePicture(String url, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        RequestBuilder builder = mRequestManager
                .load(url);
        if (defaultImg == -1) {
            defaultImg = R.drawable.default_image;
        }
        RequestOptions requestOptions = new RequestOptions()
                .circleCrop()
                .placeholder(defaultImg)
                .error(defaultImg)
                .priority(Priority.HIGH);
        builder = builder.apply(requestOptions);
        builder.into(imageView);
    }


    /**
     * 加载正方形的本地图片
     *
     * @param res        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadPicture(int res, ImageView imageView, int defaultImg) {
        if (!isInit()) {
            return;
        }
        if (imageView == null) {
            return;
        }
        RequestBuilder builder = mRequestManager
                .load(res);
        if (defaultImg == -1) {
            defaultImg = R.drawable.default_image;
        }
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(defaultImg)
                .error(defaultImg)
                .priority(Priority.HIGH);
        builder = builder.apply(requestOptions);
        builder.into(imageView);
    }


    public static void clearAll() {
        new Thread() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();

            }
        }.start();
        Glide.get(mContext).clearMemory();


    }

    public static void clearMemory() {
        Glide.get(mContext).clearMemory();
    }
}
