package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.activity.AdoptVMActivity;
import com.graduation_project.wicky.csa.activity.PayActivity;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.databinding.ActivityAdoptBinding;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.utils.CheckUtil;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class AdoptViewModel extends BaseViewModel {

    public ObservableField<String> year = new ObservableField<>();

    public ObservableField<String> payWay = new ObservableField<>();

    public ObservableField<String> remark = new ObservableField<>();

    public ObservableInt num = new ObservableInt();

    public ObservableField<Good> entity = new ObservableField<>();

    public ObservableDouble goodTotal = new ObservableDouble();

    public ObservableDouble insurePrice = new ObservableDouble(0);

    public ObservableDouble sum = new ObservableDouble();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public ObservableField<String> vNum = new ObservableField<>();

    public ObservableField<User> suppler = new ObservableField<>();

    public ActivityAdoptBinding binding;

    public int Y = 1;
    public ObservableBoolean checked = new ObservableBoolean();

    public BindingCommand checkedOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            checked.set(uc.pCheck.getValue() == null || !uc.pCheck.getValue());
        }
    });


    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public BindingCommand chooseAdoptYear = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pChooseYear.setValue(uc.pChooseYear.getValue() == null || !uc.pChooseYear.getValue());
        }
    });
    public BindingCommand choosePayWay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pChoosePayWay.setValue(uc.pChoosePayWay.getValue() == null || !uc.pChoosePayWay.getValue());
        }
    });
    public BindingCommand pay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            createOrder();
        }
    });

    public AdoptViewModel(@NonNull Application application) {
        super(application);
    }

    public void initData(Good entity,int num){
        this.entity.set(entity);
        this.num.set(num);
        goodTotal.set(entity.getPrice() * num);
        sum.set(goodTotal.get()*Y + insurePrice.get());
        switch (entity.getCategory()) {
            case 1:   //种植业
//                goodType.set("种植业");
//                goodUnit.set("亩");
                vNum.set(String.format("%d亩", num));
                unitPrice.set(String.format("%1$.2f元/亩/年", entity.getPrice()));
                break;
            case 2:  //畜牧业
//                goodType.set("畜牧业");
//                goodUnit.set("只");
                vNum.set(String.format("%d只", num));
                unitPrice.set(String.format("%1$.2f元/只/年", entity.getPrice()));
                break;
            case 3:  //渔业
//                goodType.set("渔业");
//                goodUnit.set("千克");
                vNum.set(String.format("%d千克", num));
                unitPrice.set(String.format("%1$.2f元/千克/年", entity.getPrice()));
                break;
            default:
                break;
        }
    }

    public void setY(int Y){
        this.Y = Y;
        sum.set(goodTotal.get()*Y + insurePrice.get());
    }

    public void setSuppler(User suppler){
        this.suppler.set(suppler);
    }

    public void setBinding(ActivityAdoptBinding binding){
        this.binding = binding;
    }

    public void createOrder() {
        if (CheckUtil.isNull(year.get())) {
            ToastUtils.showShort("请选择合约年限");
            return;
        }
        if (CheckUtil.isNull(payWay.get())) {
            ToastUtils.showShort("请选择付款类型");
            return;
        }
        if (!binding.cbAgree.isChecked()) {
            ToastUtils.showShort("请阅读并同意用户协议");
            return;
        }
        Order order = new Order();
        order.setCreateDate(new Date().getTime());
        order.setAmount(num.get());
        List<Good> goodList = new ArrayList<>();
        goodList.add(entity.get());
        order.setGoodList(goodList);
        order.setGoodTotal(goodTotal.get());
        order.setNote(remark.get());
        order.setSum(sum.get());
        order.setOrderStatus(0);
        SimpleDateFormat formate=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String orderNumber = formate.format(date);
        order.setOrderNumber(orderNumber);
        User user = Hawk.get(HawkKey.USER);
        order.setAdopterId(user.getId());
        order.setYear(Y);
        order.setSupplerId((goodList.get(0)).getSupplierId());
        //order.setUserId();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("order", order);
        addOrder(order);
        //ToastUtils.showShort(String.valueOf(order.getSum()),",",String.valueOf(sum.get()));
        startActivity(PayActivity.class, mBundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> pChooseYear = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> pChoosePayWay = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> pCheck = new SingleLiveEvent<>();
    }


    public void addOrder(Order order){
        try {
            SQLiteDatabase db = new DatabaseHelper(this.getApplication(),"greenAdopt",null,1).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("sum", order.getSum());
            values.put("goodTotal",order.getGoodTotal());
            values.put("goodId",order.getGoodList().get(0).getId());
            values.put("amount",order.getAmount());
            values.put("note",order.getNote());
            values.put("orderStatus",order.getOrderStatus());
            values.put("orderNumber",order.getOrderNumber());
            values.put("year",order.getYear());
            values.put("supplerId",order.getSupplerId());
            values.put("adopterId",order.getAdopterId());
            values.put("createDate",String.valueOf(order.getCreateDate()));
            db.insert("Orders",null, values);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
