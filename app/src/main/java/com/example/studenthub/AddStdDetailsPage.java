package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStdDetailsPage extends AppCompatActivity {

    private EditText uni,fac,field,year,sem,cumGPA,name,regNum;
    private Button addDetails,toDashBoardBTn;
    private String userEmail,userID;
    private ProgressBar proBarAddStd;

    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String cumGPAStr;
    private String nameStr;
    private String regNumStr;


    private DatabaseReference mdatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_std_details_page);


        proBarAddStd=findViewById(R.id.pbStdAddPage);

        uni=findViewById(R.id.ETUniversity);
        fac=findViewById(R.id.ETFaculty);
        field=findViewById(R.id.ETField);
        year=findViewById(R.id.ETYear);
        sem=findViewById(R.id.ETSemester);
        cumGPA=findViewById(R.id.ETLastCumGPA);
        addDetails=findViewById(R.id.ServerAdd);
        name=findViewById(R.id.ETAddStdNameOfStd);
        regNum=findViewById(R.id.ETRegNum);
        toDashBoardBTn=findViewById(R.id.FromStdDetailstoStddash);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                userEmail = user.getEmail();
                userID=userEmail.substring(0,userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
            finish();

        }





        //This will give the reference to the root in the database
        mdatabaseRef= FirebaseDatabase.getInstance().getReference();

        //when the add button is clicked this method will be called
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the string inputted by the user
                //using the getText methods
                 uniStr=uni.getText().toString();
                 facStr=fac.getText().toString();
                 fieldStr=field.getText().toString();
                 yearStr=year.getText().toString();
                 semStr=sem.getText().toString();
                 cumGPAStr=cumGPA.getText().toString();
                 nameStr=name.getText().toString();
                 regNumStr=regNum.getText().toString();

                //checking whether the fields are empty or not
                if(TextUtils.isEmpty(uniStr)){
                    Toast.makeText(AddStdDetailsPage.this,"University is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(facStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Faculty is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(fieldStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Field is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(yearStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Year is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(semStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Current Semester is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(cumGPAStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Last Cumulative GPA is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(nameStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Student name  is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(regNumStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Student registration number  is required",Toast.LENGTH_LONG).show();

                    return;
                }

                else{

                    proBarAddStd.setVisibility(View.VISIBLE);

                    //now we are going to add details

                    mdatabaseRef.child("Student").child(userID).child("University").setValue(uniStr);
                    mdatabaseRef.child("Student").child(userID).child("Faculty").setValue(facStr);
                    mdatabaseRef.child("Student").child(userID).child("Field").setValue(fieldStr);
                    mdatabaseRef.child("Student").child(userID).child("Name").setValue(nameStr);
                    mdatabaseRef.child("Student").child(userID).child("Year").setValue(yearStr);
                    mdatabaseRef.child("Student").child(userID).child("RegNum").setValue(regNumStr);
                    mdatabaseRef.child("Student").child(userID).child("LastCumGPA").setValue(cumGPAStr);
                    mdatabaseRef.child("Student").child(userID).child("Semester").setValue(semStr);
                    mdatabaseRef.child("Student").child(userID).child("Admin").setValue("0");

                    Toast.makeText(AddStdDetailsPage.this,"Details added successfully",Toast.LENGTH_LONG).show();

                }


            }
        });

        toDashBoardBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the addition is finished we can go back to std Dashboard
                startActivity(new Intent(getApplicationContext(),StudentArea.class));
                finish();


            }
        });


    }
}