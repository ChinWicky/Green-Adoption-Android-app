package com.graduation_project.wicky.csa.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.graduation_project.wicky.csa.R;
import com.graduation_project.wicky.csa.activity.MBaseActivity;
import com.graduation_project.wicky.csa.databinding.DialogUpdateNumberBinding;
import com.graduation_project.wicky.csa.handler.EventHandler;
import com.graduation_project.wicky.csa.utils.CheckUtil;


public class UpdateNumberDialog extends Dialog {


    private DialogUpdateNumberBinding dialogUpdateNumberBinding;
    private onCallBack mOnCallBack;

    public UpdateNumberDialog(@NonNull Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
        dialogUpdateNumberBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_update_number,null,false);
        //ButterKnife.bind(this);
        setContentView(dialogUpdateNumberBinding.getRoot());
        dialogUpdateNumberBinding.setHandler(new UpdateNumberDialogEvenHandler());
    }

    public void setNumber(int number) {
        dialogUpdateNumberBinding.etChooseNum.setText(String.valueOf(number));
        dialogUpdateNumberBinding.etChooseNum.setSelection(dialogUpdateNumberBinding.etChooseNum.getText().length());
    }

    public void setOnCallBack(onCallBack onCallBack) {
        mOnCallBack = onCallBack;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.left_int_out_dialog_style);
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public interface onCallBack {
        void onConfirm(int number);
    }

    public class UpdateNumberDialogEvenHandler extends EventHandler{

        private MBaseActivity mContext;

        public UpdateNumberDialogEvenHandler(){}

        public UpdateNumberDialogEvenHandler(Context context){
            super(context);
            mContext = (MBaseActivity) context ;

        }
        @Override
        public void onClick(View view) {
            int num = 1;
            String str = dialogUpdateNumberBinding.etChooseNum.getText().toString();
            if (!CheckUtil.checkEqual("", str)) {
                num = Integer.valueOf(str);
            }
            num = num < 1 ? 1 : num;
            switch (view.getId()) {
                case R.id.iv_reduce:
                    if (num > 1) {
                        dialogUpdateNumberBinding.etChooseNum.setText(String.valueOf(--num));
                        dialogUpdateNumberBinding.etChooseNum.setSelection(dialogUpdateNumberBinding.etChooseNum.getText().length());
                    }
                    break;
                case R.id.iv_add:
                    dialogUpdateNumberBinding.etChooseNum.setText(String.valueOf(++num));
                    dialogUpdateNumberBinding.etChooseNum.setSelection(dialogUpdateNumberBinding.etChooseNum.getText().length());
                    break;
                case R.id.cancel:
                    dismiss();
                    break;
                case R.id.commit:
                    if (mOnCallBack != null) {
                        mOnCallBack.onConfirm(num);
                    }
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
