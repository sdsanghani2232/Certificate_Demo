package com.sdsanghani.certimaker.html_certi.mainactivitysclass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.filesgenerater.Csv;
import com.sdsanghani.certimaker.html_certi.filesgenerater.Excel;
import com.sdsanghani.certimaker.html_certi.datamodels.FilesDetails;

public class GenratesCerti extends AppCompatActivity {

    RecyclerView rv;
    Button excel,csv;
    WebView webView;
    View view;
    FilesDetails details ;
    String url;

    private static final int CONFORM_CODE_EXCEL = 100;
    private static final int CONFORM_CODE_CSV = 200;


    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrates_certi);

        rv = findViewById(R.id.rv_created);
        excel = findViewById(R.id.excel_file);
        csv = findViewById(R.id.csv_file);
        webView = findViewById(R.id.html_file);
        url = getIntent().getStringExtra("url");
        details = new FilesDetails();

        details.setCode(getIntent().getStringExtra("code"));
        Log.d("code files",details.getCode());


        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        details.setEventName("git hub");

        webView.loadDataWithBaseURL(null,details.getCode(),"text/html", "UTF-8",null);

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