package com.graduation_project.wicky.csa.handler;

import android.content.Context;
import android.view.View;


public class UserEventHandler extends EventHandler{

    private Context mContext;

    public UserEventHandler(Context context) {
        super(context);
        mContext = context;
    }

    public void onClick(View view) {
        switch (view.getId()) {
//           case R.id.tv_viewMore:
//               ManageOrderActivity.open( (MBaseActivity) mContext, 0, 1);
//               //mContext.startActivity(new Intent(mContext,ManageOrderActivity.class));
//               break;
//            case R.id.iv_avatar:
//                UserInfoActivity.open((MBaseActivity) mContext);
//                break;
//            case R.id.iv_right:
//                //MessageActivity.open(mContext);
//                break;
//            case R.id.fl_waitPay:
//                ManageOrderActivity.open((MBaseActivity)mContext, 1, 1);
//                break;
//            case R.id.fl_waitSend:
//                ManageOrderActivity.open((MBaseActivity)mContext, 2, 1);
//                break;
//            case R.id.fl_waitReceive:
//                ManageOrderActivity.open((MBaseActivity)mContext, 3, 1);
//                break;
//            case R.id.fl_waitComment:
//                ManageOrderActivity.open((MBaseActivity)mContext, 4, 1);
//                break;
//            case R.id.fl_afterSale:
//                //AfterSaleListActivity.open(mContext);
//                break;
            default:
                break;
       }
    }
}


