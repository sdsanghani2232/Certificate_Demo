package com.sdsanghani.certimaker.html_certi.adapters_files;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.html_certi.data_models.PdfDetails;
import com.sdsanghani.certimaker.html_certi.helper_class.SendFiles;

import java.util.List;

public class Admin_Search_Pdf_Rv_adapter extends RecyclerView.Adapter<Admin_Search_Pdf_Rv_adapter.SearchHelper> {

    Context context;
    List<PdfDetails> pdfDetails;
    String evName;

    @NonNull
    @Override
    public SearchHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Admin_Search_Pdf_Rv_adapter.SearchHelper(LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_view_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHelper holder, int position) {
        String name[] = pdfDetails.get(position).getPdfName().split("@");
        holder.pfdName.setText(name[0]);
        String url =  pdfDetails.get(position).getPdfUrl();

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFiles files = new SendFiles();
                files.sendFile(context,url,evName);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFiles files = new SendFiles();
                files.downloadFile(context,evName,".pdf",DIRECTORY_DOWNLOADS,url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfDetails != null ? pdfDetails.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPdflist(List<PdfDetails> pdfDetails, String evName, Context context) {
        this.pdfDetails = pdfDetails;
        this.context = context;
        this.evName=evName;
        if(pdfDetails.isEmpty())
        {
            Toast.makeText(context, "NO Certificate Found..", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    class SearchHelper extends RecyclerView.ViewHolder {
        TextView pfdName;
        ImageButton send,download;
        public SearchHelper(@NonNull View itemView) {
            super(itemView);
            pfdName =itemView.findViewById(R.id.pdf_name);
            send = itemView.findViewById(R.id.Pdf_send);
            download =itemView.findViewById(R.id.Pdf_download);
        }
    }
}
