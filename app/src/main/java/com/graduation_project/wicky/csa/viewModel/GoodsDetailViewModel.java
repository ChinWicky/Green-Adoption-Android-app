package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.AdoptVMActivity;
import com.graduation_project.wicky.csa.bean.Good;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.model.Converter;
import com.graduation_project.wicky.csa.model.RetrofitClient;
import com.graduation_project.wicky.csa.model.entity.Entity;
import com.graduation_project.wicky.csa.model.entity.ModelUser;
import com.graduation_project.wicky.csa.model.service.UserApiService;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

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


public class GoodsDetailViewModel extends BaseViewModel {


    public ObservableField<Good> entity = new ObservableField();

    public ObservableField<User> suppler = new ObservableField<>();

    public ObservableInt num = new ObservableInt(1);

    public ObservableField<String> goodUnit = new ObservableField<>();

    public ObservableField<String> goodType = new ObservableField<>();

    public ObservableField<String> unitPrice = new ObservableField<>();

    public ArrayList<Integer> imagePath = new ArrayList<>();

    public GoodsDetailViewModel.UIChangeObservable uc = new GoodsDetailViewModel.UIChangeObservable();
    public BindingCommand addOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (num.get() < entity.get().getInventory()) {
                num.set(num.get() + 1);
            }else{
                ToastUtils.showShort("???????????????");
            }
        }
    });
    // public Drawable drawableImg;
    public BindingCommand minusOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(num.get() > 1){
                num.set(num.get() - 1);
            }else{
                ToastUtils.showShort("???????????????");
            }
        }
    });
    public BindingCommand numOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.numButton.setValue(uc.numButton.getValue() == null || !uc.numButton.getValue());
        }
    });
    public BindingCommand phoneContactOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.phoneContact.setValue(uc.phoneContact.getValue() == null || !uc.phoneContact.getValue());
        }
    });
    public BindingCommand adoptOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("entity", entity.get());
            mBundle.putInt("num", num.get());
            mBundle.putParcelable("suppler",suppler.get());
            if (num.get() > entity.get().getInventory()) {
                ToastUtils.showShort("????????????");
                return;
            }
            if (Hawk.get(HawkKey.IS_LOGIN)) {
                if(((User)Hawk.get(HawkKey.USER)).getId() == entity.get().getSupplierId()){
                    ToastUtils.showShort("?????????????????????????????????????????????");
                    return;
                }
                startActivity(AdoptVMActivity.class, mBundle);
            } else {
                ToastUtils.showShort("????????????");
                return;
            }
        }
    });

    public GoodsDetailViewModel(@NonNull Application application) {
        super(application);
        //drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
    }

    public void setEntity(Good entity) {
        this.entity.set(entity);
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

    public void initSuppler() {
        RetrofitClient.getInstance().create(UserApiService.class)
                .getUser(entity.get().getSupplierId())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //?????????View????????????
                .compose(RxUtils.schedulersTransformer()) //????????????
                .compose(RxUtils.exceptionTransformer()) // ???????????????????????????, ???????????????????????????ExceptionHandle
                .subscribe(new Consumer<BaseResponse<Entity<ModelUser>>>() {
                    @Override
                    public void accept(BaseResponse<Entity<ModelUser>> response) throws Exception {
                        //????????????
                        if (response.getCode() == 1) {
                            ModelUser modelUser = response.getParams().getContent().get(0);
                            suppler.set(Converter.toUser(modelUser));
                        } else {
                            //code????????????????????????Observable?????????View????????????
                            ToastUtils.showShort("????????????");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //???????????????
                        dismissDialog();
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //???????????????
                        dismissDialog();

                    }
                });
    }

    public List<String> getBannerImage() {

        return entity.get().getGoodsImgList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }

    public class UIChangeObservable {
        //?????????????????????
        public SingleLiveEvent<Boolean> numButton = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> phoneContact = new SingleLiveEvent<>();
    }



}
