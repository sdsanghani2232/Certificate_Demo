package com.sdsanghani.certimaker.html_certi.activity_class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.adapters_files.Admin_Search_Pdf_Rv_adapter;
import com.sdsanghani.certimaker.html_certi.adapters_files.Admin_Search_excel_Rv_Adapter;
import com.sdsanghani.certimaker.html_certi.data_base_files.Search_excel_files;
import com.sdsanghani.certimaker.html_certi.data_models.ExcexlFiles;

import java.util.List;

public class ScrachExcel extends AppCompatActivity {
    EditText event;
    Button search;
    RecyclerView rv;
    Search_excel_files model;
    Admin_Search_excel_Rv_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrach_excel);

        search =findViewById(R.id.search_btn_excel);
        event = findViewById(R.id.search_Excel);
        adapter = new Admin_Search_excel_Rv_Adapter();
        rv = findViewById(R.id.search_excel_rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        model = new ViewModelProvider(this).get(Search_excel_files.class);

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
            model.getPdfFiles().observe(this, new Observer<List<ExcexlFiles>>() {
                @Override
                public void onChanged(List<ExcexlFiles> excexlFiles) {
                    adapter.setExcellist(excexlFiles,eventName.trim(),getApplicationContext());
                }
            });
        }
    }
}