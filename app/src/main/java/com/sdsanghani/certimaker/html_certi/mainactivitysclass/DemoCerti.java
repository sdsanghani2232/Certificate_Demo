package com.sdsanghani.certimaker.html_certi.mainactivitysclass;

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
import com.sdsanghani.certimaker.html_certi.datamodels.HtmlDataModel;
import com.sdsanghani.certimaker.html_certi.databasefiles.HtmlDataViewModel;
import com.sdsanghani.certimaker.html_certi.htmladapter.RvAdapter;

import java.util.List;

public class DemoCerti extends AppCompatActivity {

    FloatingActionButton info,addCode;
    RecyclerView rv;

    private final static int HTML_REQUEST_CODE = 100;
    HtmlDataViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_certi);

        info =findViewById(R.id.info_page);
        addCode = findViewById(R.id.add_files);
        rv = findViewById(R.id.html_review);
        rv.setLayoutManager(new LinearLayoutManager(this));
        model = new ViewModelProvider(this).get(HtmlDataViewModel.class);

        RvAdapter adapter = new RvAdapter();
        rv.setAdapter(adapter);
        model.getCodeFile().observe(this, new Observer<List<HtmlDataModel>>() {
            @Override
            public void onChanged(List<HtmlDataModel> htmlDataModels) {
                adapter.setFilesList(htmlDataModels);
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