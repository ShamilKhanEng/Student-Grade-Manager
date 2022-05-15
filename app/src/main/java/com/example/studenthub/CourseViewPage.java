package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourseViewPage extends AppCompatActivity {

    private String userEmail,userID;
    private int courseCount=0;
    private int flag=0;
    private Spinner spinner;
    private Button BtnAdminViewSearch;
    private Button BtnBack;
    private TextView VCourseID, VCourseCredit, VCourseName, VCourseSem, VAssignmentMarks, VLabMarks, VProjectMarks, VMidMarks, VEndMarks;
    private ProgressBar progressBarCview;
    private String[] CoursesList;
    ArrayList<String> listitems;
    private String CourseNameStr="";
    private String CourseSemesterStr="";
    private String CourseCreditStr="";
    private String CourseAssignMarksStr="";
    private String CourseLabMarksStr="";
    private String CourseMidMarksStr="";
    private String CourseEndMarksStr="";
    private String CourseProjectStr="";
    private String result;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_page);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        List<String> CoursesList = new ArrayList<>();
        spinner=findViewById(R.id.CourseSpinnerView);
        BtnBack=findViewById(R.id.CourseViewBackButton);
        //VCourseID = findViewById(R.id.ETxtCourseID);
        VCourseCredit = findViewById(R.id.TVCoCreditsValue);
        VCourseName = findViewById(R.id.TVCoViewNameValue);
        VCourseSem = findViewById(R.id.TVCoSemesterValue);
        VAssignmentMarks = findViewById(R.id.TVCoAssignMarksValue);
        VLabMarks = findViewById(R.id.TVCoLabMarksValue);
        VProjectMarks = findViewById(R.id.TVCoProjectMarksValue);
        VMidMarks = findViewById(R.id.TVCoMidMarksValue);
        VEndMarks = findViewById(R.id.TVCoEndMarksValue);

        progressBarCview = findViewById(R.id.proCourseViewPage);


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

        progressBarCview.setVisibility(View.INVISIBLE);

        DatabaseReference refAdmin = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses");

        refAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                listitems=new ArrayList<String>();
                listitems.add("");
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    listitems.add(userkey); //add result into array list
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseViewPage.this, android.R.layout.simple_spinner_item,listitems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                progressBarCview.setVisibility(View.VISIBLE);


                flag=1;
                result = spinner.getSelectedItem().toString().trim();






                DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses").child(result);

                ref4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {


                            if (child.getKey().equals("Name")) {
                                CourseNameStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("Semester")) {
                                CourseSemesterStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("Credits")) {
                                CourseCreditStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("AssignmentMarks")) {
                                CourseAssignMarksStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("LabMarks")) {
                                CourseLabMarksStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("MidMarks")) {
                                CourseMidMarksStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("EndMarks")) {
                                CourseEndMarksStr = child.getValue().toString();
                            }
                            if (child.getKey().equals("ProjectMarks")) {
                                CourseProjectStr = child.getValue().toString();
                            }


                            try {
                                VCourseCredit.setText(CourseCreditStr);
                                VCourseName.setText(CourseNameStr);
                                VCourseSem.setText(CourseSemesterStr);
                                VAssignmentMarks.setText(CourseAssignMarksStr);
                                VLabMarks.setText(CourseLabMarksStr);
                                VProjectMarks.setText(CourseProjectStr);
                                VMidMarks.setText(CourseMidMarksStr);
                                VEndMarks.setText(CourseEndMarksStr);
                                progressBarCview.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                                Toast.makeText(CourseViewPage.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

                            }


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),AdminArea.class));
                finish();


            }
        });





    }
}