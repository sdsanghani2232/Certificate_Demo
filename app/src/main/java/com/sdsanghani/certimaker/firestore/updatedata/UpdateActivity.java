package com.sdsanghani.certimaker.firestore.updatedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsanghani.certimaker.R;
import com.sdsanghani.certimaker.firestore.models.UserModel;
import com.sdsanghani.certimaker.firestore.models.UserViewModle;

public class UpdateActivity extends AppCompatActivity {
    EditText name,email;
    Button update;
    UserViewModle userViewModle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name = findViewById(R.id.editnameupdate);
        email = findViewById(R.id.editemailupdate);
        update = findViewById(R.id.update_fire_store_data);

        userViewModle = new ViewModelProvider(this).get(UserViewModle.class);

        userViewModle.getUserData(getIntent().getStringExtra("id"),this, new UserDataCallback() {
            @Override
            public void onSuccess(UserModel personData) {
                name.setText(personData.getName());
                email.setText(personData.getEmail());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(UpdateActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void UpdateUser(View view) {
        userViewModle.UserUpdate(getIntent().getStringExtra("id"),name.getText().toString()
                ,email.getText().toString(),getApplicationContext());
        finish();
    }

    public interface UserDataCallback{
        void onSuccess(UserModel model);
        void onError(String error);
    }
}