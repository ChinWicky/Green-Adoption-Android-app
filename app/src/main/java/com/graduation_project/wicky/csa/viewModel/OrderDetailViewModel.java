package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Message;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.GoodApiService;
import com.graduation_project.wicky.csa.model.service.UserApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class OrderDetailViewModel extends BaseViewModel {

    private int goodId;

    public ObservableField<Order> entity = new ObservableField();


    public ObservableField<User> user = new ObservableField<>();

    public ObservableField<Good> good = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();


    public ObservableField<String> orderStatus = new ObservableField<>();

    public final BindingRecyclerViewAdapter<MessageItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public ObservableList<MessageItemViewModel> observableList = new ObservableArrayList<>();

    public ItemBinding<MessageItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_message);

    public OrderDetailViewModel.UIChangeObservable uc = new OrderDetailViewModel.UIChangeObservable();

    public BindingCommand phoneContactOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.phoneContact.setValue(uc.phoneContact.getValue() == null || !uc.phoneContact.getValue());
        }
    });

    // public Drawable drawableImg;
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.finishLoadmore.set(!uc.finishLoadmore.get());
            requestMessage();
        }
    });

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            observableList.clear();
            uc.finishRefreshing.set(!uc.finishRefreshing.get());
            requestMessage();
        }
    });


    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setEntity(Order entity) {
        this.entity.set(entity);
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

    public void initData() {
        if (entity.get().getAdopterId() == ((User) Hawk.get(HawkKey.USER)).getId()) {
            RetrofitClient.getInstance().create(UserApiService.class)
                    .getUser(entity.get().getSupplerId())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
                        @Override
                        public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
                            //请求成功
                            if (response.getCode() == 1) {
                                ModelUser modelUser = response.getParams().getContent().get(0);
                                user.set(Converter.toUser(modelUser));
                            } else {
                                //code错误时也可以定义Observable回调到View层去处理
                                ToastUtils.showShort("数据错误");
                            }
                        }
                    }, new Consumer<ResponseThrowable>() {
                        @Override
                        public void accept(ResponseThrowable throwable) throws Exception {
                            ToastUtils.showShort(throwable.message);
                            throwable.printStackTrace();
                        }
                    });
        } else {
            RetrofitClient.getInstance().create(UserApiService.class)
                    .getUser(entity.get().getAdopterId())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
                        @Override
                        public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
                            //请求成功
                            if (response.getCode() == 1) {
                                for (ModelUser modelUser : response.getParams().getContent()) {
                                    user.set(Converter.toUser(modelUser));
                                }
                            } else {
                                //code错误时也可以定义Observable回调到View层去处理
                                ToastUtils.showShort("数据错误");
                            }
                        }
                    }, new Consumer<ResponseThrowable>() {
                        @Override
                        public void accept(ResponseThrowable throwable) throws Exception {
                            ToastUtils.showShort(throwable.message);
                            throwable.printStackTrace();
                        }
                    });
        }

        if (entity.get().getGoodList() != null && entity.get().getGoodList().size() > 0) {
            goodId = entity.get().getGoodList().get(0).getId();
        }

        RetrofitClient.getInstance().create(GoodApiService.class)
                .goodGet(goodId)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelGood>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelGood>> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            for (ModelGood modelGood : response.getParams().getContent()) {
                                good.set(Converter.toGood(modelGood));
                            }

                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                });
    }

    //模拟加载数据
    public void requestMessage() {
//        for (Message entity : getMessage()) {
//            MessageItemViewModel itemViewModel = new MessageItemViewModel(OrderDetailViewModel.this, entity);
//            //双向绑定动态添加Item
//            observableList.add(itemViewModel);
//        }
    }

    protected List<Message> getMessage() {
        //dataList.clear();
        List<Message> dataList = new ArrayList();
        dataList.add(new Message());
        dataList.add(new Message());

        return dataList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }

    public class UIChangeObservable {
        //下拉刷新完成
        public ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        //上拉加载完成
        public ObservableBoolean finishLoadmore = new ObservableBoolean(false);

        public SingleLiveEvent<Boolean> phoneContact = new SingleLiveEvent<>();
    }


}
