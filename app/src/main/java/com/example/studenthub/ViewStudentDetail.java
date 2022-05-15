package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentDetail extends AppCompatActivity {

    private String userEmail,userID;
    private Button BtnStdViewBack;
    private TextView NameTV, UniversityTV,FacultyTV, FieldTV, YearTV, SemesterTV, LastCumGPATV, AdminStatusTV, EmailTV,RegNumTV;
    private ProgressBar progressBarStdview;
    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String cumGPAStr;
    private String nameStr;
    private String regNumStr;
    private String AdminStatusStr;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_detail);
        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));


        BtnStdViewBack=findViewById(R.id.StdViewBackButton);






        NameTV = findViewById(R.id.TVStdViewNameValue);
        UniversityTV = findViewById(R.id.TVStdUniversityValue);
        FacultyTV = findViewById(R.id.TVStdFacultyValue);
        FieldTV = findViewById(R.id.TVStdFieldValue);
        YearTV = findViewById(R.id.TVstdYearValue);
        SemesterTV = findViewById(R.id.TVStdSemesterValue);
        LastCumGPATV = findViewById(R.id.TVStdGPAValue);
        AdminStatusTV = findViewById(R.id.TVStdAdminStatusValue);
        EmailTV = findViewById(R.id.TVStdEmailViewValue);
        RegNumTV = findViewById(R.id.TVStdRegNumValue);

        progressBarStdview = findViewById(R.id.proStdViewPage);


        //get the email of the current user and get the userID by splitting the
        //email by @
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            userID = userEmail.substring(0, userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
            finish();

        }

        progressBarStdview.setVisibility(View.INVISIBLE);

        DatabaseReference refStdView = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
        refStdView.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    if (child.getKey().equals("RegNum")) {
                        regNumStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Name")) {
                        nameStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("University")) {
                        uniStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Faculty")) {
                        facStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Field")) {
                        fieldStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Year")) {
                        yearStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Semester")) {
                        semStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("LastCumGPA")) {
                        cumGPAStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Admin")) {
                        AdminStatusStr = child.getValue().toString();
                    }


                    try {
                        NameTV.setText(nameStr);
                        UniversityTV.setText(uniStr);
                        FacultyTV.setText(facStr);
                        FieldTV.setText(fieldStr);
                        YearTV.setText(yearStr);
                        SemesterTV.setText(semStr);
                        LastCumGPATV.setText(cumGPAStr);
                        AdminStatusTV.setText(AdminStatusStr);
                        EmailTV.setText(userEmail);
                        RegNumTV.setText(regNumStr);



                        progressBarStdview.setVisibility(View.INVISIBLE);

                    } catch (NullPointerException e) {
                        Toast.makeText(ViewStudentDetail.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        BtnStdViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),StudentArea.class));
                finish();


            }
        });




    }
}