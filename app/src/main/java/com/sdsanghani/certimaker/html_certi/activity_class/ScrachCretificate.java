package com.sdsanghani.certimaker.html_certi.activity_class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.adapters_files.Admin_Search_Rv_adapter;
import com.sdsanghani.certimaker.html_certi.data_base_files.Search_Files;
import com.sdsanghani.certimaker.html_certi.data_models.PdfDetails;

import java.util.List;

public class ScrachCretificate extends AppCompatActivity {

    EditText event;
    Button search;
    RecyclerView rv;
    Search_Files model;
    Admin_Search_Rv_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrach_cretificate);
        search =findViewById(R.id.search_btn);
        event = findViewById(R.id.search_file);
        adapter = new Admin_Search_Rv_adapter();
        rv = findViewById(R.id.search_rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        model = new ViewModelProvider(this).get(Search_Files.class);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchData();
            }
        });
    }

    public void searchData() {

        String eventName = event.getText().toString();
        if(eventName.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter Event name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            model.getEventname(eventName.trim());
            model.getPdfFiles();
            model.getPdfFiles().observe(this, new Observer<List<PdfDetails>>() {
                @Override
                public void onChanged(List<PdfDetails> pdfDetails) {
                    Log.d("pdf list",pdfDetails.toString());
                    adapter.setPdflist(pdfDetails,eventName.trim(),getApplicationContext());
                }
            });
        }
    }
}