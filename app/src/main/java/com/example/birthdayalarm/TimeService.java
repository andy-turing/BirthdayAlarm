package com.example.birthdayalarm;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.birthdayalarm.servicebackground.MyAlarmReceiver;


public class TimeService extends Service {
    // Time in milliseconds for service to run, currently set to 60 seconds
    public static final long NOTIFY_INTERVAL = 60000;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();

    public IBinder onBind(Intent intent) {
        return null;
    }

    // When the service starts, we want to stop the alarm and start it up again inorder to avoid two concurrent Alarms
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.removeCallbacks(rs);
        try {
            OFFalarm();
        } catch (Exception e) {

        }
        mHandler.post(rs);
        return super.onStartCommand(intent, flags, startId);
    }

    Runnable rs = new Runnable() {
        public void run() {
            new Thread(new Runnable() {
                public void run() {
                    ONalarm();
                }
            }).start();
        }
    };

    // Register the service to be called by the Alarm, in this case, AlarmService
    private PendingIntent getAlarm() {
        Intent myAlarm = new Intent(getApplicationContext(), AlarmReciever.class);
        PendingIntent recurringAlarm = PendingIntent.getBroadcast(getApplicationContext(), 0, myAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        return recurringAlarm;
    }

    // Turn on the Alarm, and start counting
    void ONalarm() {
        PendingIntent recurringAlarm = getAlarm();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d("hora de las 7", calendar.toString());


        AlarmManager alarms = getAlarmMAnager();
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,   AlarmManager.INTERVAL_DAY , recurringAlarm);
    }

    // Get and return the Alarm Manager System service of the app's instance
    private AlarmManager getAlarmMAnager() {
        AlarmManager alarms = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        return alarms;
    }

    // cancel any running alarms to avoid duplicates
    void OFFalarm() {
        PendingIntent recurringAlarm = getAlarm();
        AlarmManager alarms = getAlarmMAnager();
        alarms.cancel(recurringAlarm);
    }

}
