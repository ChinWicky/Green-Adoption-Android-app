package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.ReleaseOrEditActivity;
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

public class MySharingViewModel extends BaseViewModel {

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象
    public final BindingRecyclerViewAdapter<MySharingItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public User buyer;

    private List<Order> orderList = new ArrayList<>();

    public int position;
    //给RecyclerView添加ObservableList
    public ObservableList<MySharingItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<MySharingItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_sharing_order);
    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //假的
            Observable.just("")
                    .delay(3, TimeUnit.SECONDS) //延迟3秒
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ToastUtils.showShort("下拉刷新");
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            observableList.clear();
                            requestData(position);
                            //刷新完成收回
                            uc.finishRefreshing.set(!uc.finishRefreshing.get());
                        }
                    });

        }
    });
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //模拟网络上拉加载更多
            Observable.just("")
                    .delay(3, TimeUnit.SECONDS) //延迟3秒
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ToastUtils.showShort("上拉加载");
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            //刷新完成收回
                            uc.finishLoadmore.set(!uc.finishLoadmore.get());
                            //模拟一部分假数据

                        }
                    });
        }
    });
    public BindingCommand addGood = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ReleaseOrEditActivity.class);
        }
    });

    public MySharingViewModel(@NonNull Application application) {
        super(application);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void requestData(final int position) {
        SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
        int userId = ((User) Hawk.get(HawkKey.USER)).getId();
        Cursor cursor;
        switch (position) {
            case 0:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where supplerId=? and orderStatus != 0 ", new String[]{String.valueOf(userId)});
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    order.setGoodList(requestProduct(cursor.getInt(cursor.getColumnIndex("goodId"))));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    requestUser(order.getAdopterId());
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            case 1:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where supplerId=? and orderStatus = 2", new String[]{String.valueOf(userId)});
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    order.setGoodList(requestProduct(cursor.getInt(cursor.getColumnIndex("goodId"))));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    requestUser(order.getAdopterId());
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            case 2:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where supplerId=? and orderStatus = 3", new String[]{String.valueOf(userId)});
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    order.setGoodList(requestProduct(cursor.getInt(cursor.getColumnIndex("goodId"))));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    requestUser(order.getAdopterId());
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            case 3:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where supplerId=?  and orderStatus = 1", new String[]{String.valueOf(userId)});
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    order.setGoodList(requestProduct(cursor.getInt(cursor.getColumnIndex("goodId"))));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    requestUser(order.getAdopterId());
                    order.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));
                    orderList.add(order);
                    //goodObservableList.add(goodItmeViewModel);
                }
                break;
            case 4:
                observableList.clear();
                cursor = db.rawQuery("select * from Orders where supplerId=?  and orderStatus = -1", new String[]{String.valueOf(userId)});
                while (cursor.moveToNext()) {
                    Order order = new Order();
                    order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    order.setSum(cursor.getDouble(cursor.getColumnIndex("sum")));
                    order.setGoodTotal(cursor.getDouble(cursor.getColumnIndex("goodTotal")));
                    order.setGoodList(requestProduct(cursor.getInt(cursor.getColumnIndex("goodId"))));
                    order.setAmount(cursor.getInt(cursor.getColumnIndex("amount")));
                    order.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    order.setOrderStatus(cursor.getInt(cursor.getColumnIndex("orderStatus")));
                    order.setOrderNumber(cursor.getString(cursor.getColumnIndex("orderNumber")));
                    order.setYear(cursor.getInt(cursor.getColumnIndex("year")));
                    order.setSupplerId(cursor.getInt(cursor.getColumnIndex("supplerId")));
                    order.setAdopterId(cursor.getInt(cursor.getColumnIndex("adopterId")));
                    requestUser(order.getAdopterId());
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
                String url = getUriFromDrawableRes(this.getApplication(), R.mipmap.ic_launcher);
                photoList.add(url);
                good.setGoodsImgList(photoList);
            }
            goodList.add(good);
        }
        db.close();
        return goodList;
    }

    public String getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = "http://203.195.159.23:18088/api/picture/2f82d701-7061-4486-92a1-2b71214a4499.jpg";
//        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + resources.getResourcePackageName(id) + "/"
//                + resources.getResourceTypeName(id) + "/"
//                + resources.getResourceEntryName(id);
        Log.v("path---", path);
        return path;
    }

    public void requestUser(int userId) {
        RetrofitClient.getInstance().create(UserApiService.class)
                .getUser(userId)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            for (ModelUser modelUser : response.getParams().getContent()) {
                                buyer = Converter.toUser(modelUser);
                            }
                            Order order = orderList.get(0);
                            Good good = new Good();
                            if (order.getGoodList() != null && order.getGoodList().size() > 0) {
                                good = orderList.get(0).getGoodList().get(0);
                            }
                            MySharingItemViewModel itemViewModel = new MySharingItemViewModel(MySharingViewModel.this, order, good, buyer);
                            //MyAdoptItemViewModel goodItmeViewModel = new MyAdoptItemViewModel(MyAdoptViewModel.this, order.getGoodList().get(0));
                            orderList.remove(0);
                            observableList.add(itemViewModel);
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
    public void requestData() {
        for (Order entity : getData()) {
            MySharingItemViewModel itemViewModel = new MySharingItemViewModel(MySharingViewModel.this, entity, entity.getGoodList().get(0), buyer);
            //双向绑定动态添加Item
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
            order.setNote("你好");
            order.setAmount(2);
            order.setYear(1);
            order.setCreateDate(new Date().getTime());
            dataList.add(order);
        }
        return dataList;
    }

    /**
     * 获取条目下标
     *
     * @param marketItemViewModel
     * @return
     */
    public int getPosition(MarketItemViewModel marketItemViewModel) {
        return observableList.indexOf(marketItemViewModel);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UIChangeObservable {
        //下拉刷新完成
        public ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        //上拉加载完成
        public ObservableBoolean finishLoadmore = new ObservableBoolean(false);
    }


}
