package com.graduation_project.wicky.csa.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.databinding.ActivityOrderDetailBinding;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.graduation_project.wicky.csa.viewModel.OrderDetailViewModel;
import com.graduation_project.wicky.csa.widget.CallPhoneDialog;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Wicky on 2019/2/13.
 */

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding, OrderDetailViewModel> implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_PERMISSION_2 = 10002;

    private String contantPhone = "123456";
    private CallPhoneDialog callPhoneDialog;
    private User user = Hawk.get(HawkKey.USER);


    private Order order;

    @Override
    public void initParam() {
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if (mBundle != null) {
            order = mBundle.getParcelable("order");
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setEntity(order);
        viewModel.requestMessage();
        viewModel.initData();

    }

    @Override
    public void initViewObservable() {
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //????????????
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //????????????????????????
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //????????????
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });

        viewModel.uc.phoneContact.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if (viewModel.user.get() != null) {
                    contantPhone = viewModel.user.get().getPhone();
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
        switch (order.getOrderStatus()) {
            case 0:
                binding.btnRight.setText("??????");
                binding.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle mBundle = new Bundle();
                        mBundle.putParcelable("order", order);
                        startActivity(PayActivity.class, mBundle);
                    }
                });
                break;
            case 1:
                if (order.getSupplerId() == user.getId()) {
                    binding.btnRight.setText("??????");
                    binding.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtils.showShort("?????????????????????????????????????????????????????????");
                        }
                    });
                } else {
                    binding.btnRight.setText("??????");
                    binding.btnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtils.showShort("?????????????????????????????????????????????????????????");
                        }
                    });
                    break;
                }
            case -1:
                binding.btnRight.setVisibility(View.GONE);
                ToastUtils.showShort("??????????????????");
                break;
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_2)
    private void callphoneTask() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getApplication().getApplicationContext(), perms)) {
            if (viewModel.user.get() != null) {
                contantPhone = viewModel.user.get().getPhone();
            }
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contantPhone));
            startActivity(intent);
        } else {
            EasyPermissions.requestPermissions(this, "????????????????????????", REQUEST_CODE_PERMISSION_2, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (viewModel.user.get() != null) {
            contantPhone = viewModel.user.get().getPhone();
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
