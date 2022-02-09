package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginPage extends AppCompatActivity {


    //Declaring the private static variables
    private EditText mEmail,mPassword;
    private Button mLogbtn;
    private TextView mSigninBtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;

    //if the user clicks on the text representing he doesn't have a account this method is evoked
    //the user will be directed to the sign in page



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //linking the components of the xml page to the respective static variables
        mEmail=findViewById(R.id.ETEmailin);
        mPassword=findViewById(R.id.ETPasswordin);
        mLogbtn=findViewById(R.id.BtnLog);
        progressBar=findViewById(R.id.progressBar2);

        //getting the current instance of the database
        fAuth= FirebaseAuth.getInstance();

        mLogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we need to convert the data to string using toString
                //because it would be a object initially then we will trim
                //it for formatting
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                //checking whether the fields are empty or not
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginPage.this,"Email is required",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginPage.this,"Password is required",Toast.LENGTH_LONG).show();
                    return;
                }

                /*
                if(password.length()<6){
                    mPassword.setError("Password must be >=6 Characters");
                    return;
                }

                 */

                //if above criteria are fulfilled displaying the progress bar to the
                //user indicating that the authenticating process has started
                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //if successful the authentication the progress bar disappears
                            progressBar.setVisibility(View.INVISIBLE);

                            //diplaying a toast message indicating the user has succesfully logged in
                            Toast.makeText(LoginPage.this,"Logged in Successfully",Toast.LENGTH_LONG).show();

                            //creating an instance of the Intent to move to Selector page from
                            //this context while carrying the email entered by the user
                            Intent toSelector=new Intent(LoginPage.this,SelectorPage.class);
                            toSelector.putExtra("nameOfUser","loginPage"+email);
                            startActivity(toSelector);

                            //closing this activity
                            finish();
                        }
                        else{
                            Toast.makeText(LoginPage.this,"An Error ! " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });






    }


    public void toSignin(View view) {

        Intent movSign=new Intent(LoginPage.this,SignInPage.class);
        startActivity(movSign);
    }
}