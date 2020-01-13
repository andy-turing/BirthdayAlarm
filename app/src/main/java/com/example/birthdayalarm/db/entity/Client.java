package com.example.birthdayalarm.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "client")
public class Client {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String lastName;
    public String address;
    public String mail;
    public Date dateBirthday;
    public String diaFnac;
    public String mesFnac;
    public String anioFnac;


    public Client(String name, String lastName, String address, String mail, Date dateBirthday, String diaFnac, String mesFnac, String anioFnac) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.mail = mail;
        this.dateBirthday = dateBirthday;
        this.diaFnac = diaFnac;
        this.mesFnac = mesFnac;
        this.anioFnac = anioFnac;
    }

    public Date getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDiaFnac() {
        return diaFnac;
    }

    public void setDiaFnac(String diaFnac) {
        this.diaFnac = diaFnac;
    }

    public String getMesFnac() {
        return mesFnac;
    }

    public void setMesFnac(String mesFnac) {
        this.mesFnac = mesFnac;
    }

    public String getAnioFnac() {
        return anioFnac;
    }

    public void setAnioFnac(String anioFnac) {
        this.anioFnac = anioFnac;
    }
}
