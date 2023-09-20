package com.sdsanghani.certimaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Daksh");

        reference.setValue("Daksh sanghani");

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("User");
        String idBYDatabse=  reference1.push().getKey();
        String idBYDatabse1=  reference1.push().getKey();
        String idBYDatabse2=  reference1.push().getKey();
        DemoData demoData = new DemoData("Daksh","d@gmil.com");
        DemoData demoData1 = new DemoData("het","d@gmil.com");
        DemoData demoData2 = new DemoData("jay","d@gmil.com");

        reference1.child(idBYDatabse).setValue(demoData);
        reference1.child(idBYDatabse1).setValue(demoData1);
        reference1.child(idBYDatabse2).setValue(demoData2);
//        reference1.child(idBYDatabse).child("Dakshh").setValue("Sanghani");


        // get data

         reference1.child(idBYDatabse).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                DemoData data = snapshot.getValue(DemoData.class);
                Log.d("value",""+data.getName()+data.getEmail());
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

                 Log.d("error","error");
             }
         });
    }
}