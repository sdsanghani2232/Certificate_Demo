package com.sdsanghani.certimaker.storage;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsanghani.certimaker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

//com.squareup.picasso:picasso:2.8
public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgHolder> {

    Context context;
    List<String> datalist;
    ImgViewModel model;
    public ImgAdapter(Context context, List<String> datalist, ImgViewModel model) {
        this.context = context;
        this.datalist = datalist;
        this.model = model;
    }

    @NonNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImgAdapter.ImgHolder(LayoutInflater.from(context).inflate(R.layout.img_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(datalist.get(position)).into(holder.imageView);
        holder.dow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                model.downlode(datalist.get(position),uploadImg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class ImgHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button dow;
        public ImgHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_layout);
            dow = itemView.findViewById(R.id.downlode);
        }
    }
}
