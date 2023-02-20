package com.graduation_project.wicky.csa.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.BR;
import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.bean.ListOption;
import com.graduation_project.wicky.csa.bean.User;
import com.graduation_project.wicky.csa.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by goldze on 2017/7/17.
 */

public class UserViewModel extends BaseViewModel {

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象
    public final BindingRecyclerViewAdapter<OptionItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    public User user;
    //给RecyclerView添加ObservableList
    public ObservableList<OptionItemViewModel> observableList = new ObservableArrayList<>();

    public ItemBinding<OptionItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_model);

    public UserViewModel(@NonNull Application application) {
        super(application);
        if (Hawk.get(HawkKey.IS_LOGIN)) {
            user = Hawk.get(HawkKey.USER);
        }
    }

    public void getOption() {
        String[] titles;
        if (user != null && user.isAdmin()) {
            titles = new String[]{"产品审核", "我的认养", "我的共享", "地址管理", "我的收藏", "实名认证", "设置"};
        } else {
            titles = new String[]{"我的认养", "我的共享", "地址管理", "我的收藏", "实名认证", "设置"};
        }
        for (String title : titles) {
            ListOption entity = new ListOption(title);
            OptionItemViewModel itemViewModel = new OptionItemViewModel(UserViewModel.this, entity);
            //双向绑定动态添加Item
            observableList.add(itemViewModel);
        }
        //ToastUtils.showShort(observableList.size());
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


}
