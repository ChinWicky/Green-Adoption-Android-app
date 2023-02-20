package com.graduation_project.wicky.csa.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.databinding.ActivityManageOrderBinding;
import com.graduation_project.wicky.csa.databinding.ActivityUserInfoBinding;
import com.graduation_project.wicky.csa.fragment.OrderFragment;
import com.graduation_project.wicky.csa.widget.SelectPhotoDialog;
import com.graduation_project.wicky.csa.widget.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//
public class UserInfoActivity extends MBaseActivity {

    private ActivityUserInfoBinding userInfoBinding;
    private SelectPhotoDialog photoDialog;
    @Override
    protected void setBaseTitle(TextView titleView) {

    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        userInfoBinding.setHandler(new UserInfoEvenHandler());
        init(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userInfoBinding.userInfoTitle.tvTitle.setText("个人资料");
        userInfoBinding.userInfoTitle.ivBack.setOnClickListener(new View.OnClickListener() {
        @Override
           public void onClick(View view) {
           finish();
           }
        });;
      photoDialog = new SelectPhotoDialog((MBaseActivity)this);
    }



    public static void open(MBaseActivity mContext){
        mContext.startActivity(null, UserInfoActivity.class);
    }


    //事件处理
    public class UserInfoEvenHandler{
        private Context mContext;



        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_myAvatar:
                    photoDialog.show();
                    break;
                case R.id.rl_nickname://昵称
//                    if (mUserInfo == null) {
//                        return;
//                    }
//                    UpdateNicknameActivity.open(mContext, mUserInfo.getNickname());
                    break;
                case R.id.rl_sex: //性别
//                    PickerUtils.getInstance().pickerOption(mContext, "性别", new String[]{"男", "女"}, new PickerUtils.onCallBackOptions() {
//                        @Override
//                        public void onOptionPicked(int index, String item) {
//                            presenter.updateUserInfo("sex", String.valueOf(index));
//                        }
//                    });
                    break;
                case R.id.rl_location:
//                    PickerUtils.getInstance().pickerAddress(mContext, new PickerUtils.onCallBackAddress() {
//                        @Override
//                        public void onAddressPicked(Province province, City city, County county) {
//                            if (county.getName().equals("-")) {
//                                chooseArea = province.getName() + city.getName();
//                                presenter.updateUserInfo("areaId", city.getAreaId());
//                            } else {
//                                chooseArea = province.getName() + city.getName() + county.getName();
//                                presenter.updateUserInfo("areaId", county.getAreaId());
//                            }
//                        }
//                    });
                    break;
                case R.id.rl_safe: // 密码
//                    if (mUserInfo == null) {
//                        return;
//                    }
//                    UpDatePwdFirstActivity.open(mContext, mUserInfo.getPhone());
                    break;
                case R.id.rl_status: //状态
                   // BuyerAuthActivity.open(mContext);
                    break;
                default:
                    break;
            }
        }
    }


}
