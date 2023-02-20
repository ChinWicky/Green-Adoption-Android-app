package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.graduation_project.wicky.csa.adapter.PublicPicAdapter;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.entity.ModelGoodNoId;
import com.graduation_project.wicky.csa.model.service.GoodApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.IOException;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by goldze on 2017/7/17.
 */

public class ReleaseOrEditViewModel extends BaseViewModel {

    public PublicPicAdapter detailspicAdapter;
    public PublicPicAdapter masterpicAdapter;
    public List<String> detailsimagePath = new ArrayList<>();
    public List<String> masterimagePath = new ArrayList<>();

    public ObservableField<Good> entity = new ObservableField<>();

    public ObservableField<String> price = new ObservableField<>();

    public ObservableField<String> inventory = new ObservableField<>();

    public ObservableField<String> category = new ObservableField<>();

    public UIChangeObservable uc = new UIChangeObservable();

    public List<String> photoUrl =new ArrayList<>();

    long id;


    public BindingCommand<Boolean> chooseType = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pChooseType.setValue(uc.pChooseType.getValue() == null || !uc.pChooseType.getValue());
        }
    });
    public BindingCommand<Boolean> choosePlace = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pChoosePlace.setValue(uc.pChoosePlace.getValue() == null || !uc.pChoosePlace.getValue());
        }
    });

    public BindingCommand addGoodInfo = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (entity.get().getName() == null) {
                ToastUtils.showShort("空产品名");
                return;
            }
            if (category.get() == null) {
                ToastUtils.showShort("空分类");
                return;
            }
            if (price.get() == null) {
                ToastUtils.showShort("空价格");
                return;
            }
            if (inventory.get() == null) {
                ToastUtils.showShort("空库存");
                return;
            }
            //ToastUtils.showShort(entity.get().getName());
            switch (category.get()) {
                case "种植业":
                    entity.get().setCategory(1);
                    break;
                case "畜牧业":
                    entity.get().setCategory(2);
                    break;
                case "渔业":
                    entity.get().setCategory(3);
                    break;
                default:
                    break;
            }
            entity.get().setPrice(Double.valueOf(price.get()));
            entity.get().setInventory(Integer.valueOf(inventory.get()));
            entity.get().setSupplierId(((User) Hawk.get(HawkKey.USER)).getId());
            // entity.get().setSupplierId(user.getId());

            ToastUtils.showShort(String.valueOf(entity.get().getPrice()));
            SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("price", entity.get().getPrice());
            values.put("name", entity.get().getName());
            values.put("category", entity.get().getCategory());
            values.put("supplerId", entity.get().getSupplierId());
            values.put("description", entity.get().getDescription());
            values.put("inventory", entity.get().getInventory());

            id = db.insert("Goods", null, values);
            entity.get().setId((int) id);
            entity.get().setGoodsImgList(photoUrl);

            db.close();
            addGood(entity.get());
        }
    });


    public ReleaseOrEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void setEntity(Good entity) {
        this.entity.set(entity);
        User user = Hawk.get(HawkKey.USER);
        if (entity.getPrice() != 0)
            price.set(String.valueOf(entity.getPrice()));
        if (entity.getInventory() != 0)
            inventory.set(String.valueOf(entity.getInventory()));
        if (entity.getCategory() != 0) {
            switch (entity.getCategory()) {
                case 1:
                    category.set("种植业");
                    break;
                case 2:
                    category.set("畜牧业");
                    break;
                case 3:
                    category.set("渔业");
                    break;
                default:
                    break;
            }
        }
    }


    public void uploadPhoto(LocalMedia media) {
        File file = new File(media.getPath());
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);

        RetrofitClient.getInstance().create(GoodApiService.class)
                .uploadPhoto(part)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在上传...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<String>>() {
                    @Override
                    public void accept(BaseResponse<String> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            String url = response.getParams();
                            Log.v("photoUrl", url);
                            photoUrl.add("http://203.195.159.23:18088/api" + url);
                            ToastUtils.showShort("上传成功");
                            detailsimagePath.addAll(photoUrl);
                            detailspicAdapter.notifyDataSetChanged();
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }

    public void addGood(final Good good) {

        Map<String, Object> map = new HashMap<>();
        ModelGoodNoId modelGoodNoId = Converter.toModelGoodNoId(good);

        map.put("saleObject", modelGoodNoId);
        String json = new Gson().toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);


        RetrofitClient.getInstance().create(GoodApiService.class)
//                //.login(userName.get(), password.get(), userType)
                .addGood(body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在提交...");
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        dismissDialog();
                        //进入DemoActivity页面
                        if (response.getCode() == 1) {
                            SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
                            ContentValues orderValues = new ContentValues();
                            orderValues.put("goodId",  good.getId());
                            orderValues.put("sum", entity.get().getPrice());
                            orderValues.put("orderStatus", 2);
                            SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date date = new Date();
                            String orderNumber = formate.format(date);
                            orderValues.put("orderNumber", orderNumber);
                            orderValues.put("createDate", new Date().getTime());
                            orderValues.put("supplerId", entity.get().getSupplierId());
                            db.insert("Orders", null, orderValues);
                            for(String url:photoUrl){
                                ContentValues photoValues = new ContentValues();
                                Log.v("url",url);
                                photoValues.put("photoUrl","http://203.195.159.23:18088/api"+url);
                                photoValues.put("type",2);
                                photoValues.put("goodId",good.getId());
                                db.insert("Photo",null,photoValues);
                            }
                            db.close();
                            ToastUtils.showShort("提交成功" + String.valueOf(good.getId()));
                            finish();
                        } else {
                            ToastUtils.showShort(response.getMessage());
                        }

                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();//请求刷新完成收回

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> pChooseType = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> pChoosePlace = new SingleLiveEvent<>();
    }


}
