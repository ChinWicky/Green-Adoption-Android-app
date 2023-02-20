package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.activity.MainActivity;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2017/7/17.
 */

public class SettingViewModel extends BaseViewModel {


//    //封装一个界面发生改变的观察者
//    public UIChangeObservable uc = new UIChangeObservable();
//
//    public class UIChangeObservable {
//        //密码开关观察者
//        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
//    }

    //    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand logout = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Hawk.delete(HawkKey.USER);
            Hawk.put(HawkKey.IS_LOGIN, false);
            startActivity(MainActivity.class);
            finish();
        }
    });

    public SettingViewModel(@NonNull Application application) {
        super(application);
    }

    //登录按钮的点击事件
//    public View.OnClickListener logout = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Hawk.delete(HawkKey.USER);
//           Hawk.put(HawkKey.IS_LOGIN,false);
//        }
//    };
//    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
//    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
//            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null ? true : !uc.pSwitchEvent.getValue());
//        }
//    });
//    //用户名输入框焦点改变的回调事件
//    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
//        @Override
//        public void call(Boolean hasFocus) {
//            if (hasFocus) {
//                clearBtnVisibility.set(View.VISIBLE);
//            } else {
//                clearBtnVisibility.set(View.INVISIBLE);
//            }
//        }
//    });
//    //登录按钮的点击事件
//    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            login();
//        }
//    });
//
//    /**
//     * 网络模拟一个登陆操作
//     **/
//    private void login() {
//        //ToastUtils.showShort(userName.get()+","+password.get());
//        if (TextUtils.isEmpty(userName.get())) {
//            ToastUtils.showShort("请输入账号！");
//            return;
//        }
//        if (TextUtils.isEmpty(password.get())) {
//            ToastUtils.showShort("请输入密码！");
//            return;
//        }
//
//        user =  new User(userName.get(),password.get());
//
//        //RaJava模拟一个延迟操作
//        Observable.just("")
//                .delay(3, TimeUnit.SECONDS) //延迟3秒
//                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
//                .compose(RxUtils.schedulersTransformer()) //线程调度
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        showDialog();
//                    }
//                })
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        dismissDialog();
//                        //进入DemoActivity页面
//                        Hawk.put(HawkKey.IS_LOGIN,true);
//                        Hawk.put(HawkKey.USER,user);
//                        Bundle mBundle = new Bundle();
//                        mBundle.putParcelable("user",user);
//                        startActivity(MainActivity.class,mBundle);
//                        //关闭页面
//                        finish();
//                    }
//                });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
