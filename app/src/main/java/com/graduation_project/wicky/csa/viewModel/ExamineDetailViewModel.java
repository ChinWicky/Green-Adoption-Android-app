package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.ModelGoodNoId;
import com.graduation_project.wicky.csa.model.service.GoodApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
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
import okhttp3.RequestBody;


public class ExamineDetailViewModel extends BaseViewModel {

    public ObservableField<Order> order = new ObservableField();

    public ObservableField<String> orderStatus = new ObservableField<>();

    public ObservableField<Good> entity = new ObservableField();

    public ObservableField<User> suppler = new ObservableField();


    public ObservableField<String> goodUnit = new ObservableField<>();

    public ObservableField<String> goodType = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public ArrayList<Integer> imagePath = new ArrayList<>();

    public User user = Hawk.get(HawkKey.USER);

    public UIChangeObservable uc = new UIChangeObservable();
    public BindingCommand rejectOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //reject
        }
    });
    // public Drawable drawableImg;
    public BindingCommand examineOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (user.isAdmin()) {
                agreeGood(entity.get());
            } else {
                //delete
            }
        }
    });

    public ExamineDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setOrder(Order order) {
        this.order.set(order);
        switch (order.getOrderStatus()) {
            case 0:
                orderStatus.set("?????????");
                break;
            case 1:
                orderStatus.set("?????????");
                break;
            case -1:
                orderStatus.set("?????????");
                break;
            case 2:
                orderStatus.set("?????????");
                break;
            case 3:
                orderStatus.set("?????????");
                break;
            default:
                break;
        }
    }

    public void setSuppler(User suppler) {
        this.suppler.set(suppler);

    }


    public void setEntity(Good entity) {
        this.entity.set(entity);
        Log.v("price", String.valueOf(entity.getCategory()));
        switch (entity.getCategory()) {
            case 1:   //?????????
                goodType.set("?????????");
                goodUnit.set("???");
                unitPrice.set(String.format("%1$.2f???/???/???", entity.getPrice()));
                break;
            case 2:  //?????????
                goodType.set("?????????");
                goodUnit.set("???");
                unitPrice.set(String.format("%1$.2f???/???/???", entity.getPrice()));
                break;
            case 3:  //??????
                goodType.set("??????");
                goodUnit.set("??????");
                unitPrice.set(String.format("%1$.2f???/??????/???", entity.getPrice()));
                break;
            default:
                break;
        }
    }

    public List<String> getBannerImage() {
        return entity.get().getGoodsImgList();
    }


    public void cancelGood(final Good good) {
        Map<String, Object> map = new HashMap<>();
        ModelGoodNoId modelGoodNoId = Converter.toModelGoodNoId(good);
        modelGoodNoId.setAvailable(0);
        map.put("saleObject", modelGoodNoId);
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);

        RetrofitClient.getInstance().create(GoodApiService.class)
                .updateGood(good.getId(), body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //?????????View????????????
                .compose(RxUtils.schedulersTransformer()) //????????????
                .compose(RxUtils.exceptionTransformer()) // ???????????????????????????, ???????????????????????????ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("????????????...");
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        dismissDialog();
                        //??????DemoActivity??????
                        if (response.getCode() == 1) {
                            try {
                                SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("orderStatus", -1);
                                //db.insert("Orders", null, orderValues);
                                db.update("Orders", values, "orderNumber=?", new String[]{order.get().getOrderNumber()});
                                db.close();
                                ToastUtils.showShort("??????????????????");
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShort(response.getMessage());
                        }

                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //???????????????
                        dismissDialog();
                        //????????????????????????
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //???????????????
                        dismissDialog();//????????????????????????

                    }
                });
    }

    public void agreeGood(final Good good) {

        Map<String, Object> map = new HashMap<>();
        ModelGoodNoId modelGoodNoId = Converter.toModelGoodNoId(good);
        modelGoodNoId.setAvailable(1);
        map.put("saleObject", modelGoodNoId);
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);
        RetrofitClient.getInstance().create(GoodApiService.class)
//                //.login(userName.get(), password.get(), userType)
                .updateGood(good.getId(), body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //?????????View????????????
                .compose(RxUtils.schedulersTransformer()) //????????????
                .compose(RxUtils.exceptionTransformer()) // ???????????????????????????, ???????????????????????????ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("????????????...");
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        dismissDialog();
                        //??????DemoActivity??????
                        if (response.getCode() == 1) {
                            SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("orderStatus", 3);
                            //db.insert("Orders", null, orderValues);
                            db.update("Orders", values, "orderNumber=?", new String[]{order.get().getOrderNumber()});
                            db.close();
                            ToastUtils.showShort("????????????" + String.valueOf(good.getId()));
                            finish();
                        } else {
                            ToastUtils.showShort(response.getMessage());
                        }

                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //???????????????
                        dismissDialog();
                        //????????????????????????
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //???????????????
                        dismissDialog();//????????????????????????

                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> phoneContact = new SingleLiveEvent<>();
    }


}
