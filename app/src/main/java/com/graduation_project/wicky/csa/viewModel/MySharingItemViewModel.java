package com.graduation_project.wicky.csa.viewModel;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.ExamineDetailActivity;
import com.graduation_project.wicky.csa.activity.OrderDetailActivity;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2017/7/17.
 */

public class MySharingItemViewModel extends ItemViewModel<MySharingViewModel> {

    public ObservableField<Order> entity = new ObservableField<>();

    public ObservableField<Good> good = new ObservableField<>();

    public ObservableField<User> suppler = new ObservableField<>();

    public ObservableField<String> orderStatus = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public ObservableField<String> orderNumber = new ObservableField<>();


    public Drawable drawableImg;
    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
            if (entity.get().getOrderStatus() == 2 || entity.get().getOrderStatus() == 3) {
                Bundle bundle = new Bundle();
                bundle.putString("examineIn", "sharing");
                bundle.putParcelable("order", entity.get());
                bundle.putParcelable("suppler", suppler.get());
                if (viewModel.position == 2)
                    bundle.putBoolean("isExamined", true);
                if (entity.get().getGoodList() != null && entity.get().getGoodList().size() > 0) {
                    bundle.putParcelable("good", entity.get().getGoodList().get(0));
                } else {
                    bundle.putParcelable("good", new Good());
                }
                viewModel.startActivity(ExamineDetailActivity.class, bundle);
            } else {
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("order", entity.get());
                viewModel.startActivity(OrderDetailActivity.class, mBundle);
            }

        }
    });

    public MySharingItemViewModel(@NonNull MySharingViewModel viewModel, Order entity, Good good, User suppler) {
        super(viewModel);
        this.entity.set(entity);
        this.good.set(good);
        this.suppler.set(suppler);
        unitPrice.set(String.format("%1$.2f元", entity.getSum()));
        orderNumber.set("订单号：" + entity.getOrderNumber());

        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);

        switch (entity.getOrderStatus()) {
            case 0:
                orderStatus.set("未支付");
                break;
            case 1:
                orderStatus.set("认养中");
                break;
            case -1:
                orderStatus.set("已完成");
                break;
            case 2:
                orderStatus.set("待审核");
                break;
            case 3:
                orderStatus.set("已审核");
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
