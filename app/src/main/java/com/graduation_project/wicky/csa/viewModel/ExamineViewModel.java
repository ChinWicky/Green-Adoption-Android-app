package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.Order;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.UserApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by goldze on 2017/7/17.
 */

public class ExamineViewModel extends BaseViewModel {

    public final BindingRecyclerViewAdapter<ExamineItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public int position;

    private List<Order> orderList = new ArrayList<>();

    public User suppler;

    private String path = "http://203.195.159.23:18088/api/picture/2f82d701-7061-4486-92a1-2b71214a4499.jpg";

    //??????????????????????????????????????????
    public UIChangeObservable uc = new UIChangeObservable();
    //???RecyclerView??????ObservableList
    public ObservableList<ExamineItemViewModel> observableList = new ObservableArrayList<>();
    //???RecyclerView??????ItemBinding
    public ItemBinding<ExamineItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_examine);
    //????????????


    public ExamineViewModel(@NonNull Application application) {
        super(application);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //??????????????????
    public void requestData() {
        for (Order entity : getData()) {
            ExamineItemViewModel itemViewModel = new ExamineItemViewModel(ExamineViewModel.this, entity, entity.getGoodList().get(0), suppler);
            //????????????????????????Item
            observableList.add(itemViewModel);
        }
    }


    protected List<Order> getData() {
        //dataList.clear();
        List<Order> dataList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            order.setOrderNumber("123456789");
            order.setSum(200);
            order.setGoodTotal(100);
            order.setNote("??????");
            order.setAmount(2);
            order.setYear(1);
            order.setCreateDate(new Date().getTime());
            dataList.add(order);
        }
        return dataList;
    }

    /**
     * ??????????????????
     *
     * @param marketItemViewModel
     * @return
     */
    public int getPosition(MarketItemViewModel marketItemViewModel) {
        return observableList.indexOf(marketItemViewModel);
    }


    /**
     * ????????????????????????ViewModel????????????Retrofit+RxJava??????Repository???????????????Model???
     */
    public void requestData(final int position) {
        SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
        int userId = ((User) Hawk.get(HawkKey.USER)).getId();
        Cursor cursor;
        switch (position) {
            case 0:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where orderStatus='2'", null);
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    int goodId = cursor.getInt(cursor.getColumnIndex("goodId"));
                    order.setGoodList(requestProduct(goodId));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    requestUser(order.getSupplerId());
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            case 1:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where orderStatus='3'", null);
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    int goodId = cursor.getInt(cursor.getColumnIndex("goodId"));
                    order.setGoodList(requestProduct(goodId));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    requestUser(order.getSupplerId());
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            default:
                break;
        }
    }

    public List<Good> requestProduct(int productId) {
        SQLiteDatabase db = new DatabaseHelper(this.getApplication(), "greenAdopt", null, 1).getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Goods where id=?", new String[]{String.valueOf(productId)});
        List<Good> goodList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Good good = new Good();
            good.setId(cursor.getInt(cursor.getColumnIndex("id")));
            good.setName(cursor.getString(cursor.getColumnIndex("name")));
            good.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            good.setSupplierId(cursor.getInt(cursor.getColumnIndex("supplerId")));
            good.setInventory(cursor.getInt(cursor.getColumnIndex("inventory")));
            good.setPlaceId(cursor.getString(cursor.getColumnIndex("placeId")));
            good.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            good.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
            List<String> photoList = new ArrayList<>();

            Cursor cursorPhoto = db.rawQuery("select * from Photo where type ='2' and goodId=?", new String[]{String.valueOf(good.getId())});
            while (cursorPhoto.moveToNext() && cursor.getCount() > 0) {
                photoList.add(cursorPhoto.getString(cursorPhoto.getColumnIndex("photoUrl")));
            }


            if (photoList.size() > 0) {
                good.setGoodsImgList(photoList);
            } else {
                //String url = getUriFromDrawableRes(this.getApplication(), R.mipmap.ic_launcher);
                photoList.add(path);
                good.setGoodsImgList(photoList);
            }
            goodList.add(good);
        }
        db.close();
        return goodList;
    }
//
//    public String getUriFromDrawableRes(Context context, int id) {
//        Resources resources = context.getResources();
//        String path = "http://203.195.159.23:18088/api/picture/2f82d701-7061-4486-92a1-2b71214a4499.jpg";
////        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
////                + resources.getResourcePackageName(id) + "/"
////                + resources.getResourceTypeName(id) + "/"
////                + resources.getResourceEntryName(id);
//        Log.v("path---", path);
//        return path;
//    }

    public void requestUser(int userId) {
        RetrofitClient.getInstance().create(UserApiService.class)
                .getUser(userId)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //?????????View????????????
                .compose(RxUtils.schedulersTransformer()) //????????????
                .compose(RxUtils.exceptionTransformer()) // ???????????????????????????, ???????????????????????????ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
                        //????????????
                        if (response.getCode() == 1) {
                            for (ModelUser modelUser : response.getParams().getContent()) {
                                suppler = Converter.toUser(modelUser);
                            }
                            Order order = orderList.get(0);
                            Good good = new Good();
                            if (order.getGoodList() != null && order.getGoodList().size() > 0) {
                                good = orderList.get(0).getGoodList().get(0);
                            }
                            ExamineItemViewModel itemViewModel = new ExamineItemViewModel(ExamineViewModel.this, order, good, suppler);
                            //MyAdoptItemViewModel goodItmeViewModel = new MyAdoptItemViewModel(MyAdoptViewModel.this, order.getGoodList().get(0));
                            orderList.remove(0);
                            observableList.add(itemViewModel);
                        } else {
                            //code????????????????????????Observable?????????View????????????
                            ToastUtils.showShort("????????????");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UIChangeObservable {
        //??????????????????
        public ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        //??????????????????
        public ObservableBoolean finishLoadmore = new ObservableBoolean(false);
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //??????
            Observable.just("")
                    .delay(2, TimeUnit.SECONDS) //??????3???
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//????????????????????????
                    .compose(RxUtils.schedulersTransformer()) //????????????
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ToastUtils.showShort("????????????");
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            observableList.clear();
                            //??????????????????
                            requestData(position);
                            uc.finishRefreshing.set(!uc.finishRefreshing.get());

                        }
                    });
        }
    });
    //????????????
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //??????????????????????????????
            Observable.just("")
                    .delay(2, TimeUnit.SECONDS) //??????3???
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//????????????????????????
                    .compose(RxUtils.schedulersTransformer()) //????????????
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ToastUtils.showShort("????????????");
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            //??????????????????
                            uc.finishLoadmore.set(!uc.finishLoadmore.get());

                        }
                    });
        }
    });
}
