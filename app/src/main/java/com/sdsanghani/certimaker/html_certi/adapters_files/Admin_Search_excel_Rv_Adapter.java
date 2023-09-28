package com.sdsanghani.certimaker.html_certi.adapters_files;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

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
import com.sdsanghani.certimaker.html_certi.data_models.ExcexlFiles;
import com.sdsanghani.certimaker.html_certi.helper_class.SendFiles;

import java.util.List;

public class Admin_Search_excel_Rv_Adapter extends RecyclerView.Adapter<Admin_Search_excel_Rv_Adapter.ExcelHolder> {
    Context context;
    List<ExcexlFiles> excelFiles;
    String evName;
    @NonNull
    @Override
    public ExcelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Admin_Search_excel_Rv_Adapter.ExcelHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_view_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExcelHolder holder, int position) {
        holder.pfdName.setText(evName+"_Excel");
        String url =  excelFiles.get(position).getExcelFile();

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
                files.downloadFile(context,evName,".xlsx",DIRECTORY_DOWNLOADS,url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return excelFiles != null ? excelFiles.size() : 0;
    }

    public void setExcellist(List<ExcexlFiles> excelFiles, String evName, Context context) {
        this.excelFiles = excelFiles;
        this.context = context;
        this.evName=evName;
        if(excelFiles.isEmpty())
        {
            Toast.makeText(context, "NO Excel File Found..", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    class ExcelHolder extends RecyclerView.ViewHolder {
        TextView pfdName;
        ImageButton send,download;
        public ExcelHolder(@NonNull View itemView) {
            super(itemView);
            pfdName =itemView.findViewById(R.id.pdf_name);
            send = itemView.findViewById(R.id.Pdf_send);
            download =itemView.findViewById(R.id.Pdf_download);
        }
    }
}
