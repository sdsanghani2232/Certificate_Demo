package com.sdsanghani.certimaker.firestore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.firestore.updatedata.UpdateActivity;
import com.sdsanghani.certimaker.firestore.models.UserModel;
import com.sdsanghani.certimaker.firestore.models.UserViewModle;

import java.util.ArrayList;

public class UserlistAdapter extends RecyclerView.Adapter<UserlistAdapter.DataHolder> {
    Context context;
    ArrayList<UserModel> datalist;
    UserViewModle userViewModle;

    public UserlistAdapter(Context context, ArrayList<UserModel> datalist, UserViewModle userViewModle) {
        this.context = context;
        this.datalist = datalist;
        this.userViewModle = userViewModle;

    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(context).inflate(R.layout.list_formet,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(datalist.get(position).getName());
        holder.email.setText(datalist.get(position).getEmail());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UpdateActivity.class).putExtra("id",datalist.get(position).getId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userViewModle.UserDelete(datalist.get(position).getId(),context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {

        TextView name,email;
        Button update,delete;
        public DataHolder(@NonNull View itemView) {
            super(itemView);

            update = itemView.findViewById(R.id.update_fire_store);
            delete = itemView.findViewById(R.id.delete_fire_store);
            name = itemView.findViewById(R.id.re_name);
            email = itemView.findViewById(R.id.re_email);
        }
    }
}
