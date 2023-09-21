package com.sdsanghani.certimaker.firestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsanghani.certimaker.R;

import java.util.ArrayList;

public class ReadData extends RecyclerView.Adapter<ReadData.DataHolder> {
    Context context;
    ArrayList<UserMode> datalist;

    public ReadData(Context context, ArrayList<UserMode> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(LayoutInflater.from(context).inflate(R.layout.list_formet,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        holder.name.setText(datalist.get(position).getName());
        holder.email.setText(datalist.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {

        TextView name,email;
        public DataHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.re_name);
            email = itemView.findViewById(R.id.re_email);
        }
    }
}
