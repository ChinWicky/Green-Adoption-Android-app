package com.graduation_project.wicky.csa.utils;

import com.alibaba.fastjson.JSON;
import com.graduation_project.wicky.csa.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Author：SimGa
 * CoryRight：2018/11/16
 * PackageName：com.ewhale.imissyou.userside.utils
 */
public class PickerUtils {


    private static final PickerUtils ourInstance = new PickerUtils();


    public static PickerUtils getInstance() {
        return ourInstance;
    }



    public void pickerOption(BaseActivity mContext, String title, String[] items, final onCallBackOptions onCallBackOptions) {
        OptionPicker peiSongPicker = new OptionPicker(mContext, items);
        peiSongPicker.setDividerRatio(WheelView.DividerConfig.FILL);
        peiSongPicker.setTextSize(17);
        peiSongPicker.setTitleTextSize(17);
        peiSongPicker.setCancelTextSize(17);
        peiSongPicker.setSubmitTextSize(17);
        peiSongPicker.setCycleDisable(true);
        peiSongPicker.setTitleText(title);
        peiSongPicker.setTopHeight(50);
        peiSongPicker.setHeight((int) (DensityUtil.getHeightInPx(mContext) / 2.5));
        peiSongPicker.setDividerColor(mContext.getResources().getColor(R.color.colorPrimary));
        peiSongPicker.setSubmitTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        peiSongPicker.setCancelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        peiSongPicker.setTextColor(mContext.getResources().getColor(R.color.colorPrimary),
                mContext.getResources().getColor(R.color.text_999999));
        peiSongPicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        peiSongPicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        peiSongPicker.setAnimationStyle(R.style.bottom_int_out_dialog_style);
        peiSongPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if (onCallBackOptions != null) {
                    onCallBackOptions.onOptionPicked(index, item);
                }
            }
        });
        peiSongPicker.show();
    }

    public void pickerYearMouthDay(BaseActivity mContext, String title , final onCallBackDate onCallBackDate) {
        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = new DatePicker(mContext);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setTextSize(17);
        datePicker.setTitleTextSize(17);
        datePicker.setCancelTextSize(17);
        datePicker.setSubmitTextSize(17);
        datePicker.setCycleDisable(true);
        datePicker.setTitleText(title);
        datePicker.setTopHeight(50);
        datePicker.setUseWeight(false);
        datePicker.setHeight((int) (DensityUtil.getHeightInPx(mContext) / 2.5));
        datePicker.setDividerColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setSubmitTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setCancelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setTextColor(mContext.getResources().getColor(R.color.colorPrimary),
                mContext.getResources().getColor(R.color.text_999999));
        datePicker.setLabelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        datePicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        datePicker.setAnimationStyle(R.style.bottom_int_out_dialog_style);
        datePicker.setRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setRangeEnd(calendar.get(Calendar.YEAR) + 99, 12, 31);
        datePicker.setResetWhileWheel(false);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (onCallBackDate != null) {
                    onCallBackDate.onOptionPicked(year, month, day);
                }
            }
        });
        datePicker.show();
    }



    public void pickerYearMouth(BaseActivity mContext, String title , final onCallBackDate onCallBackDate) {
        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = new DatePicker(mContext, DateTimePicker.YEAR_MONTH);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setTextSize(17);
        datePicker.setTitleTextSize(17);
        datePicker.setCancelTextSize(17);
        datePicker.setSubmitTextSize(17);
        datePicker.setCycleDisable(true);
        datePicker.setTitleText(title);
        datePicker.setTopHeight(50);
        datePicker.setUseWeight(true);
        datePicker.setUseWeight(false);
        datePicker.setHeight((int) (DensityUtil.getHeightInPx(mContext) / 2.5));
        datePicker.setDividerColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setSubmitTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setCancelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setTextColor(mContext.getResources().getColor(R.color.colorPrimary),
                mContext.getResources().getColor(R.color.text_999999));
        datePicker.setLabelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        datePicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        datePicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        datePicker.setAnimationStyle(R.style.bottom_int_out_dialog_style);
        datePicker.setRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        datePicker.setRangeEnd(calendar.get(Calendar.YEAR) + 99, 12);
        datePicker.setResetWhileWheel(false);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                if (onCallBackDate != null) {
                    onCallBackDate.onOptionPicked(year, month, "");
                }
            }
        });
        datePicker.show();
    }

    public void pickerAddress(BaseActivity mContext, final onCallBackAddress callBackAddress) {
        ArrayList<Province> data = new ArrayList<>();
        try {
            String json = ConvertUtils.toString(mContext.getAssets().open("cityChoose.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker addressPicker = new AddressPicker(mContext, data);
            addressPicker.setHideProvince(false);
            addressPicker.setHideCounty(false);
            addressPicker.setTitleText("所在地区");
            addressPicker.setTextSize(17);
            addressPicker.setTitleTextSize(17);
            addressPicker.setCancelTextSize(17);
            addressPicker.setSubmitTextSize(17);
            addressPicker.setTopHeight(50);
            addressPicker.setHeight((int) (DensityUtil.getHeightInPx(mContext) / 2.5));
            addressPicker.setDividerColor(mContext.getResources().getColor(R.color.colorPrimary));
            addressPicker.setSubmitTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            addressPicker.setCancelTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            addressPicker.setCycleDisable(true);
            addressPicker.setTextColor(mContext.getResources().getColor(R.color.colorPrimary),
                    mContext.getResources().getColor(R.color.text_999999));
            addressPicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
            addressPicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
            addressPicker.setAnimationStyle(R.style.bottom_int_out_dialog_style);
            addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    if (callBackAddress != null) {
                        callBackAddress.onAddressPicked(province, city, county);
                    }
                }
            });
            addressPicker.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private onCallBackAddress onCallBackAddress;

    private onCallBackOptions onCallBackOptions;

    private onCallBackDate onCallBackDate;

    public void setOnCallBackAddress(PickerUtils.onCallBackAddress onCallBackAddress) {
        this.onCallBackAddress = onCallBackAddress;
    }

    public void setOnCallBackOptions(onCallBackOptions onCallBackOptions) {
        this.onCallBackOptions = onCallBackOptions;
    }

    public void setOnCallBackDate(onCallBackDate onCallBackDate) {
        this.onCallBackDate = onCallBackDate;
    }

    public interface onCallBackAddress {
        void onAddressPicked(Province province, City city, County county);
    }

    public interface onCallBackOptions {
        void onOptionPicked(int index, String item);
    }

    public interface onCallBackDate {
        void onOptionPicked(String year, String month, String day);
    }
}
