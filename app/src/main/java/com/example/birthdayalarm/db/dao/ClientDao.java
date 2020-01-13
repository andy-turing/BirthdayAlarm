package com.example.birthdayalarm.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthdayalarm.db.entity.Client;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insert(Client client);

    @Query("SELECT *, CAST(strftime('%m', datetime(dateBirthday/1000, 'unixepoch')) AS int) AS month, CAST(strftime('%d', datetime(dateBirthday/1000, 'unixepoch')) AS int) AS day   FROM client WHERE day = :diaFnac and month = :mesFnac")
    List<Client> getBirthday(String diaFnac, String mesFnac);

    @Query("SELECT * FROM client")
    List<Client> getAll();
}
