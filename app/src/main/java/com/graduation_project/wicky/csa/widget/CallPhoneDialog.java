package com.graduation_project.wicky.csa.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.MBaseActivity;
import com.graduation_project.wicky.csa.databinding.DialogCallPhoneBinding;
import com.graduation_project.wicky.csa.handler.EventHandler;

import me.goldze.mvvmhabit.base.BaseActivity;


/**
 * Created by Administrator on 2016/11/30.
 */
public class CallPhoneDialog extends Dialog {
    DialogCallPhoneBinding dialogCallPhoneBinding;
    private Callback callback;
    private BaseActivity activity;

    /**
     * @param context 上下文
     * @param content 提示的文字
     */
    public CallPhoneDialog(Context context, String content, String phone) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        dialogCallPhoneBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_call_phone, null, false);
        setContentView(dialogCallPhoneBinding.getRoot());
        activity = (BaseActivity) context;
        dialogCallPhoneBinding.tishi.setText(content);
        dialogCallPhoneBinding.textshow.setText(phone);
        dialogCallPhoneBinding.setHandler(new CallPhoneDialog.CallPhoneDialogEvenHandler());
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void callback();

        void cancle();
    }

    public class CallPhoneDialogEvenHandler extends EventHandler {

        private MBaseActivity mContext;

        public CallPhoneDialogEvenHandler() {
        }

        public CallPhoneDialogEvenHandler(Context context) {
            super(context);
            mContext = (MBaseActivity) context;

        }

        @Override
        public void onClick(View view) {
            if (callback == null)
                return;
            switch (view.getId()) {
                case R.id.commit:
                    dismiss();
                    callback.callback();
                    break;
                case R.id.cancel:
                    dismiss();
                    callback.cancle();
                    break;
                default:
                    break;
            }
        }
    }
}
