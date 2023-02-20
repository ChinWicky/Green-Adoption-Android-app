package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.db.DatabaseHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class PayViewModel extends BaseViewModel {

    public ObservableField<Order> entity = new ObservableField<>();
    public ObservableBoolean checked = new ObservableBoolean();
    public User user;
    public int payType = -1;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public BindingCommand pay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(!checked.get()){
                ToastUtils.showShort("请选择支付方式");
                return;
            }
            Observable.just("")
                    .delay(3, TimeUnit.SECONDS) //延迟3秒
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("支付中...");
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            payOrder();
                            dismissDialog();
                            finish();
                            ToastUtils.showShort("支付成功");
                        }
                    });
        }
    });
    public BindingCommand checkedOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            checked.set(uc.pCheck.getValue() == null || !uc.pCheck.getValue());
        }
    });
    public PayViewModel(@NonNull Application application) {
        super(application);
    }

    public void initData(Order entity) {
        this.entity.set(entity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> pCheck = new SingleLiveEvent<>();
    }

    public void payOrder(){
        SQLiteDatabase db = new DatabaseHelper(this.getApplication(),"greenAdopt",null,1).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("orderStatus",1);
        db.update("Orders", values, "orderNumber=?",  new String[]{entity.get().getOrderNumber()});
        db.close();



    }
}
