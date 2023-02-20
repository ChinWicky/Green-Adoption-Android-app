package com.graduation_project.wicky.csa.viewModel;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.OrderDetailActivity;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.UserApiService;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2017/7/17.
 */

public class MyAdoptItemViewModel extends ItemViewModel<MyAdoptViewModel> {

    public ObservableField<Order> entity = new ObservableField<>();

    public ObservableField<Good> good = new ObservableField<>();

    public ObservableField<String> orderStatus = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public ObservableField<String> orderNumber = new ObservableField<>();

    public Drawable drawableImg;
    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("order", entity.get());
            viewModel.startActivity(OrderDetailActivity.class, mBundle);
        }
    });


//    public MyAdoptItemViewModel(@NonNull MyAdoptViewModel viewModel, Good entity) {
//        super(viewModel);
//        this.good.set(entity);
//    }



    public MyAdoptItemViewModel(@NonNull MyAdoptViewModel viewModel, Order entity ,Good good) {
        super(viewModel);
        this.entity.set(entity);
        this.good.set(good);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
        unitPrice.set(String.format("%1$.2f元", entity.getSum()));
        orderNumber.set("订单号：" + entity.getOrderNumber());
//        if (entity.getGoodList() != null && entity.getGoodList().size()>0) {
//            this.good.set(entity.getGoodList().get(0));
//            if(entity.getGoodList().get(0).getGoodsImgList().size()!=0)
//            Log.v("goodImg----",entity.getGoodList().get(0).getGoodsImgList().get(0));
//            else
//                Log.v("goodImg----",String.valueOf(entity.getGoodList().get(0).getGoodsImgList().size()));
//        }

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
        //requestSuppler(entity.getGoodList().get(0));
    }

//    public void requestSuppler(Good entity) {
//        RetrofitClient.getInstance().create(UserApiService.class)
//                .getUser(entity.getSupplierId())
//                .compose(RxUtils.schedulersTransformer()) //线程调度
//                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
//                .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
//                    @Override
//                    public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
//                        //请求成功
//                        if (response.getCode() == 1) {
//                            ModelUser modelUser = response.getParams().getContent().get(0);
//                            suppler.set(Converter.toUser(modelUser));
//                        } else {
//                            //code错误时也可以定义Observable回调到View层去处理
//                            ToastUtils.showShort("数据错误");
//                        }
//                    }
//                }, new Consumer<ResponseThrowable>() {
//                    @Override
//                    public void accept(ResponseThrowable throwable) throws Exception {
//                        //关闭对话框
//                        ToastUtils.showShort(throwable.message);
//                        throwable.printStackTrace();
//                    }
//                });
//    }
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
