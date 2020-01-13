package com.example.birthdayalarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.ui.MainActivity;

import java.util.Calendar;
import java.util.List;


public class AlarmService extends Service {
    private Handler mHandler = new Handler();
    public static final int REQUEST_CODE = 12345;
    private NotificationManager notificationManager;
    private String alarma,descripcion,titulo;
    ClientRepository clientRepository;
    private int notificationCount = 0;
    int SUMMARY_ID = 0;
    String GROUP_KEY_BIRTHDAY = "com.android.example.BIRTHDAY_REMEMBER";


    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.removeCallbacks(r);
        mHandler.post(r);
        return super.onStartCommand(intent, flags, startId);
    }

    // Build your notification widget
    private void triggerNotification(String t, int id) {
//        Toast.makeText(this, "aquiiiiiiiiiiiii", Toast.LENGTH_SHORT).show();
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("not","not");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("BIRTHDAY_CHANNEL_ID",
                    "BIRTHDAY_CHANNEL",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("CHANNEL BIRTHDAY REMEMBER");
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"BIRTHDAY_CHANNEL_ID");
        builder.setContentIntent(contentIntent)

                .setTicker("" )
                .setContentTitle("Notificación de cumpleaños ")
                .setContentText(t)
                //.setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_cake_black_24dp))
                .setSmallIcon(R.drawable.icbirthday)
                .setAutoCancel(true) //Cuando se pulsa la notificacion  desaparece
                .setSound(defaultSound)
                .setGroup(GROUP_KEY_BIRTHDAY)
                // .setGroupSummary(true)
                .setVibrate(pattern);

        Notification notificacion = new NotificationCompat.BigTextStyle(builder)
                .bigText(t)
                .setBigContentTitle("Notificación de cumpleaños")
                .setSummaryText("Cumpleañeros del día")
                .build();
       /* notificacion.number = notificationCount++;

        if (notificacion.number > 1)
        {
            String text = GROUP_KEY_BIRTHDAY + " (" + notificacion.number + ")";

            Notification summaryNotification =
                    new NotificationCompat.Builder(contexto)
                            .setContentTitle("Deseales Feliz Cumpleaños")
                            //set content text to support devices running API level < 24
                            .setContentText("Deseales Feliz Cumpleaños")
                            .setSmallIcon(R.drawable.ic_cake_black_24dp)
                            //build summary info into InboxStyle template
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .setSummaryText(text))
                            //specify which group this notification belongs to
                            .setGroup(GROUP_KEY_BIRTHDAY)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                            .setShortcutId("summary_" + GROUP_KEY_BIRTHDAY)
                            .setAutoCancel(true)
                            .build();
            notificationManager.notify(SUMMARY_ID, summaryNotification);
        }*/
        notificationManager.notify(id, notificacion);

    }

    // A runnable to perform actions when the Alarm is fired
    private Runnable r = new Runnable() {
        public void run() {
            // Do stuff every time this service is called by the Alarm, every 60 seconds in this example
            new Thread(new Runnable() {
                public void run() {
                    Calendar calendario = Calendar.getInstance();
                    int hora, min,dia,mes,ano;
                    String hora_sistema;

                    dia = calendario.get(Calendar.DAY_OF_MONTH);
                    mes = calendario.get(Calendar.MONTH)+1;
                    ano = calendario.get(Calendar.YEAR);
                    hora = calendario.get(Calendar.HOUR_OF_DAY);
                    min = calendario.get(Calendar.MINUTE);
                    //fecha_sistema=mes+"-"+dia+"-"+ano+" ";
                    hora_sistema=hora+":"+min;

                    clientRepository = new ClientRepository();

                    // if (hora_sistema.equals(Constants.HOUR_REMEMBER_BIRTHDAY)){
                    List<Client> clients = clientRepository.getBirthday(String.valueOf(dia),String.valueOf(mes));

                    if (clients != null){
                        int id=0;
                        for (Client client : clients) {
                            titulo = "Felicita a: " +client.getName() + " " + client.getLastName();
                            //notification(context,"Notificación de Cumpleaños", titulo, id);

                            triggerNotification(titulo ,id++);
                        }
                    }
                    //}

                }
            }).start();
        }
    };
}