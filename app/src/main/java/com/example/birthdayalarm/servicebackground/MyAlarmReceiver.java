package com.example.birthdayalarm.servicebackground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.birthdayalarm.ClientRepository;
import com.example.birthdayalarm.R;
import com.example.birthdayalarm.app.MyApp;
import com.example.birthdayalarm.constants.Constants;
import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.ui.MainActivity;
import com.example.birthdayalarm.viewmodel.NuevoClientViewModel;

import java.util.Calendar;
import java.util.List;


public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    private NotificationManager notificationManager;
    private String alarma,descripcion,titulo;
    ClientRepository clientRepository;
    private int notificationCount = 0;
    int SUMMARY_ID = 0;
    String GROUP_KEY_BIRTHDAY = "com.android.example.BIRTHDAY_REMEMBER";


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyTestService.class);
        context.startService(i);
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
        Toast.makeText(context, hora_sistema, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, Constants.HOUR_REMEMBER_BIRTHDAY, Toast.LENGTH_SHORT).show();

        clientRepository = new ClientRepository();

        if (hora_sistema.equals(Constants.HOUR_REMEMBER_BIRTHDAY)){
            List<Client> clients = clientRepository.getBirthday(String.valueOf(dia),String.valueOf(mes));

            if (clients != null){
                int id=0;
                for (Client client : clients) {
                    titulo = "Felicita a: " +client.getName() + " " + client.getLastName();
                    //notification(context,"Notificación de Cumpleaños", titulo, id);

                    triggerNotification(context, titulo ,id++);
                }
            }
        }
    }

    /*void notification(Context contexto,String title, String message,int id) {
        NotificationManager mNotificationManager = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("BIRTHDAY_CHANNEL_ID",
                    "BIRTHDAY_CHANNEL",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("CHANNEL BIRTHDAY REMEMBER");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(contexto, "BIRTHDAY_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_cake_black_24dp) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent notificationIntent = new Intent(contexto, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(id, mBuilder.build());
    }
*/


    private void triggerNotification(Context contexto, String t,int id) {
        Toast.makeText(contexto, "aquiiiiiiiiiiiii", Toast.LENGTH_SHORT).show();
        notificationManager = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("not","not");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("BIRTHDAY_CHANNEL_ID",
                    "BIRTHDAY_CHANNEL",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("CHANNEL BIRTHDAY REMEMBER");
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(contexto, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(contexto,"BIRTHDAY_CHANNEL_ID");
        builder.setContentIntent(contentIntent)

                .setTicker("" )
                .setContentTitle("Notificación de cumpleaños ")
                .setContentText(t)
                //.setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(contexto.getResources(), R.drawable.ic_cake_black_24dp))
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

}
