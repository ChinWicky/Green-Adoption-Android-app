package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.graduation_project.wicky.csa.activity.RegOrForgActivity;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.databinding.ActivityAdoptBinding;
import com.graduation_project.wicky.csa.databinding.ActivityRegorforgetBinding;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.UserApiService;
import com.graduation_project.wicky.csa.utils.CheckUtil;
import com.graduation_project.wicky.csa.utils.StringUtil;

import java.util.HashMap;
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

/**
 * Created by goldze on 2017/7/17.
 */

public class RegOrForgViewModel extends BaseViewModel {

    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> phone = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> againPassword = new ObservableField<>("");
    public ObservableField<String> code = new ObservableField<>("");
    public ObservableBoolean checked = new ObservableBoolean();

    public ActivityRegorforgetBinding binding;
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public BindingCommand checkedOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            checked.set(uc.pCheck.getValue() == null || !uc.pCheck.getValue());
        }
    });
    //登录按钮的点击事件
    public BindingCommand confirm = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //login();
            register();
        }
    });
    public BindingCommand getCheckCode = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (CheckUtil.isNull(phone.get())) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
            if (!StringUtil.isMobile(phone.get())) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            uc.pGetCode.setValue(uc.pGetCode.getValue() == null || !uc.pGetCode.getValue());
        }
    });
    public void setBinding(ActivityRegorforgetBinding binding){
        this.binding = binding;
    }

    public RegOrForgViewModel(@NonNull Application application) {
        super(application);
    }

    private void register() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入用户名！");
            return;
        }
        if (CheckUtil.isNull(phone.get())) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!StringUtil.isMobile(phone.get())) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        if (TextUtils.isEmpty(againPassword.get())) {
            ToastUtils.showShort("请输入验证码！");
            return;
        }
        if (!(againPassword.get().equals(password.get()))) {
            ToastUtils.showShort("请输入相同的密码！");
            return;
        }
        if (TextUtils.isEmpty(code.get())) {
            ToastUtils.showShort("请输入验证码！");
            return;
        }
        if (!code.get().equals("0000")) {
            ToastUtils.showShort("验证码错误！");
            return;
        }
        if (!binding.cbAgree.isChecked()) {
            ToastUtils.showShort("请阅读并同意用户协议");
            return;
        }

        ModelUser user = new ModelUser();
        user.setUsername(userName.get());
        user.setPassword(password.get());
        user.setPhoneNumber(phone.get());
        //user.setCreateTime(new Date().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        String json = new Gson().toJson(map);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), json);
        RetrofitClient.getInstance().create(UserApiService.class)
//                //.login(userName.get(), password.get(), userType)
                .register(body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在注册...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<ModelUser>>() {
                    @Override
                    public void accept(BaseResponse<ModelUser> response) throws Exception {
                        dismissDialog();
                        //进入DemoActivity页面
                        if (response.getCode() == 1) {
//                            if (response.getParams() != null) {
//                                ModelUser modelUser = response.getParams();
//                                user = Converter.toUser(modelUser, new User());
//                                ToastUtils.showShort(user.getUserName());
//                            }
//                            Hawk.put(HawkKey.USER, user);
//                            Hawk.put(HawkKey.IS_LOGIN, true);
                            ToastUtils.showShort("注册成功！用户名:" + userName.get());
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
    }

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> pGetCode = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> pCheck = new SingleLiveEvent<>();
    }
}
