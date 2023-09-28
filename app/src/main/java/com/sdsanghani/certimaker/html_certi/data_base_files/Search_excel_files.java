package com.sdsanghani.certimaker.html_certi.data_base_files;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsanghani.certimaker.html_certi.data_models.ExcexlFiles;

import java.util.ArrayList;
import java.util.List;

public class Search_excel_files extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<String> eventName = new MutableLiveData<>();
    private MutableLiveData<List<ExcexlFiles>> excelList;

    public void getEventname(String EvName)
    {
        eventName.setValue(EvName);
    }
    public LiveData<List<ExcexlFiles>> getPdfFiles()
    {

        excelList = new MutableLiveData<>();
        getFiles();
        return excelList;
    }

    private void getFiles() {
        if(eventName.getValue() != null)
        {
            db.collection("Events_Excels").document(eventName.getValue()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        ExcexlFiles files = documentSnapshot.toObject(ExcexlFiles.class);
                        List<ExcexlFiles> list = new ArrayList<>();
                        list.add(files);
                        excelList.setValue(list);
                        Log.d("list of data ",excelList.toString());
                    }
                }
            });
        }
    }
}
