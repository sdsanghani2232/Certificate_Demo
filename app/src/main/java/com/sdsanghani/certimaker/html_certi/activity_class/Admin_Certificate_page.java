package com.sdsanghani.certimaker.html_certi.activity_class;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.data_models.HtmlFiles;
import com.sdsanghani.certimaker.html_certi.data_base_files.HtmlDataViewModel;
import com.sdsanghani.certimaker.html_certi.adapters_files.Html_Admin_Rv_adapter;

import java.util.List;

public class Admin_Certificate_page extends AppCompatActivity {

    FloatingActionButton info,addCode;
    RecyclerView rv;
    HtmlDataViewModel model;

    private final static int HTML_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_certificate_page);

        info =findViewById(R.id.info_page);
        addCode = findViewById(R.id.add_files);
        rv = findViewById(R.id.html_review);
        rv.setLayoutManager(new LinearLayoutManager(this));
        model = new ViewModelProvider(this).get(HtmlDataViewModel.class);

        Html_Admin_Rv_adapter adapter = new Html_Admin_Rv_adapter();
        rv.setAdapter(adapter);
        model.getCodeFile().observe(this, new Observer<List<HtmlFiles>>() {
            @Override
            public void onChanged(List<HtmlFiles> htmlFiles) {
                adapter.setFilesList(htmlFiles);
            }
        });


        addCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/html");
                startActivityForResult(intent,HTML_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HTML_REQUEST_CODE && resultCode ==RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            model.UploadHtmlFile(uri,getApplicationContext());
        }
    }
}