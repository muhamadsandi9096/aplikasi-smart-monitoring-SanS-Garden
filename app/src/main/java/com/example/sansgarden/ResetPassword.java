package com.example.sansgarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    EditText mEmail;
    Button mRstBtnPW;
    ImageView mBackreset;

    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmail           = findViewById(R.id.EmailAddressforreset);
        mRstBtnPW        = findViewById(R.id.rstbtnpw);
        mBackreset       = findViewById(R.id.backrst);

        fAuth            = FirebaseAuth.getInstance();
        progressBar      = findViewById(R.id.progressBar);

        /*if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));
            finish();
        }*/

        mRstBtnPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = mEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                /*final Task<Void> voidTask = fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ResetPassword.class));
                        } else {
                            Toast.makeText(ResetPassword.this, "Error ! Reset Link is Not Sent", Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });*/

                AlertDialog dialog= new AlertDialog.Builder(ResetPassword.this)
                        .setTitle("Select")
                        .setMessage("Reset Password?")
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel",null)
                        .show();
                Button positiveButton =dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reset Pw User
                        final Task<Void> voidTask = fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ResetPassword.class));
                                } else {
                                    Toast.makeText(ResetPassword.this, "Error ! Reset Link is Not Sent", Toast.LENGTH_SHORT).show();
                                    //progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });



            }
        });
        mBackreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}