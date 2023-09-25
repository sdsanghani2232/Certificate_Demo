package com.sdsanghani.certimaker;
//implementation("org.apache.poi:poi:5.2.3")
//implementation("org.apache.poi:poi-ooxml:5.2.3")

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import org.apache.poi.ss.usermodel.Cell; //for excel read and write
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelAndCsv extends AppCompatActivity {


    Button excel,csv;
    Workbook workbook;
    Sheet sheet;
    int row = 1,currentIndex = 0;
    int width,height;
    WebView web;
    String code;
    List<List<String>> dataOfExcel;
    List<List<String>> dataOfCsv;
    List<String> rowdataOfCav;
    boolean excelFile = false;
    boolean csvFile= false;
    private static final int CONFORM_CODE_EXCEL = 100;
    private static final int CONFORM_CODE_CSV = 200;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_excel_and_csv);
        excel = findViewById(R.id.file_get_excel);
        csv = findViewById(R.id.file_get_csv);
        web = findViewById(R.id.web);
        code = "<html>\n" +
                "    <head>\n" +
                "        <center>\n" +
                "<style>\n"+
                ".signature, .title { \n" +
                "float:left;\n" +
                "  border-top: 1px solid #000;\n" +
                "  width: 200px; \n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "<div style=\"width:800px; height:600px; padding:20px; text-align:center; border: 10px solid #787878\">\n" +
                "<div style=\"width:750px; height:550px; padding:20px; text-align:center; border: 5px solid #787878\">\n" +
                "       <span style=\"font-size:50px; font-weight:bold\">Certificate of Completion</span>\n" +
                "       <br><br>\n" +
                "       <span style=\"font-size:25px\"><i>This is to certify that</i></span>\n" +
                "       <br><br>\n" +
                "       <span style=\"font-size:30px\"><b>$student.getFullName()</b></span><br/><br/>\n" +
                "       <span style=\"font-size:25px\"><i>has participant in</i></span> <br/><br/>\n" +
                "       <span style=\"font-size:30px\">GDSC</span> <br/><br/>\n" +
                "       <span style=\"font-size:25px\"><i>Completed Date</i></span><br>\n" +
                "       <span style=\"font-size:25px\"><i>01-Sep-2023</i></span><br>\n" +
                "<table style=\"margin-top:40px;float:left\">\n" +
                "<tr>\n" +
                "<td><span><b>$student.getFullName()</b></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width:200px;float:left;border:0;border-bottom:1px solid #000;\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align:center\"><span><b>Signature</b></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table style=\"margin-top:40px;float:right\">\n" +
                "<tr>\n" +
                "<td><span><b>$student.getFullName()</b></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width:200px;float:right;border:0;border-bottom:1px solid #000;\"></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align:center\"><span><b>Signature</b></td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</div>\n" +
                "</center>\n" +
                " \n" +
                "    </body>";
        Log.d("code",code);

        web.loadDataWithBaseURL(null,code,"text/html","UTF-8",null);
        web.setWebViewClient(new WebViewClient());
        WebSettings webSettings = web.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
