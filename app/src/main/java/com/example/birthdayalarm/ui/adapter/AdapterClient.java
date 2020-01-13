package com.example.birthdayalarm.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdayalarm.R;
import com.example.birthdayalarm.db.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class AdapterClient extends RecyclerView.Adapter<AdapterClient.ViewHolder> {
    private View v;
    public static List<Client> clients;

    public AdapterClient(List<Client> clients) {
        this.clients = clients;
    }

    @NonNull
    @Override
    public AdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_listado_client, parent, false);
       // v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClient.ViewHolder holder, int position) {
        try{
            Client client = clients.get(position);
            holder.nombre.setText(client.getName() + " " + client.getLastName());
            holder.email.setText(client.getDateBirthday().toString());
            holder.imageView.setImageDrawable(v.getResources().getDrawable(R.mipmap.ic_user));
        }catch (IndexOutOfBoundsException e){

        }
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView email;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombre);
            email = itemView.findViewById(R.id.txtEmail);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }
}
