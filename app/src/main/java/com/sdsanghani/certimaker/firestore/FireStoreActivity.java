package com.sdsanghani.certimaker.firestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.firestore.adapters.DateAndTimeStamp;
import com.sdsanghani.certimaker.firestore.adapters.UserlistAdapter;
import com.sdsanghani.certimaker.firestore.models.UserModel;
import com.sdsanghani.certimaker.firestore.models.UserViewModle;

import java.util.ArrayList;
import java.util.List;
public class FireStoreActivity extends AppCompatActivity {

    private UserViewModle userViewModle;
    EditText name,email;
    Button submit;
    String dataName ,dataEmail;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_store);

        name = findViewById(R.id.editname);
        email = findViewById(R.id.editemail);
        submit = findViewById(R.id.submit_fire_store);
        rv = findViewById(R.id.recyclerview_data);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        userViewModle = new ViewModelProvider(this).get(UserViewModle.class);
        userViewModle.getUserList().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                rv.setAdapter(new UserlistAdapter(getApplicationContext(), (ArrayList<UserModel>) userModels,userViewModle));
            }
        });
    }

    public void submit(View view) {
        dataName = name.getText().toString();
        dataEmail = email.getText().toString();

        UserModel userModel = new UserModel(dataName,dataEmail,new DateAndTimeStamp().DateTime());
        userViewModle.UserAdd(userModel,getApplicationContext());
    }

}