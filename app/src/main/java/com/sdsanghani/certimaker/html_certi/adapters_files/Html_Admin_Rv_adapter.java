package com.sdsanghani.certimaker.html_certi.adapters_files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.data_models.HtmlFiles;
import com.sdsanghani.certimaker.html_certi.activity_class.Generate_Certificate;
import com.sdsanghani.certimaker.html_certi.helper_class.Html_View;

import java.util.List;

public class Html_Admin_Rv_adapter extends RecyclerView.Adapter<Html_Admin_Rv_adapter.CodeFile> {

    Context context;
    List<HtmlFiles> htmlFiles;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilesList(List<HtmlFiles> filesList) {
        this.htmlFiles = filesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CodeFile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Html_Admin_Rv_adapter.CodeFile(LayoutInflater.from(parent.getContext()).inflate(R.layout.html_file_layout,parent,false));
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull CodeFile holder, int position) {
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webSettings.setLoadWithOverviewMode(true);
        holder.webSettings.setJavaScriptEnabled(true);
        holder.webSettings.setUseWideViewPort(true);
        holder.url = htmlFiles.get(position).getFileDownloadUrl();
        new Html_View(new Html_View.OnHtmlFetchedListener() {
            @Override
            public void OnHtmlFetched(String code) {
                if(code != null)
                {
                    holder.code = code;
                    holder.webView.loadData(code,"text/html", "UTF-8");
                }
            }
        }).execute(holder.url);

        holder.use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Generate_Certificate.class)
                        .putExtra("code",holder.code)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    @Override
    public int getItemCount() {
        return htmlFiles != null ? htmlFiles.size() : 0;
    }

    class CodeFile extends RecyclerView.ViewHolder {

        WebView webView;
        String url,code;
        Button use;
        WebSettings webSettings;
        public CodeFile(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.html_code_view);
            webSettings = webView.getSettings();
            use = itemView.findViewById(R.id.Use_file);
        }
    }
}
