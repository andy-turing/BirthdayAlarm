package com.example.birthdayalarm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.birthdayalarm.app.MyApp;
import com.example.birthdayalarm.db.ClientRoomDatabase;
import com.example.birthdayalarm.db.dao.ClientDao;
import com.example.birthdayalarm.db.entity.Client;

import java.util.List;

public class ClientRepository {
    private ClientDao clientDao;

    public ClientRepository(){
        ClientRoomDatabase roomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                ClientRoomDatabase.class,
                "birthdayRemember"
        ).allowMainThreadQueries()
        .build();

        clientDao = roomDatabase.clientDao();
    }

    public List<Client> getBirthday(String diaFnac, String mesFnac){
        return  clientDao.getBirthday(diaFnac, mesFnac);
    }

    public List<Client> getAll(){
        return  clientDao.getAll();
    }


    public void insert(Client client){
        //Se realiza la insercion de manera asincrona
        new InsertAsyncTask(clientDao).execute(client);
    }

    private static class InsertAsyncTask extends AsyncTask<Client,Void,Void> {
        private ClientDao clientDaoAsyncTask;

        InsertAsyncTask(ClientDao dao){
            clientDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDaoAsyncTask.insert(clients[0]);
            return null;
        }
    }
}
