package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInPage extends AppCompatActivity {

    private EditText mFullName,mEmail,mPassword;
    private Button mRegisterbtn;
    private TextView mLoginBtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private String email,password;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        mFullName=findViewById(R.id.ETName);
        mEmail=findViewById(R.id.ETEmail);
        mPassword=findViewById(R.id.ETPassword);

        mRegisterbtn=findViewById(R.id.ServerAdd);
        mLoginBtn=findViewById(R.id.tolog);

        //we are getting the current instance from the database to perform various operations
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.pbStdAddPage);

       /* if(fAuth.getCurrentUser() != null){
            Intent toLogin=new Intent(SignInPage.this,SelectorPage.class);
            startActivity(toLogin);
            finish();

        }
       */

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=mEmail.getText().toString().trim();
                password=mPassword.getText().toString().trim();
                String name=mFullName.getText().toString();

                //checking whether the fields are empty or not
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignInPage.this,"Email is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignInPage.this,"Password is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(password.length()<6){

                    Toast.makeText(SignInPage.this,"Password must be >=6 Characters",Toast.LENGTH_LONG).show();

                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignInPage.this,"User Created",Toast.LENGTH_LONG).show();
                            Intent toLogin=new Intent(SignInPage.this,SelectorPage.class);
                            toLogin.putExtra("nameOfUser","SignPage"+name);
                            startActivity(toLogin);
                            finish();

                        }
                        else{

                            Toast.makeText(SignInPage.this,"An Error ! " + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });



            }
        });

    }

    public void tologin(View view) {

        startActivity(new Intent(getApplicationContext(),LoginPage.class));
        finish();

    }




}