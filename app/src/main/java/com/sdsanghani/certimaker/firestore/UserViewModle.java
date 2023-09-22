package com.sdsanghani.certimaker.firestore;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

// view model
public class UserViewModle extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<List<UserModel>> userlist;

    public LiveData<List<UserModel>> getUserList()
    {
        if(userlist == null)
        {
            userlist = new MutableLiveData<>();
            loadUser();
        }
        return userlist;
    }

    private void loadUser()
    {
        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null)
                {
                    List<UserModel> user = value.toObjects(UserModel.class);
                    userlist.postValue(user);
                }
            }
        });
    }

    public void UserAdd(UserModel userModel, Context context)
    {
        db.collection("user").document(new DateAndTimeStamp().DateTime())
                .set(userModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Add Data successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error....", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void UserUpdate(String id, String name_New, String email_New, Context context)
    {
        db.collection("user").document(id)
                .update("email",email_New,"name",name_New)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void UserDelete(String id,Context context) {
        db.collection("user").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "delete Data successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserData(String id, Context context, UpdateActivity.UserDataCallback callback)
    {

        db.collection("user").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null)
                {
                    UserModel personData = value.toObject(UserModel.class);
                    callback.onSuccess(personData);
                }else {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    callback.onError("error");
                }
            }
        });
    }
}
