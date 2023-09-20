package com.sdsanghani.certimaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogOut extends AppCompatActivity {

    TextView emilid;
    Button logOut;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        emilid = findViewById(R.id.emailid);
        logOut = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        else
        {
            emilid.setText(user.getEmail());
        }
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}