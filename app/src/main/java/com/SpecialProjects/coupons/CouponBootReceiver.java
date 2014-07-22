package com.SpecialProjects.coupons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CouponBootReceiver extends BroadcastReceiver {
    MyReceiver alarm = new MyReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);

        }
    }
}
