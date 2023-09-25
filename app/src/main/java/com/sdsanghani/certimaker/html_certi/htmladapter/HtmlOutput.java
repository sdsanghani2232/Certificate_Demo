package com.sdsanghani.certimaker.html_certi.htmladapter;

import android.graphics.ImageDecoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlOutput extends AsyncTask<String,Void,String> {
    private OnHtmlFetchedListener listener;

    public HtmlOutput(OnHtmlFetchedListener listener)
    {
        this.listener =listener;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            Log.d("data","Error");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String content) {
        if(content != null)
        {
            listener.OnHtmlFetched(content);
        }
    }

    public interface OnHtmlFetchedListener
    {
       void OnHtmlFetched(String code);
    }
}
