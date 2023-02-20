package com.graduation_project.wicky.csa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.graduation_project.wicky.csa.activity.MainActivity;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.db.DatabaseHelper;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelGood;
import com.graduation_project.wicky.csa.model.service.GoodApiService;
import com.orhanobut.hawk.Hawk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class Application  extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        //初始化全局异常崩溃
        initCrash();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
        Hawk.init(this).build();

        SQLiteDatabase db = new DatabaseHelper(getApplicationContext(),"greenAdopt",null,1).getWritableDatabase();
        db.execSQL("delete from Photo");
        db.execSQL("delete from sqlite_sequence where name = 'Photo'");
        db.close();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    SQLiteDatabase db = new DatabaseHelper(getApplicationContext(),"greenAdopt",null,1).getWritableDatabase();
                    //db.execSQL("delete from Photo where type='1'");
                    Document document = Jsoup.connect("http://www.moa.gov.cn/")
                            .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                            .get();
                    Elements elements = document.select("div.hwslider")
                            .select("ul")
                            .select("li");
                    ContentValues values = new ContentValues();
                    Log.v("urlsize-----------", String.valueOf(elements.size()) );
                    String htmlHead = "http://www.moa.gov.cn/";
                    for (int i = 0; i < elements.size(); i++) {
                        Element node = elements.get(i);
//                      String href = node.select("div.index_title3").select("a.two").attr("href");
                        String title = node.select("a").attr("title");
                        String imageUrl = htmlHead + node.select("a").select("img").attr("src");
//                      String intentUrl = htmlHead + href;
                        Log.v("imageurl-------------" , imageUrl);
                        values.put("photoUrl",imageUrl);
                        values.put("type", 1);
                        values.put("title",title);
                        Long is = db.insert("Photo",null, values);
                        Log.v("isinsert-------------" , String.valueOf(is));
                    }
                    db.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        RetrofitClient.getInstance().create(GoodApiService.class)
                .goodGet(1000,1)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelGood>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelGood>> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            SQLiteDatabase db = new DatabaseHelper(getApplicationContext(),"greenAdopt",null,1).getWritableDatabase();
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
                                for(int i=0; i< good.getGoodsImgList().size(); i++){
                                    ContentValues valuesPhoto = new ContentValues();
                                    valuesPhoto.put("photoUrl",good.getGoodsImgList().get(i));
                                    valuesPhoto.put("type",2);
                                    valuesPhoto.put("goodId",good.getId());
                                    db.insert("Photo",null, valuesPhoto);
                                }
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

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

}
