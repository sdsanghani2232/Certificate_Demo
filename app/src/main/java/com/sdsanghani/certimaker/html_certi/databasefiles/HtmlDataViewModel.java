package com.sdsanghani.certimaker.html_certi.databasefiles;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsanghani.certimaker.html_certi.datamodels.HtmlDataModel;

import java.util.List;

public class HtmlDataViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference();

    // get html and txt files
    private MutableLiveData<List<HtmlDataModel>> CodeFileList;

    public LiveData<List<HtmlDataModel>> getCodeFile()
    {
        if(CodeFileList == null)
        {
            CodeFileList = new MutableLiveData<>();
            getFiles();
        }
        return CodeFileList;
    }

    private void getFiles() {

        db.collection("Codes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null)
                {
                    List<HtmlDataModel> code = value.toObjects(HtmlDataModel.class);
                    CodeFileList.postValue(code);

                }
            }
        });
    }


    public void UploadHtmlFile(Uri uri, Context context)
    {
        String name = "html" +System.currentTimeMillis();
        reference = storage.getReference().child("html/"+name);

        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HtmlDataModel dataModel = new HtmlDataModel(name,uri.toString());
                        db.collection("Codes").document(name)
                                .set(dataModel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "File uploaded ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });
    }

}
