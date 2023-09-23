package com.sdsanghani.certimaker.firestorageclass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdsanghani.certimaker.R;

import java.util.ArrayList;
import java.util.List;

public class UploadImgActivity extends AppCompatActivity {

    private static final int IMG_REQUEST_CODE = 100;
    ImgViewModel model;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        rv = findViewById(R.id.img_reView);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        model = new ViewModelProvider(this).get(ImgViewModel.class);


        Button img = findViewById(R.id.img_button);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMG_REQUEST_CODE);
            }
        });
        model.getimglist().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                rv.setAdapter(new ImgAdapter(getApplicationContext(),strings,model));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        String name = "img"+System.currentTimeMillis();
        if(requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK)
        {
            UploadImg  uploadImg = new UploadImg(name,uri.toString());
            model.storeImg(uri,uploadImg,getApplicationContext());
        }
    }
}