package com.graduation_project.wicky.csa.viewModel;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.GoodsDetailActivity;
import com.graduation_project.wicky.csa.bean.Good;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2017/7/17.
 */

public class MarketItemViewModel extends ItemViewModel<MarketViewModel> {

    public ObservableField<Good> entity = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public Drawable drawableImg;
    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
            if (entity.get().getId() == -1) {
                ToastUtils.showShort(entity.get().getName());
            } else {
                //跳转到详情界面,传入条目的实体对象
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("good", entity.get());
                //ToastUtils.showShort(entity.get().getName() + "," + entity.get().getCategory());
                viewModel.startActivity(GoodsDetailActivity.class, mBundle);
            }
        }
    });

    public MarketItemViewModel(@NonNull MarketViewModel viewModel, Good entity) {
        super(viewModel);
        this.entity.set(entity);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
        switch (entity.getCategory()) {
            case 1:   //种植业
//                goodType.set("种植业");
//                goodUnit.set("亩");
                unitPrice.set(String.format("%1$.2f元/亩/年", entity.getPrice()));
                break;
            case 2:  //畜牧业
//                goodType.set("畜牧业");
//                goodUnit.set("只");
                unitPrice.set(String.format("%1$.2f元/只/年", entity.getPrice()));
                break;
            case 3:  //渔业
//                goodType.set("渔业");
//                goodUnit.set("千克");
                unitPrice.set(String.format("%1$.2f元/千克/年", entity.getPrice()));
                break;
            default:
                break;
        }
    }
    //条目的长按事件
//    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
////            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
//            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
//            viewModel.deleteItemLiveData.setValue(MarketItemViewModel.this);
//        }
//    });
//    /**
//     * 可以在xml中使用binding:currentView="@{viewModel.titleTextView}" 拿到这个控件的引用, 但是强烈不推荐这样做，避免内存泄漏
//     **/
//    private TextView tv;
//    //将标题TextView控件回调到ViewModel中
//    public BindingCommand<TextView> titleTextView = new BindingCommand(new BindingConsumer<TextView>() {
//        @Override
//        public void call(TextView tv) {
//            NetWorkItemViewModel.this.tv = tv;
//        }
//    });
}
