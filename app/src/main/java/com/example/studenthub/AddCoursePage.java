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

import java.util.HashMap;

public class AddCoursePage extends AppCompatActivity {


    private Button BtnMore,BtnFinish;
    private EditText CourseID, CourseCredit, CourseName, CourseSem, AssignmentMarks, LabMarks, ProjectMarks, MidMarks, EndMarks;
    private ProgressBar progressBarCAdd;
    private DatabaseReference mdatabaseRef;
    private String userEmail, userID;

    private String CourseIDStr, CourseNameStr, CourseCreditStr, AsignmentMarkStr, SemesterStr, MidMarkStr, LabMarkStr, EndMarkStr, ProjectMarkStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_page);


        BtnMore = findViewById(R.id.AddMoreBtn);
        BtnFinish=findViewById(R.id.FinishBtn);



        CourseID = findViewById(R.id.ETxtCourseID);
        CourseCredit = findViewById(R.id.ETxtCourseCredit);
        CourseName = findViewById(R.id.ETxtCourseName);
        CourseSem = findViewById(R.id.ETSemesterVal);
        AssignmentMarks = findViewById(R.id.ETAssignMarks);
        LabMarks = findViewById(R.id.ETLabMarks);
        ProjectMarks = findViewById(R.id.ETProjectMarks);
        MidMarks = findViewById(R.id.ETMidMarks);
        EndMarks = findViewById(R.id.ETEndMarks);

        progressBarCAdd = findViewById(R.id.proCourseViewPage);

        progressBarCAdd.setVisibility(View.INVISIBLE);

        //This will give the reference to the root in the database
        mdatabaseRef = FirebaseDatabase.getInstance().getReference();




        FirebaseUser Currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (Currentuser != null) {
            userEmail = Currentuser.getEmail();
            userID = userEmail.substring(0, userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
            finish();

        }

        BtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //getting the string inputted by the user
                //using the getText methods
                CourseIDStr=CourseID.getText().toString().trim();
                CourseNameStr=CourseName.getText().toString().trim();
                CourseCreditStr=CourseCredit.getText().toString().trim();
                SemesterStr=CourseSem.getText().toString().trim();
                AsignmentMarkStr=AssignmentMarks.getText().toString().trim();
                LabMarkStr=LabMarks.getText().toString().trim();
                ProjectMarkStr=ProjectMarks.getText().toString().trim();
                MidMarkStr=MidMarks.getText().toString().trim();
                EndMarkStr=EndMarks.getText().toString();




                //checking whether the fields are empty or not
                if(TextUtils.isEmpty(CourseIDStr)){
                    Toast.makeText(AddCoursePage.this,"Course ID is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(CourseNameStr)){
                    Toast.makeText(AddCoursePage.this,"Course Name is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(CourseCreditStr)){
                    Toast.makeText(AddCoursePage.this,"Course Credit is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(AsignmentMarkStr)){
                    Toast.makeText(AddCoursePage.this,"Assignment marks required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(SemesterStr)) {
                    Toast.makeText(AddCoursePage.this, "Semester is required", Toast.LENGTH_LONG).show();

                    return;
                }

                else if(TextUtils.isEmpty(LabMarkStr)) {
                    Toast.makeText(AddCoursePage.this, "Lab marks required", Toast.LENGTH_LONG).show();

                    return;
                }

                else if(TextUtils.isEmpty(ProjectMarkStr)) {
                    Toast.makeText(AddCoursePage.this, "Project marks required", Toast.LENGTH_LONG).show();

                    return;
                }

                else if(TextUtils.isEmpty(MidMarkStr)) {
                    Toast.makeText(AddCoursePage.this, "Mid marks required", Toast.LENGTH_LONG).show();

                    return;
                }

                else if(TextUtils.isEmpty(EndMarkStr)) {
                    Toast.makeText(AddCoursePage.this, "End marks required", Toast.LENGTH_LONG).show();

                    return;
                }
                else{



                    HashMap<String,String> course = new HashMap<>();
                    course.put("Name",CourseNameStr);
                    course.put("Credits",CourseCreditStr);
                    course.put("Semester",SemesterStr);
                    course.put("AssignmentMarks",AsignmentMarkStr);
                    course.put("LabMarks",LabMarkStr);
                    course.put("ProjectMarks",ProjectMarkStr);
                    course.put("MidMarks",MidMarkStr);
                    course.put("EndMarks",EndMarkStr);

                    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

                    mdatabaseRef.child("Admin").child(userID).child("Courses").child(CourseIDStr).setValue(course);


                    Toast.makeText(AddCoursePage.this,CourseIDStr+" details added successfully",Toast.LENGTH_LONG).show();


                    CourseID.setText("");
                    CourseCredit.setText("");
                    CourseName.setText("");
                    CourseSem.setText("");
                    AssignmentMarks.setText("");
                    LabMarks.setText("");
                    ProjectMarks.setText("");
                    MidMarks.setText("");
                    EndMarks.setText("");


                }


            }
        });



        BtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AdminArea.class));

            }
        });
    }
}
