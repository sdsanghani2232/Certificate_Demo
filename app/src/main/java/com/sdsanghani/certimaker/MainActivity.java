package com.sdsanghani.certimaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
// that not need for that application
    private  static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private  static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static  final int WRITE_REQUEST_CODE = 100;
    private static  final int READ_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       requestRuntimePermission();
    }

    private void requestRuntimePermission() {
        if(ActivityCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else if(ActivityCompat.shouldShowRequestPermissionRationale(this,WRITE_EXTERNAL_STORAGE))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("get the permission for write data").
                    setTitle("Permission Required").
                    setCancelable(false).
                    setPositiveButton("ok", (dialogInterface, i) -> ActivityCompat.requestPermissions(MainActivity.this,new String[]{WRITE_EXTERNAL_STORAGE},WRITE_REQUEST_CODE)).
                    setNegativeButton("cancle", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE},WRITE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == WRITE_REQUEST_CODE)
        {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,WRITE_EXTERNAL_STORAGE))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please get permission to write the file in setting").
                        setTitle("Permission Required").
                        setCancelable(false).
                        setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getPackageName(),null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        }).
                        setNegativeButton("cancle", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            }

            else
            {
                requestRuntimePermission();
            }
        }
    }
}