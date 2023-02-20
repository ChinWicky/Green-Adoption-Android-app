package com.graduation_project.wicky.csa.activity;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.databinding.ActivityAdoptBinding;
import com.graduation_project.wicky.csa.utils.PickerUtils;
import com.graduation_project.wicky.csa.viewModel.AdoptViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class AdoptVMActivity extends BaseActivity<ActivityAdoptBinding,AdoptViewModel> {

    private Good good;
    private int num;
    private User suppler;

    @Override
    public void initParam(){
        Intent intent = this.getIntent();
        Bundle mBundle = intent.getExtras();
        if(mBundle != null){
            good = mBundle.getParcelable("entity");
            num = mBundle.getInt("num");
            suppler = mBundle.getParcelable("suppler");
        }
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_adopt;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(){
        viewModel.initData(good,num);
        viewModel.setBinding(binding);
        viewModel.setSuppler(suppler);
    }


    @Override
    public void initViewObservable(){
        viewModel.uc.pChooseYear.observe(this, new Observer<Boolean>(){
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                PickerUtils.getInstance().pickerOption(mContext, getString(R.string.contract_year),
                        new String[]{"1年", "2年","3年","4年"}, new PickerUtils.onCallBackOptions() {
                            @Override
                            public void onOptionPicked(int index, String item) {
                                binding.tvDeliveryType.setText(item);
                                switch (item){
                                    case "1年":
                                        viewModel.setY(1);
                                        //ToastUtils.showShort("1");
                                        break;
                                    case "2年":
                                        viewModel.setY(2);
                                       // ToastUtils.showShort("2");
                                        break;
                                    case "3年":
                                        viewModel.setY(3);
                                       // ToastUtils.showShort("3");
                                        break;
                                    case "4年":
                                        viewModel.setY(4);
                                       // ToastUtils.showShort("4");
                                        break;
                                    default:
                                      //  ToastUtils.showShort("no");
                                        break;
                                }
                               // viewModel.sum.set(viewModel.goodTotal.get()*viewModel.Y + viewModel.insurePrice.get());
                            }
                        });
            }
        });
        viewModel.uc.pChoosePayWay.observe(this, new Observer<Boolean>(){
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                PickerUtils.getInstance().pickerOption(mContext, getString(R.string.pay_type),
                        new String[]{"全额支付"}, new PickerUtils.onCallBackOptions() {
                            @Override
                            public void onOptionPicked(int index, String item) {
                                binding.tvPayType.setText(item);
                            }
                        });
            }
        });
    }

    @Override
    public void initBar() {
        binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.titleBar.tvTitle.setText("认养详细");
    }




}
