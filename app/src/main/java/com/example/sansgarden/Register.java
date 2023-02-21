package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    EditText mUsername, mEmail, mPassword, mConfirmPassword;
    Button mRegisterbtn;
    ImageView mBackbtn;
    TextView mSignIn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    public FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername        = findViewById(R.id.username);
        mEmail           = findViewById(R.id.RegisterEmailAddress);
        mPassword        = findViewById(R.id.RegisterPassword);
        mConfirmPassword = findViewById(R.id.RegisterPasswordConfirm);
        mRegisterbtn     = findViewById(R.id.registerbtn);
        mBackbtn         = findViewById(R.id.backbtn);
        mSignIn          = findViewById(R.id.signin);

        fAuth            = FirebaseAuth.getInstance();
        fstore           = FirebaseFirestore.getInstance();
        progressBar      = findViewById(R.id.progressBar);

       /* if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),bna.class));
            finish();
        }*/

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirm_password = mConfirmPassword.getText().toString().trim();
                String Username = mUsername.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confirm_password)){
                    mConfirmPassword.setError("Password Confirmation is Required");
                    return;
                }
                if(password.length() < 8){
                    mPassword.setError("Minimum Password is 8 Characters");
                    return;
                }
                if(!password.equals(confirm_password)){
                    mConfirmPassword.setError("Those password didn't match. Please try Again");
                    return;
                }else {

                }
                //progressBar.setVisibility(View.VISIBLE);

                // Register user di Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //Kirim Verifikasi link

                            FirebaseUser user;
                            user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                 //if(task.isSuccessful()) {
                                     Toast.makeText(Register.this, "User Created ! Verification Email has been Sent", Toast.LENGTH_SHORT).show();
                                     userID = fAuth.getCurrentUser().getUid();
                                     DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fName",Username);
                                    user.put("email",email);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG,"on Success: user profile is created for"+ userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"onFailure :" + e.toString());
                                        }
                                    });
                                     startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                 //}
                                 //else{ Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                 //}
                                }
                            });

                        }else {
                            Toast.makeText(Register.this, "Error !" + task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }

        });
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}