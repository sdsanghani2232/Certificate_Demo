package com.sdsanghani.certimaker.loginactivity.panel;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CheckUser {

    Context context;

    public CheckUser(Context context) {
        this.context = context;
    }

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;



    public void AuthenticateEmile(Intent Data)
    {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(Data);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

            auth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                user = auth.getCurrentUser();
                                if(user != null) checkAdmin(user.getEmail());
                            }
                            else
                            {
                                Toast.makeText(context, "Login ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void checkAdmin(String email) {

        DocumentReference reference = db.collection("admin").document(email);

        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
//                    context.startActivity(new Intent(context,AdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    // use that remove stack and new activity that clear "full stack" and add new activity
                    TaskStackBuilder.create(context)
                            .addNextIntentWithParentStack(new Intent(context, AdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                            .startActivities();
                }
                else checkUser(email);
            }
        });
    }

    private void checkUser(String email) {

        DocumentReference reference = db.collection("user").document(email);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
//                    context.startActivity(new Intent(context, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    TaskStackBuilder.create(context)
                            .addNextIntentWithParentStack(new Intent(context, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                            .startActivities();
                }
                else addUser(email);
            }
        });
    }

    private void addUser(String email) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("user").document(email)
                .set(new HashMap<String,Object>()
                {
                    {
                        put("email",email);
                    }

                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        context.startActivity(new Intent(context, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                        ((AppCompatActivity) context).finish(); that give some error so not use that
                        TaskStackBuilder.create(context)
                                .addNextIntentWithParentStack(new Intent(context, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                .startActivities();
                    }
                });
    }
}
