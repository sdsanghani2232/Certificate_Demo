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
import com.sdsanghani.certimaker.MainActivity;
import com.sdsanghani.certimaker.R;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    private FirebaseAuth mAuth;
    Button login;
    TextView regitxt;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            if(currentUser.getEmail().equals("daksh@gmail.com"))
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), LogOut.class));
                finish();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailTxt);
        pass = findViewById(R.id.passtxt);
        login = findViewById(R.id.login);
        regitxt = findViewById(R.id.registertxt);
        mAuth = FirebaseAuth.getInstance();

        regitxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Rigerster.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtxt = email.getText().toString();
                String passtxt = pass.getText().toString();
                if(TextUtils.isEmpty(emailtxt))
                {
                    Toast.makeText(LoginActivity.this, "Add email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(passtxt)) {
                    Toast.makeText(LoginActivity.this, "Add password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailtxt, passtxt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String emailID = user.getEmail();
                                    if(emailID.equals("daksh@gmail.com"))
                                    {
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        startActivity(new Intent(getApplicationContext(),LogOut.class));
                                        finish();
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "invalid Data.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}