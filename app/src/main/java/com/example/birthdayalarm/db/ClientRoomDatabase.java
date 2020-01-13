package com.example.birthdayalarm.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.birthdayalarm.app.MyApp;
import com.example.birthdayalarm.db.dao.ClientDao;
import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.db.entity.Converters;

@Database(entities = {Client.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ClientRoomDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();
}
