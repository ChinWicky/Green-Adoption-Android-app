package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.graduation_project.wicky.csa.activity.RegOrForgActivity;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.UserApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.RequestBody;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {

    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });
    public BindingCommand registerOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RegOrForgActivity.class);
        }
    });
    private User user;
    private String userType;
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Login();
        }
    });

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    private void Login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        if (userName.get().equals("123")) {
            userType = "ADMIN";
        } else {
            userType = "PRODUCER";
        }
        if(userType.equals("ADMIN")){
            Map<String, Object> map = new HashMap<>();
            map.put("username", userName.get());
            map.put("password", password.get());
            map.put("userType", userType);
            String json = new Gson().toJson(map);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);
            RetrofitClient.getInstance().create(UserApiService.class)
                    .login(body)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("正在请求...");
                        }
                    })
                    .subscribe(new Consumer<BaseResponse<ModelUser>>() {
                        @Override
                        public void accept(BaseResponse<ModelUser> response) throws Exception {
                            dismissDialog();
                            //进入DemoActivity页面
                            if (response.getCode() == 1) {
                                if (response.getParams() != null) {
                                    ModelUser modelUser = response.getParams();
                                    user = Converter.toUser(modelUser);
                                    user.setAdmin(true);
                                    ToastUtils.showShort(user.getUserName());
                                }
                                Hawk.put(HawkKey.USER, user);
                                Hawk.put(HawkKey.IS_LOGIN, true);
                                finish();
                            } else {
                                ToastUtils.showShort(response.getMessage());
                            }

                            // Bundle mBundle = new Bundle();
                            //mBundle.putParcelable("user",user);
                            //startActivity(MainActivity.class);
                            //关闭页面
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
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("username", userName.get());
            map.put("password", password.get());
            map.put("userType", userType);
            String json = new Gson().toJson(map);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);
            RetrofitClient.getInstance().create(UserApiService.class)
                .login(body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<ModelUser>>() {
                    @Override
                    public void accept(BaseResponse<ModelUser> response) throws Exception {
                        dismissDialog();
                        //进入DemoActivity页面
                        if (response.getCode() == 1) {
                            if (response.getParams() != null) {
                                ModelUser modelUser = response.getParams();
                                user = Converter.toUser(modelUser);
                                ToastUtils.showShort(user.getUserName());
                            }
                            Hawk.put(HawkKey.USER, user);
                            Hawk.put(HawkKey.IS_LOGIN, true);
                            finish();
                        } else {
                            ToastUtils.showShort(response.getMessage());
                        }

                        // Bundle mBundle = new Bundle();
                        //mBundle.putParcelable("user",user);
                        //startActivity(MainActivity.class);
                        //关闭页面
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }
}
