package com.SpecialProjects.coupons;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by David Smith on 7/19/2014.
 */
public class MyReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Tag", "MyReceiver entered");
        Intent service1 = new Intent(context, MyAlarmService.class);
        startWakefulService(context,service1);
    }

    public void setAlarm(Context context) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,MyReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //set the alarms trigger to hour and minute
        calendar.set(Calendar.HOUR_OF_DAY,14);
        calendar.set(Calendar.MINUTE,11);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
    }
}
