package com.graduation_project.wicky.csa.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.databinding.ActivityGoodsDetailBinding;
import com.graduation_project.wicky.csa.utils.GlideImageLoader;
import com.graduation_project.wicky.csa.viewModel.GoodsDetailViewModel;
import com.graduation_project.wicky.csa.widget.CallPhoneDialog;
import com.graduation_project.wicky.csa.widget.UpdateNumberDialog;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Wicky on 2019/2/13.
 */

public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding, GoodsDetailViewModel> implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_PERMISSION_2 = 10002;
    private String contantPhone = "123456";
    private CallPhoneDialog callPhoneDialog;

    private UpdateNumberDialog mNumberDialog;

    private Good good;

    @Override
    public void initParam() {
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if (mBundle != null) {
            good = mBundle.getParcelable("good");
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setEntity(good);
        viewModel.initSuppler();
        //contantPhone = viewModel.suppler.get().getPhone();
        initBanner();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.numButton.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mNumberDialog = new UpdateNumberDialog(mContext);
                mNumberDialog.setOnCallBack(new UpdateNumberDialog.onCallBack() {
                    @Override
                    public void onConfirm(int number) {
                        viewModel.num.set(number);
                    }
                });
                mNumberDialog.setNumber(viewModel.num.get());
                mNumberDialog.show();
                //ToastUtils.showShort(viewModel.num.get());
            }
        });
        viewModel.uc.phoneContact.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (viewModel.suppler.get() != null) {
                    contantPhone = viewModel.suppler.get().getPhone();
                }
                callPhoneDialog = new CallPhoneDialog(mContext, getString(R.string.comfig_call_service_phone), contantPhone);
                callPhoneDialog.setCallback(new CallPhoneDialog.Callback() {
                    @Override
                    public void callback() {
                        callphoneTask();
                    }

                    @Override
                    public void cancle() {

                    }
                });
                callPhoneDialog.show();
            }
        });

    }

    public void initBanner() {
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        //???????????????????????????????????????????????????????????????????????????
        binding.banner.setBannerStyle(BannerConfig.CENTER);
        //????????????????????????????????????????????????
        binding.banner.setImageLoader(glideImageLoader);
        //??????????????????????????????
        //binding.banner.setBannerTitles(list_title);
        //?????????????????????????????????????????????????????????????????????????????????????????????
        binding.banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //???????????????????????????????????????????????????
        binding.banner.setIndicatorGravity(BannerConfig.CENTER);
        //????????????????????????
        binding.banner.setDelayTime(3000);
        //???????????????????????????????????????????????????
        binding.banner.isAutoPlay(true);
        //????????????????????????????????????
        binding.banner.setImages(viewModel.getBannerImage())
                //???????????????????????????????????????????????????????????????????????????????????????????????????
                // .setOnBannerListener(this)
                .start();
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_2)
    private void callphoneTask() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getApplication().getApplicationContext(), perms)) {
            if (viewModel.suppler.get() != null) {
                contantPhone = viewModel.suppler.get().getPhone();
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contantPhone));
            startActivity(intent);
        } else {
            EasyPermissions.requestPermissions(this, "????????????????????????", REQUEST_CODE_PERMISSION_2, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (viewModel.suppler.get() != null) {
            contantPhone = viewModel.suppler.get().getPhone();
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contantPhone));
        startActivity(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setTitle("????????????")
                    .setRationale("????????????????????????")
                    .setPositiveButton(getString(R.string.confirm))
                    .setNegativeButton(getString(R.string.cancle))
                    .build()
                    .show();
        }
    }

    @Override
    public void initBar() {
        binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.titleBar.tvTitle.setText("????????????");
    }
}
