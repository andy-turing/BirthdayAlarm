package com.example.birthdayalarm.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.birthdayalarm.ClientRepository;
import com.example.birthdayalarm.R;
import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.ui.adapter.AdapterClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainFragment extends Fragment {
    private FloatingActionButton agregar;
    final int REGISTRO_USUARIO = 1;
    private RecyclerView listado;
    private LinearLayoutManager layoutManager;
    private AdapterClient adapterClient;
    private ClientRepository clientRepository;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listado = view.findViewById(R.id.recyclerView);
        agregar = view.findViewById(R.id.floatingActionButton);
        listado.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        listado.setLayoutManager(layoutManager);
        clientRepository = new ClientRepository();
        List<Client> clients = clientRepository.getAll();


        adapterClient= new AdapterClient(clients);
        listado.setAdapter(adapterClient);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).agregarFragmentos(REGISTRO_USUARIO);
            }
        });
        return view;
    }


}
