package com.example.birthdayalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReciever extends BroadcastReceiver {
    public void onReceive(Context context, Intent
            intent) {

        // When the phone boots, start the TimeService so that it starts counting behind the scene
        context.startService(new Intent(context,TimeService.class));
    }
}