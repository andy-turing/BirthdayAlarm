package com.example.birthdayalarm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.birthdayalarm.ClientRepository;
import com.example.birthdayalarm.R;
import com.example.birthdayalarm.TimeService;
import com.example.birthdayalarm.app.MyApp;
import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.servicebackground.MyAlarmReceiver;
import com.example.birthdayalarm.servicebackground.MyTestService;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

    final int HOME = 0;
    final int REGISTRO_USUARIO = 1;
    private int  fragmentPos=HOME;
    ClientRepository clientRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servicio();
        agregarFragmentos(HOME);
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
       Toast.makeText(this, hora_sistema, Toast.LENGTH_SHORT).show();
       Toast.makeText(this, "7:00", Toast.LENGTH_SHORT).show();

        clientRepository = new ClientRepository();
        List<Client> clients = clientRepository.getBirthday(String.valueOf(dia),String.valueOf(mes));

        if (clients != null){
            int id=0;
            for (Client client : clients) {
                String titulo = "Felicita a: " +client.getName() + " " + client.getLastName();
               // Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show();
                Log.d("a0",client.getName() + " " + client.getDiaFnac());
            }
        }else{
            Toast.makeText(this, "vacio ", Toast.LENGTH_SHORT).show();
            Log.d("dfgh","dfghj");
        }


    }

    public void agregarFragmentos(int pos){
        Log.d("Posicion agregar", String.valueOf(pos));
        quitarFragmentos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (pos) {
            case REGISTRO_USUARIO:
                fragment = new RegistroFragment();
                fragmentPos = REGISTRO_USUARIO;
                break;

            case HOME:
                fragment= new MainFragment();
                fragmentPos = HOME;
                break;
        }
        transaction.setCustomAnimations(R.anim.zoom_forward_in, R.anim.zoom_forward_out, R.anim.zoom_back_in, R.anim.zoom_back_out)
                .add(R.id.container, fragment)
                .commit();
    }

    public void quitarFragmentos() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transactionx = fragmentManager.beginTransaction();
        List<Fragment> fragmentos = fragmentManager.getFragments();
        if (fragmentos.size() > 0) {
            transactionx.remove(fragmentos.get(0));
            transactionx.commit();
        }
    }

    public void servicio() {
       Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis();  // aranca la palicacion
        int intervalMillis = 1 * 5 * 1000; //3 segundos
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);/*

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pIntent); */




       //NUEVAS CLASES
       //startService(new Intent(this, TimeService.class));
        // registerReceiver();
    }

   /* private final String BROADCAST_ACTION = "com.example.VIEW_ACTION";

    // Receive the action from the notification item when its clicked
    // This receiver can be used to receive intents from other applications as well not just our Notification
    BroadcastReceiver notifyServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //intent, from the arguments will contain the parameters from the Notification used to trigger our IntentFilter
            startActivityIfNeeded(new Intent(getApplicationContext(), MainActivity.class), 1);
        }
    };

    // Register the Intent Receiver with tha Broadcast action it's to be called with
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(notifyServiceReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        try {
            this.unregisterReceiver(notifyServiceReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        unregisterReceiver();
        super.onPause();
    }

    protected void onResume() {
        registerReceiver();
        super.onResume();
    }*/


}
