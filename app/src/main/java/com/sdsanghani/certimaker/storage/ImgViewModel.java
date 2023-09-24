package com.sdsanghani.certimaker.storage;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsanghani.certimaker.firestore.adapters.DateAndTimeStamp;

import java.util.ArrayList;
import java.util.List;

public class ImgViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://certimaker-demo.appspot.com"); // if many bucket then pass that url, and no any url pass that consider default bucket
    StorageReference reference = storage.getReference();

    private MutableLiveData<List<String>> imglist;

    public LiveData<List<String>> getimglist()
    {
        if(imglist == null)
        {
            imglist = new MutableLiveData<>();
            lodeImg();
        }
        return imglist;
    }

    private void lodeImg() {
       reference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
           @Override
           public void onSuccess(ListResult listResult) {
               List<String> urls = new ArrayList<>();
               for(StorageReference item: listResult.getItems())
               {
                   item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           urls.add(uri.toString());
                           imglist.setValue(urls);
                       }
                   });
               }
           }
       });
    }

    public void storeImg(Uri uri,UploadImg uploadImg ,Context context)
   {
       reference = storage.getReference().child(uploadImg.getName());
       reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {

                       db.collection("img").document(new DateAndTimeStamp().DateTime())
                               .set(uploadImg)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(context, "img uploaded", Toast.LENGTH_SHORT).show();
                                   }
                               });
                   }
               });
           }
       });
   }

//   public void downlode(String s, UploadImg uploadImg)
//   {
//       reference = storage.getReference().child(uploadImg.getImgUrl());
//       File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),uploadImg.getName());
//
//       reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//           @Override
//           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//               Log.d("downlode ","pass");
//           }
//       });
//   }
}
