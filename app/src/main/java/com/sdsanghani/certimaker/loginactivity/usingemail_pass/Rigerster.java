package com.sdsanghani.certimaker.loginactivity.usingemail_pass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsanghani.certimaker.R;

public class Rigerster extends AppCompatActivity {

    EditText email,pass;
    private FirebaseAuth mAuth;
    Button signIn;
    TextView logiClick;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), LogOut.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigerster);

        email = findViewById(R.id.emailTxtregister);
        pass = findViewById(R.id.emailTxtregister);
        signIn = findViewById(R.id.signIn);
        logiClick = findViewById(R.id.loginTxt);
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtxt = email.getText().toString();
                String passtxt = pass.getText().toString();
                if(TextUtils.isEmpty(emailtxt))
                {
                    Toast.makeText(Rigerster.this, "Add email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(passtxt)) {
                    Toast.makeText(Rigerster.this, "Add password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailtxt, passtxt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(Rigerster.this, "Login",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
//                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    Toast.makeText(Rigerster.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        logiClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}