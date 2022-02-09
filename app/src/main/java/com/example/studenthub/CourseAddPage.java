package com.example.studenthub;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseAddPage extends AppCompatActivity implements View.OnClickListener {

    private int i=0;
    private Button BtnMore,BtnEnd;
    private TextView Count;
    private EditText CourseID,CourseCredit,CourseName,CourseSem,AssignmentMarks,LabMarks,ProjectMarks,MidMarks,EndMarks;
    private StringBuilder AdminRegCourses=new StringBuilder();
    private ProgressBar progressBarCAdd;
    private DatabaseReference mdatabaseRef;
    private String userEmail,userID;

    private String CourseIDStr,CourseNameStr,CourseCreditStr,AsignmentMarkStr,SemesterStr,MidMarkStr,LabMarkStr,EndMarkStr,ProjectMarkStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add_page);


        Count=findViewById(R.id.TVCountVal);
        BtnMore=findViewById(R.id.MoreBtn);
        BtnEnd=findViewById(R.id.EndBtn);

        CourseID=findViewById(R.id.ETxtCourseID);
        CourseCredit=findViewById(R.id.ETxtCourseCredit);
        CourseName=findViewById(R.id.ETxtCourseName);
        CourseSem=findViewById(R.id.ETSemesterVal);
        AssignmentMarks=findViewById(R.id.ETAssignMarks);
        LabMarks=findViewById(R.id.ETLabMarks);
        ProjectMarks=findViewById(R.id.ETProjectMarks);
        MidMarks=findViewById(R.id.ETMidMarks);
        EndMarks=findViewById(R.id.ETEndMarks);

        progressBarCAdd=findViewById(R.id.proCourseAddPage);

        progressBarCAdd.setVisibility(View.INVISIBLE);

        //This will give the reference to the root in the database
        mdatabaseRef= FirebaseDatabase.getInstance().getReference();

        BtnMore.setOnClickListener(this);
        BtnEnd.setOnClickListener(this);






    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.MoreBtn){

            //getting the string inputted by the user
            //using the getText methods
            CourseIDStr=CourseID.getText().toString();
            CourseNameStr=CourseName.getText().toString();
            CourseCreditStr=CourseCredit.getText().toString();
            SemesterStr=CourseSem.getText().toString();
            AsignmentMarkStr=AssignmentMarks.getText().toString();
            LabMarkStr=LabMarks.getText().toString();
            ProjectMarkStr=ProjectMarks.getText().toString();
            MidMarkStr=MidMarks.getText().toString();
            EndMarkStr=EndMarks.getText().toString();

            AdminRegCourses.append(CourseIDStr);
            AdminRegCourses.append(",");


            //checking whether the fields are empty or not
            if(TextUtils.isEmpty(CourseIDStr)){
                Toast.makeText(CourseAddPage.this,"Course ID is required",Toast.LENGTH_LONG).show();

                return;
            }
            else if(TextUtils.isEmpty(CourseNameStr)){
                Toast.makeText(CourseAddPage.this,"Course Name is required",Toast.LENGTH_LONG).show();

                return;
            }
            else if(TextUtils.isEmpty(CourseCreditStr)){
                Toast.makeText(CourseAddPage.this,"Course Credit is required",Toast.LENGTH_LONG).show();

                return;
            }
            else if(TextUtils.isEmpty(AsignmentMarkStr)){
                Toast.makeText(CourseAddPage.this,"Assignment marks required",Toast.LENGTH_LONG).show();

                return;
            }
            else if(TextUtils.isEmpty(SemesterStr)) {
                Toast.makeText(CourseAddPage.this, "Semester is required", Toast.LENGTH_LONG).show();

                return;
            }

            else if(TextUtils.isEmpty(LabMarkStr)) {
                Toast.makeText(CourseAddPage.this, "Lab marks required", Toast.LENGTH_LONG).show();

                return;
            }

            else if(TextUtils.isEmpty(ProjectMarkStr)) {
                Toast.makeText(CourseAddPage.this, "Project marks required", Toast.LENGTH_LONG).show();

                return;
            }

            else if(TextUtils.isEmpty(MidMarkStr)) {
                Toast.makeText(CourseAddPage.this, "Mid marks required", Toast.LENGTH_LONG).show();

                return;
            }

            else if(TextUtils.isEmpty(EndMarkStr)) {
                Toast.makeText(CourseAddPage.this, "End marks required", Toast.LENGTH_LONG).show();

                return;
            }
            else{

                progressBarCAdd.setVisibility(View.VISIBLE);







                progressBarCAdd.setVisibility(View.INVISIBLE);

                Toast.makeText(CourseAddPage.this,"Course details added successfully",Toast.LENGTH_LONG).show();

                i++;
                CourseID.setText("");
                CourseCredit.setText("");
                CourseName.setText("");
                CourseSem.setText("");
                AssignmentMarks.setText("");
                LabMarks.setText("");
                ProjectMarks.setText("");
                MidMarks.setText("");
                EndMarks.setText("");

                Count.setText(Integer.toString(i));






            }

        }
        else if(view.getId()==R.id.EndBtn){

            //This will give the reference to the root in the database
            mdatabaseRef= FirebaseDatabase.getInstance().getReference();

            mdatabaseRef.child("Admin").child(userID).child("Courses").setValue(AdminRegCourses.toString());
            startActivity(new Intent(getApplicationContext(),AdminArea.class));
            finish();






        }

    }
}