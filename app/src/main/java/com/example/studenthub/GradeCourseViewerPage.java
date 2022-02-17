package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class GradeCourseViewerPage extends AppCompatActivity {


    private String userEmail,userID;
    private Spinner spinnerMarksViewPage;
    private Button BtnBackMarksViewPage;
    private TextView GViewCourseName, GViewCourseSem, GViewAssignmentMarks, GViewLabMarks, GViewProjectMarks, GViewMidMarks, GViewEndMarks,GViewGPA,GViewGRADE;
    private ProgressBar progressBarCGView;
    private ArrayList<String> listGradeViewitems;
    private String CourseNameStr="";
    private String CourseSemesterStr="";
    private String CourseAssignMarksStr="";
    private String CourseLabMarksStr="";
    private String CourseMidMarksStr="";
    private String CourseEndMarksStr="";
    private String CourseProjectStr="";
    private String CourseGPAStr="";
    private String CourseGRADEStr="";
    private String resultFromSpinner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_course_viewer_page);


        //linking the spinner
        spinnerMarksViewPage=findViewById(R.id.CourseSpinnerGradeView);



        //linking the buttons
        BtnBackMarksViewPage=findViewById(R.id.CourseGradeViewBack);



        //linking the edit texts


        GViewCourseName=findViewById(R.id.TVCoGradeViewNameValue);
        GViewCourseSem = findViewById(R.id.TVCoGradeViewSemesterValue);
        GViewAssignmentMarks = findViewById(R.id.TVCoGradeViewAssignMarksValue);
        GViewLabMarks = findViewById(R.id.TVCoGradeViewLabMarksValue);
        GViewProjectMarks = findViewById(R.id.TVCoGradeViewProjectMarksValue);
        GViewMidMarks = findViewById(R.id.TVCoGradeViewMidMarksValue);
        GViewEndMarks = findViewById(R.id.TVCoGradeViewEndMarksValue);
        GViewGPA = findViewById(R.id.ETCoGradeViewGPAValue);
        GViewGRADE = findViewById(R.id.TVCoGradeViewGRADEValue);

        //linking the progress bar
        progressBarCGView = findViewById(R.id.progBarCoGradeViewPage);




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

        progressBarCGView.setVisibility(View.INVISIBLE);

        listGradeViewitems=new ArrayList<String>();
        listGradeViewitems.add("");


        DatabaseReference refStdCourses = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("Courses");

        refStdCourses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    listGradeViewitems.add(userkey); //add result into array list
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference refStdPreCourses = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("PrevCourses");

        refStdPreCourses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    listGradeViewitems.add(userkey); //add result into array list
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradeCourseViewerPage.this, android.R.layout.simple_spinner_item,listGradeViewitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarksViewPage.setAdapter(adapter);



        spinnerMarksViewPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                progressBarCGView.setVisibility(View.VISIBLE);




                resultFromSpinner = spinnerMarksViewPage.getSelectedItem().toString().trim();

                CourseNameStr="";
                CourseSemesterStr="";
                CourseAssignMarksStr="";
                CourseLabMarksStr="";
                CourseMidMarksStr="";
                CourseEndMarksStr="";
                CourseProjectStr="";
                CourseGPAStr="";
                CourseGRADEStr="";




                DatabaseReference refForCourses = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("Courses").child(resultFromSpinner);

                refForCourses.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {


                            if (child.getKey().equals("Name")) {


                                CourseNameStr = child.getValue().toString().trim();




                            }
                            if (child.getKey().equals("Semester")) {
                                CourseSemesterStr = child.getValue().toString().trim();
                            }

                            if (child.getKey().equals("AssignmentMarks")) {
                                CourseAssignMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("LabMarks")) {
                                CourseLabMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("MidMarks")) {
                                CourseMidMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("EndMarks")) {
                                CourseEndMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("ProjectMarks")) {
                                CourseProjectStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("Final GPA")) {
                                CourseGPAStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("Final Grade")) {
                                CourseGRADEStr = child.getValue().toString().trim();
                            }

                            try {

                                GViewCourseName.setText(CourseNameStr);
                                GViewCourseSem.setText(CourseSemesterStr);
                                GViewAssignmentMarks.setText(CourseAssignMarksStr);
                                GViewLabMarks.setText(CourseLabMarksStr);
                                GViewProjectMarks.setText(CourseProjectStr);
                                GViewMidMarks.setText(CourseMidMarksStr);
                                GViewEndMarks.setText(CourseEndMarksStr);
                                GViewGPA.setText(CourseGPAStr);
                                GViewGRADE.setText(CourseGRADEStr);
                                progressBarCGView.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                                Toast.makeText(GradeCourseViewerPage.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

                            }



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference refForPrevCourses = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("PrevCourses").child(resultFromSpinner);

                refForPrevCourses.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot child : snapshot.getChildren()) {


                            if (child.getKey().equals("Name")) {


                                CourseNameStr = child.getValue().toString().trim();



                            }
                            if (child.getKey().equals("Semester")) {
                                CourseSemesterStr = child.getValue().toString().trim();
                            }

                            if (child.getKey().equals("AssignmentMarks")) {
                                CourseAssignMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("LabMarks")) {
                                CourseLabMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("MidMarks")) {
                                CourseMidMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("EndMarks")) {
                                CourseEndMarksStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("ProjectMarks")) {
                                CourseProjectStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("FinalGPA")) {
                                CourseGPAStr = child.getValue().toString().trim();
                            }
                            if (child.getKey().equals("FinalGrade")) {
                                CourseGRADEStr = child.getValue().toString().trim();
                            }

                            try {

                                GViewCourseName.setText(CourseNameStr);
                                GViewCourseSem.setText(CourseSemesterStr);
                                GViewAssignmentMarks.setText(CourseAssignMarksStr);
                                GViewLabMarks.setText(CourseLabMarksStr);
                                GViewProjectMarks.setText(CourseProjectStr);
                                GViewMidMarks.setText(CourseMidMarksStr);
                                GViewEndMarks.setText(CourseEndMarksStr);
                                GViewGPA.setText(CourseGPAStr);
                                GViewGRADE.setText(CourseGRADEStr);
                                progressBarCGView.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                                Toast.makeText(GradeCourseViewerPage.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

                            }



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                progressBarCGView.setVisibility(View.INVISIBLE);
            }



            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });










        BtnBackMarksViewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),GradePage.class));
                finish();


            }
        });

    }
}