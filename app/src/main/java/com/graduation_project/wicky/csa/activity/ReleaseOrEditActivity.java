package com.graduation_project.wicky.csa.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.adapter.PublicPicAdapter;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.databinding.ActivityReleaseOrEditGoodBinding;
import com.graduation_project.wicky.csa.utils.PickerUtils;
import com.graduation_project.wicky.csa.utils.PictureUtils;
import com.graduation_project.wicky.csa.viewModel.ReleaseOrEditViewModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import me.goldze.mvvmhabit.base.BaseActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class ReleaseOrEditActivity extends BaseActivity<ActivityReleaseOrEditGoodBinding, ReleaseOrEditViewModel> implements EasyPermissions.PermissionCallbacks {

    Good good;


    protected ImageView mIvRight;
    private String goodsImgList = "";
    private String goodsDetailImgList = "";

    @Override
    public void initParam() {
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if (mBundle != null && mBundle.getParcelable("good") != null) {
            good = mBundle.getParcelable("good");
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_release_or_edit_good;
    }

    @Override
    public void initData() {
        if (good == null) {
            viewModel.setEntity(new Good());
        } else {
            viewModel.setEntity(good);
        }

    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pChooseType.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                PickerUtils.getInstance().pickerOption(mContext, "请选择品类", getResources().getStringArray(R.array.agri_type_edit), new PickerUtils.onCallBackOptions() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        //ToastUtils.showShort("品类");
                        binding.tvCategory.setText(item);
                        switch (item) {
                            case "种植业":
                                binding.tvPriceUnit.setText("元/亩/年");
                                binding.tvGoodInventoryUnit.setText("亩");
                                break;
                            case "畜牧业":
                                binding.tvPriceUnit.setText("元/只/年");
                                binding.tvGoodInventoryUnit.setText("只");
                                break;
                            case "渔业":
                                binding.tvPriceUnit.setText("元/千克/年");
                                binding.tvGoodInventoryUnit.setText("千克");
                                break;
                            default:
                                break;
                        }
                        //category = getResources().getIntArray(R.array.reminder_methods_values2)[index];
                    }
                });
            }
        });
        viewModel.uc.pChoosePlace.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                PickerUtils.getInstance().pickerAddress(mContext, new PickerUtils.onCallBackAddress() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        //ToastUtils.showShort("地址");
                        if (county.getName().equals("-")) {
                            binding.tvCountryOfOrigin.setText(province.getName() + city.getName());
                            viewModel.entity.get().setPlaceId(city.getAreaId());
                        } else {
                            binding.tvCountryOfOrigin.setText(province.getName() + city.getName() + county.getName());
                            viewModel.entity.get().setPlaceId(city.getAreaId());
                        }
                    }
                });
            }
        });

        viewModel.detailspicAdapter = new PublicPicAdapter(getApplicationContext(), viewModel.detailsimagePath, 5);
        viewModel.masterpicAdapter = new PublicPicAdapter(getApplicationContext(), viewModel.masterimagePath, 5);
        binding.detailsGoodsGridview.setAdapter(viewModel.detailspicAdapter);
        binding.masterGoodsGridview.setAdapter(viewModel.masterpicAdapter);
        initListener();
    }

    protected void initListener() {
        viewModel.detailspicAdapter.setCallBack(new PublicPicAdapter.DeleteCallBack() {
            @Override
            public void onDeleteListener(int position) {
                viewModel.detailsimagePath.remove(position);
                viewModel.detailspicAdapter.notifyDataSetChanged();
            }
        });
        viewModel.masterpicAdapter.setCallBack(new PublicPicAdapter.DeleteCallBack() {
            @Override
            public void onDeleteListener(int position) {
                viewModel.masterimagePath.remove(position);
                viewModel.masterpicAdapter.notifyDataSetChanged();
            }
        });
        binding.detailsGoodsGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == viewModel.detailsimagePath.size()) {
                    requestPermissionsMain(viewModel.detailsimagePath.size(), REQUEST_CODE_CHOOSE_DETAIL_IMAGE);
                }
            }
        });
        binding.masterGoodsGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == viewModel.masterimagePath.size()) {
                    requestPermissionsMain(viewModel.masterimagePath.size(), REQUEST_CODE_CHOOSE_QRCODE_MASTER_IMAGE);
                }
            }
        });

//        mIvRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //showDialog("...");
//                for (String s : detailsimagePath) {
//                    goodsImgList = goodsImgList + s + ",";
//                }
//                for (String s : masterimagePath) {
//                    goodsDetailImgList = goodsDetailImgList + s + ",";
//                }
//                saveOrEditGoods();
//            }
//        });
    }

    @Override
    public void initBar() {
        binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (good != null) {
            binding.titleBar.tvTitle.setText("产品编辑");
        } else {
            binding.titleBar.tvTitle.setText("产品发布");
        }
    }

    private static final int REQUEST_CODE_CHOOSE_DETAIL_IMAGE = 666;

    private static final int REQUEST_CODE_CHOOSE_QRCODE_MASTER_IMAGE = 555;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_DETAIL_IMAGE:
                List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                for(LocalMedia localMedia:mediaList){
                    viewModel.uploadPhoto(localMedia);
                }

                binding.tvDetailsNum.setText(viewModel.detailsimagePath.size() + "/5");
//               UpLoadImageUtils.upLoad(mediaList, new UpLoadImageUtils.onCallBackList() {
//                    @Override
//                    public void complete(List<String> key) {
//                        dismissDialog();
//                        detailsimagePath.addAll(key);
//                        detailspicAdapter.notifyDataSetChanged();
//                        binding.tvDetailsNum.setText(detailsimagePath.size() + "/5");
//                    }
//
//                    @Override
//                    public void fail() {
//                        dismissDialog();
//                    }
//                });

                break;
            case REQUEST_CODE_CHOOSE_QRCODE_MASTER_IMAGE:
                List<LocalMedia> masterimageslist = PictureSelector.obtainMultipleResult(data);
                showDialog("...");
//                UpLoadImageUtils.upLoad(masterimageslist, new UpLoadImageUtils.onCallBackList() {
//                    @Override
//                    public void complete(List<String> key) {
//                        dismissDialog();
//                        masterimagePath.addAll(key);
//                        masterpicAdapter.notifyDataSetChanged();
//                        tvMasterNum.setText(masterimagePath.size() + "/5");
//                    }
//
//                    @Override
//                    public void fail() {
//                        dismissDialog();
//                    }
//                });
                break;
        }
    }

    private static final int REQUEST_CODE_PERMISSION_2 = 10002;

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_2)
    private void requestPermissionsMain(int num, int request) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            PictureUtils.openAluamMore(ReleaseOrEditActivity.this, request, 5 - num);
        } else {
            EasyPermissions.requestPermissions(this, "申请获取相关权限", REQUEST_CODE_PERMISSION_2, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).setTitle("权限申请")
                    .setRationale("申请获取权限")
                    .setPositiveButton(getString(R.string.confirm))
                    .setNegativeButton(getString(R.string.cancle))
                    .build()
                    .show();
        }
    }
}
