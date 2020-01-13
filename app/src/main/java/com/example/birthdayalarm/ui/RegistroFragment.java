package com.example.birthdayalarm.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birthdayalarm.ClientRepository;
import com.example.birthdayalarm.R;
import com.example.birthdayalarm.db.entity.Client;
import com.example.birthdayalarm.viewmodel.NuevoClientViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RegistroFragment extends Fragment {
    private EditText name;
    private EditText lastName;
    private EditText address;
    private EditText mail;
    private EditText date;
    private Button guardar;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private int dia;
    private int mes;
    private int año;
    final int HOME = 0;
    private ClientRepository clientRepository;


    public RegistroFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        bind(view);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = name.getText().toString();
                apellido = lastName.getText().toString();
                direccion = address.getText().toString();
                email = mail.getText().toString();

                if (!nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty() && !email.isEmpty()){
                    NuevoClientViewModel clientViewModel = ViewModelProviders.of(getActivity()).get(NuevoClientViewModel.class);
                    String dateString = dia+"/"+mes+"/"+año;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(convertedDate);
                    clientViewModel.insertClient(new Client(nombre,apellido,direccion,email,convertedDate,String.valueOf(dia),String.valueOf(mes),String.valueOf(año)));
                    ((MainActivity) getActivity()).agregarFragmentos(HOME);
                }else{
                    Toast.makeText(getActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void bind(View view){
        name = view.findViewById(R.id.name);
        lastName = view.findViewById(R.id.lastName);
        address = view.findViewById(R.id.address);
        mail = view.findViewById(R.id.mail);
        date = view.findViewById(R.id.fecha);
        guardar = view.findViewById(R.id.guardarRegistro);

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                dia = day;
                mes = month +1;
                año = year;
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                date.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
