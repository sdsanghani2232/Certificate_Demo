package com.sdsanghani.certimaker.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsanghani.certimaker.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FireStoreActivity extends AppCompatActivity {

    EditText name,email;
    Button submit;
    String dataName ,dataEmail;

    RecyclerView rv;
    FirebaseFirestore db;
    HashSet<String> user; //  that use id but her not any is so user = name+email

    ArrayList<UserMode> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_store);

        name = findViewById(R.id.editname);
        email = findViewById(R.id.editemail);
        submit = findViewById(R.id.submit_fire_store);
        rv = findViewById(R.id.recyclerview_data);
        user = new HashSet<>();
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db = FirebaseFirestore.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataName = name.getText().toString();
                dataEmail = email.getText().toString();

                UserMode userMode = new UserMode(dataName,dataEmail);
                db.collection("user").document(new DateAndTimeStamp().DateTime()) // here that can be null
                        .set(userMode)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(FireStoreActivity.this, "add data", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(FireStoreActivity.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        getAllData();
    }

    public void getAllData()
    {
        datalist = new ArrayList<>();
        rv.setAdapter(new ReadData(getApplicationContext(),datalist));
        // that is real time so use is better to all other method
        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null)
                {
                    List<UserMode> data = value.toObjects(UserMode.class);
                    for (UserMode userMode : data)
                    {
                        if(!user.contains(userMode.getName()+userMode.getEmail()))
                        {
                            user.add(userMode.getName()+userMode.getEmail());
                            datalist.add(0,userMode);
                            rv.getAdapter().notifyItemChanged(0);

                        }
                    }

                    rv.setAdapter(new ReadData(getApplicationContext(),datalist));
                }
            }
        });
    }
}