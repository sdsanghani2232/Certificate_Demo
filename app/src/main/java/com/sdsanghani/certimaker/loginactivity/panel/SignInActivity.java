package com.sdsanghani.certimaker.loginactivity.panel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsanghani.certimaker.R;

public class SignInActivity extends AppCompatActivity {

    Button signIn;
    GoogleSignInOptions options;
    GoogleSignInClient client;
    FirebaseAuth auth;
    Intent intent;
    ProgressBar progressBar;
    FirebaseUser currentUser;
    private static final int GOOGLE_SIGNIN_REQUEST_CODE = 100;

    public void onStart() {
        super.onStart();
        idFind();

        if(currentUser != null)
        {
            new CheckUser(getApplicationContext()).checkAdmin(currentUser.getEmail());
            progressBar.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            signIn.setClickable(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        CheckInernet();
        idFind();

         options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

         client = GoogleSignIn.getClient(this,options);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!CheckInernet())
                {
                    Toast.makeText(getApplicationContext(), "Check Internet", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent = client.getSignInIntent();
                    startActivityForResult(intent,GOOGLE_SIGNIN_REQUEST_CODE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGNIN_REQUEST_CODE && data != null && resultCode == RESULT_OK)
        {
            new CheckUser(getApplicationContext()).AuthenticateEmile(data);
            progressBar.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            signIn.setClickable(false);
        }
    }

    private void idFind() {
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        progressBar = findViewById(R.id.progressBar);
        signIn = findViewById(R.id.signInGoogle_panel);
    }


    public boolean CheckInernet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }
}