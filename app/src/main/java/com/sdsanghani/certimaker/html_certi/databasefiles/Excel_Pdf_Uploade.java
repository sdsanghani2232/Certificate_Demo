package com.sdsanghani.certimaker.html_certi.databasefiles;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Excel_Pdf_Uploade {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference;

    public void uploadPdf(byte[] pdfBytes, String pdfname, String eventName, String userEmil, final OnPdfUploadListener listener)
    {
        reference = storage.getReference().child("events /"+eventName + "/"+pdfname);
        reference.putBytes(pdfBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       db.collection(eventName).document(userEmil).
                               set(new HashMap<String,Object>(){
                                   {
                                       put("Pdf File",uri.toString());
                                   }
                               })
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       listener.onUploadSuccess();
                                   }
                               });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void uploadExcel(byte[] excel, String newExcel, String eventName)
    {
        reference = storage.getReference().child("events /"+eventName + "/"+newExcel);
        reference.putBytes(excel).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        db.collection(eventName).document(eventName).
                                set(new HashMap<String,Object>(){
                                    {
                                        put("Excel File",uri.toString());
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("code file","add complte");
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("code file","not add");
            }
        });
    }

    public interface OnPdfUploadListener {
        void onUploadSuccess();
    }
}
