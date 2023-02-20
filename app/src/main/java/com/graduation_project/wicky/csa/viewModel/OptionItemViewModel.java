package com.graduation_project.wicky.csa.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.graduation_project.wicky.csa.activity.SettingActivity;
import com.graduation_project.wicky.csa.bean.ListOption;
import com.graduation_project.wicky.csa.fragment.AdoptPagerGroupFragment;
import com.graduation_project.wicky.csa.fragment.ExaminePagerGroupFragment;
import com.graduation_project.wicky.csa.fragment.SharingPagerGroupFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class OptionItemViewModel extends ItemViewModel<UserViewModel> {
    public ObservableField<ListOption> entity = new ObservableField<>();
    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
            switch (entity.get().getTitle()) {
                case "产品审核":
                    viewModel.startContainerActivity(ExaminePagerGroupFragment.class.getCanonicalName());
                    break;
                case "我的认养":
                    viewModel.startContainerActivity(AdoptPagerGroupFragment.class.getCanonicalName());
                    break;
                case "我的共享":
                    viewModel.startContainerActivity(SharingPagerGroupFragment.class.getCanonicalName());
                    break;
                case "设置":
                    viewModel.startActivity(SettingActivity.class);
                    break;
                default:
                    break;
            }
        }
    });

    public OptionItemViewModel(@NonNull UserViewModel viewModel, ListOption entity) {
        super(viewModel);
        this.entity.set(entity);
    }

}