// add xml to click funcation
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
        if (requestCode == CONFORM_CODE_EXCEL && resultCode == RESULT_OK && data != null)
        {

            uri = data.getData();
            resolver = getContentResolver();
            type = resolver.getType(uri);
            if (type != null && (type.equals("application/vnd.ms-excel") || type.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")))
            {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    workbook = new XSSFWorkbook(inputStream);
                    sheet = workbook.getSheetAt(0);
                    dataOfExcel = new ArrayList<>();
                    for (Row row : sheet)
                    {
                        boolean isRowEmpty = true;
                        List<String> rowData = new ArrayList<>();
                        for (Cell cell : row)
                        {
                            String celldata = cell.getStringCellValue();
                            if(!celldata.isEmpty())
                            {
                                rowData.add(celldata);
                                isRowEmpty = false;
                            }
                        }
                        if(!isRowEmpty) dataOfExcel.add(rowData);
                    }
                    workbook.close();
                    createExcel();
                    excelFile = true;
                    ExcelDelay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == CONFORM_CODE_CSV && resultCode == RESULT_OK && data != null)
        {
            uri = data.getData();
            resolver = getContentResolver();
            type = resolver.getType(uri);
            if(type != null && (type.equals("text/comma-separated-values")))
            {
                try
                {
                    InputStream stream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    dataOfCsv = new ArrayList<>();
                    String line;
                    while ((line = reader.readLine())!= null)
                    {
                        String[] parts = line.split("\\,");
                        rowdataOfCav = new ArrayList<>();

                        for (String part : parts)
                            rowdataOfCav.add(part.trim());

                        dataOfCsv.add(rowdataOfCav);
                    }
                    createExcel();
                    csvFile = true;
                    CsvDelay();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(this, "Select CSV file", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Error on get file on storage", Toast.LENGTH_SHORT).show();
        }
    }

    private void ExcelDelay() {
        if(currentIndex<dataOfExcel.size())
        {
            List<String> row = dataOfExcel.get(currentIndex);
            String name = row.get(0);
            String email = row.get(1);
            pdfCreater(name,email);
            Log.d("check data" ,name);
        }else
        {
//            web.setVisibility(View.GONE);
//            web.loadUrl("about:blank");
            excelFile = false;
            currentIndex = 0;
        }
    }
    //TODO : make one function for delay
    private void CsvDelay() {
        if(currentIndex<dataOfCsv.size())
        {
            List<String> row = dataOfCsv.get(currentIndex);
            String name = row.get(0);
            String email = row.get(1);
//            Log.d("check data" ,name);
            pdfCreater(name,email);
        }else
        {
            csvFile = false;
//            web.setVisibility(View.GONE);
//            web.loadUrl("about:blank");
            currentIndex = 0;
        }
    }


    private void createExcel() {

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("sheet1");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("email");
        headerRow.createCell(2).setCellValue("Id");
    }

    public void pdfCreater(String name,String email) {

        String codeNew = code.replace("$student.getFullName()",name);
        web.setVisibility(View.VISIBLE);
        web.loadUrl("about:blank");
        web.loadDataWithBaseURL(null,codeNew,"text/html","UTF-8",null);

        web.postDelayed(new Runnable() {
            @Override
            public void run() {
                width = web.getMeasuredWidth();
                height = web.getMeasuredHeight();
                carete(name,email);
            }
        }, 400);
    }

    private void carete(String name,String email)  {
        View view = findViewById(R.id.web);
        PdfDocument pd;
        PdfDocument.Page page;
        pd = new PdfDocument();
        PdfDocument.PageInfo pg = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        page = pd.startPage(pg);
        view.draw(page.getCanvas());
        pd.finishPage(page);

        File save = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String crName = "cr"+System.currentTimeMillis()+".pdf";
        File f = new File(save,crName);
        try {
            FileOutputStream fo = new FileOutputStream(f);
            pd.writeTo(fo);
            pd.close();
            fo.close();
            addexcle(name,email);
//            Toast.makeText(this, "create success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addexcle(String name,String email) {
        Row row1 = sheet.createRow(row++);
        row1.createCell(0).setCellValue(name);
        row1.createCell(1).setCellValue(email);
        row1.createCell(2).setCellValue("certificate " + System.currentTimeMillis());
//        Log.d("check data","certificate created");
        downlodeExcle();

    }

    private void downlodeExcle() {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String nameFile = "Certificate"+System.currentTimeMillis()+".xlsx";
            File OUTPUT = new File(file,nameFile);
            try {
                OutputStream stream = new FileOutputStream(OUTPUT);
                workbook.write(stream);
            } catch (IOException  e) {
                throw new RuntimeException(e);
            }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                if(excelFile) ExcelDelay();
                else CsvDelay();
            }
        },400);
    }
}