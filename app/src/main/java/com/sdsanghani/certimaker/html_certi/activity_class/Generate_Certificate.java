package com.sdsanghani.certimaker.html_certi.activity_class;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.files_generater.Csv;
import com.sdsanghani.certimaker.html_certi.files_generater.Excel;
import com.sdsanghani.certimaker.html_certi.data_models.FilesDetails;

public class Generate_Certificate extends AppCompatActivity {

    Button excel,csv;
    WebView webView;
    View view;
    FilesDetails details;

    private static final int CONFORM_CODE_EXCEL = 100;
    private static final int CONFORM_CODE_CSV = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_certificate);

        getIds();
        setWebView();

        excel.setOnClickListener(view -> {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("application/vnd.ms-excel");
                String[] mintypes = {"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
                i.putExtra(Intent.EXTRA_MIME_TYPES,mintypes);
                startActivityForResult(i,CONFORM_CODE_EXCEL);

        });

        csv.setOnClickListener(view -> {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("text/*");
                startActivityForResult(i,CONFORM_CODE_CSV);
        });
    }


    private void getIds() {

        excel = findViewById(R.id.excel_file);
        csv = findViewById(R.id.csv_file);
        webView = findViewById(R.id.html_file);
        details = new FilesDetails();
        details.setCode(getIntent().getStringExtra("code"));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {

        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.loadDataWithBaseURL(null,details.getCode(),"text/html", "UTF-8",null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri;
        ContentResolver resolver;
        String type;

        if (requestCode == CONFORM_CODE_EXCEL && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            resolver = getContentResolver();
            type = resolver.getType(uri);
            if (type != null && (type.equals("application/vnd.ms-excel") || type.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")))
            {
                view = findViewById(R.id.html_file);

               Excel excel1 = new Excel(getApplicationContext(),details);
               excel1.excelToPdf(uri,webView,view);
            }
        }
        else if (requestCode == CONFORM_CODE_CSV && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            resolver = getContentResolver();
            type = resolver.getType(uri);
            if(type != null && (type.equals("text/comma-separated-values")))
            {
                view = findViewById(R.id.html_file);
                Csv csv =new Csv(getApplicationContext(),details);
                csv.csvToPdf(uri,webView,view);
            }
            else
            {
                Toast.makeText(this, "Select CSV file", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "No any file Selected", Toast.LENGTH_SHORT).show();
        }

    }
}