package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.service.GoodApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by goldze on 2017/7/17.
 */

public class MarketViewModel extends BaseViewModel {
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象
    public final BindingRecyclerViewAdapter<MarketItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    //给RecyclerView添加ObservableList
    public ObservableList<MarketItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<MarketItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_goods);
    public BindingCommand chooseType = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.filterArrow.setValue(uc.filterArrow.getValue() == null || !uc.filterArrow.getValue());
        }
    });

    public int pageNumber = 1;
    public int pageSize = 10;

    public BindingCommand chooseArea = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //模拟网络上拉加载更多
            Observable.just("")
                    .delay(2, TimeUnit.SECONDS) //延迟3秒
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
                            //adapter.notifyItemInserted(itemIndex);
                            pageNumber++;
                            requestNetWork(pageSize, pageNumber);

//                            for (int i = 0; i < 10; i++) {
//                                Good item = new Good();
//                                item.setId(-1);
//                                item.setName("模拟条目" + itemIndex++);
//                                MarketItemViewModel itemViewModel = new MarketItemViewModel(MarketViewModel.this, item);
//                                //双向绑定动态添加Item
//                                observableList.add(itemViewModel);
//                            }
                        }
                    });
        }
    });

    private Good good = new Good();
    private List<Good> allGood = new ArrayList<>();
    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pageNumber = 1;
            observableList.clear();
            requestNetWork(pageSize, pageNumber);
        }
    });

    public MarketViewModel(@NonNull Application application) {
        super(application);
    }


    protected List<Good> getData() {
        List<Good> dataList = new ArrayList();
        dataList.add(new Good("苹果", 5.0, 1));
        dataList.add(new Good("菠萝", 3.0, 1));
        dataList.add(new Good("猪", 30.0, 2));
        dataList.add(new Good("牛", 50.0, 2));
        dataList.add(new Good("鲈鱼", 15.0, 3));
        dataList.add(new Good("水稻", 8.0, 1));
        return dataList;
    }


    public void initMarket(){
        RetrofitClient.getInstance().create(GoodApiService.class)
                .goodGet(1000,1)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelGood>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelGood>> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            SQLiteDatabase db = new DatabaseHelper(getApplication(),"greenAdopt",null,1).getWritableDatabase();
                            db.execSQL("delete from Goods");
                            db.execSQL("delete from sqlite_sequence where name = 'Goods'");
                            //将实体赋给LiveData
                            for (ModelGood entity : response.getParams().getContent()) {
                                Good good = Converter.toGood(entity);
                                ContentValues values = new ContentValues();
                                values.put("id",good.getId());
                                values.put("name",good.getName());
                                values.put("price",good.getPrice());
                                values.put("category",good.getCategory());
                                values.put("supplerId",good.getSupplierId());
                                values.put("inventory",good.getInventory());
                                values.put("placeId",good.getPlaceId());
                                values.put("description",good.getDescription());
//                                for(int i=0; i< good.getGoodsImgList().size(); i++){
//                                    ContentValues valuesPhoto = new ContentValues();
//                                    valuesPhoto.put("photoUrl",good.getGoodsImgList().get(i));
//                                    valuesPhoto.put("type",2);
//                                    valuesPhoto.put("goodId",good.getId());
//                                    db.insert("Photo",null, valuesPhoto);
//                                }
                                db.insert("Goods",null, values);
                                //双向绑定动态添加Item
                            }
                            db.close();
                            // ToastUtils.showShort(response.getParams().getContent().size());
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //关闭对话框
                        //请求刷新完成收回
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 网络请求方法，在ViewModel中调用，Retrofit+RxJava充当Repository，即可视为Model层
     */
    public void requestNetWork(int pageSize, int pageNumber) {
        RetrofitClient.getInstance().create(GoodApiService.class)
                .goodGet(pageSize, pageNumber)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<Entity<ModelGood>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelGood>> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            allGood.clear();
                            SQLiteDatabase db = new DatabaseHelper(getApplication(), "greenAdopt", null, 1).getWritableDatabase();
                            //将实体赋给LiveData
                           // db.execSQL("delete from Photo where type =2");
                            for (ModelGood entity : response.getParams().getContent()) {
                                good = Converter.toGood(entity);
                                ContentValues values = new ContentValues();
                                values.put("id", good.getId());
                                values.put("name", good.getName());
                                values.put("price", good.getPrice());
                                values.put("category", good.getCategory());
                                values.put("supplerId", good.getSupplierId());
                                values.put("inventory", good.getInventory());
                                values.put("placeId", good.getPlaceId());
                                values.put("description", good.getDescription());
//                                for(int i=0; i<good.getGoodsImgList().size(); i++){
//                                    ContentValues valuesPhoto = new ContentValues();
//                                    valuesPhoto.put("photoUrl",good.getGoodsImgList().get(i));
//                                    valuesPhoto.put("type",2);
//                                    valuesPhoto.put("goodId",good.getId());
//                                    db.insert("Photo",null, valuesPhoto);
//                                }
                                db.insert("Goods", null, values);
                                if (entity.getAvailable() != 1) {
                                    continue;
                                }
                                MarketItemViewModel itemViewModel = new MarketItemViewModel(MarketViewModel.this, good);
                                //双向绑定动态添加Item
                                allGood.add(good);
                                observableList.add(itemViewModel);
                            }
                            db.close();
                            // ToastUtils.showShort(response.getParams().getContent().size());
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
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                    }
                });
    }

    public void requestProduct(int cateId) {
        //getAll();
        observableList.clear();

        if (cateId != 0) {
            for (int i = 0; i < allGood.size(); i++) {
                MarketItemViewModel itemViewModel = new MarketItemViewModel(MarketViewModel.this, allGood.get(i));
                if (allGood.get(i).getCategory() == cateId)
//                    //observableList.remove(i);
                    observableList.add(itemViewModel);
            }
        } else {
            for (int i = 0; i < allGood.size(); i++) {
                MarketItemViewModel itemViewModel = new MarketItemViewModel(MarketViewModel.this, allGood.get(i));
                observableList.add(itemViewModel);
            }
        }
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
        //筛选
        public SingleLiveEvent<Boolean> filterArrow = new SingleLiveEvent<>();
    }


}
