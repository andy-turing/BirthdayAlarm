package com.example.birthdayalarm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdayalarm.ClientRepository;
import com.example.birthdayalarm.db.entity.Client;

import java.util.List;

public class NuevoClientViewModel extends ViewModel {
    private List<Client> allClients;
    private ClientRepository clientRepository;


    public NuevoClientViewModel() {
        clientRepository = new ClientRepository();
        allClients = clientRepository.getAll();

    }

    public  List<Client> getAllClient(){
        return  allClients;
    }

    public void insertClient(Client client){
        clientRepository.insert(client);
    }

    public List<Client> getBirthday(String day, String month){
        return clientRepository.getBirthday(day,month);

    }
}
