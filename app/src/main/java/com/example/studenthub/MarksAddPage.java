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

public class MarksAddPage extends AppCompatActivity {

    private String userEmail,userID;
    private int flag=0;
    private Spinner spinnerMarksAddPage;
    private Button BtnBackMarksAddPage, GAddPageAddBtn;
    private EditText GAddCourseID, GAddCourseName, GAddCourseSem, GAddAssignmentMarks, GAddLabMarks, GAddProjectMarks, GAddMidMarks, GAddEndMarks,GAddGPA,GAddGRADE;
    private ProgressBar progressBarCGAdd;
    private StringBuilder updatedDetails=new StringBuilder();
    private ArrayList<String> listGradeAdditems;
    private String CourseNameStr="";
    private String CourseSemesterStr="";
    private String CourseAssignMarksStr="";
    private String CourseLabMarksStr="";
    private String CourseMidMarksStr="";
    private String CourseEndMarksStr="";
    private String CourseProjectStr="";
    private String resultFromSpinner;
    private CheckBox CoGAddNameCheck;
    private CheckBox CoGAddSemesterCheck;
    private CheckBox CoGAddAssignMarksCheck;
    private CheckBox CoGAddLabMarksCheck;
    private CheckBox CoGAddMidMarksCheck;
    private CheckBox CoGAddEndMarksCheck;
    private CheckBox CoGAddProjectCheck;
    private CheckBox CoGAddIDCheck;
    private CheckBox CoGAddGPACheck;
    private CheckBox CoGAddGRADECheck;

    private String uniVar,facVar,fieldVar,yearVar,semVar,AdminuserID;
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
        setContentView(R.layout.activity_marks_add_page);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        //linking the spinner
        spinnerMarksAddPage=findViewById(R.id.CourseSpinnerGradeAdd);



        //linking the buttons
        BtnBackMarksAddPage=findViewById(R.id.CourseGradeAddBack);
        GAddPageAddBtn=findViewById(R.id.CourseGradeAddButton);


        //linking the edit texts

        GAddCourseID = findViewById(R.id.ETCoGradeAddCourseIDValue);
        GAddCourseName=findViewById(R.id.ETCoGradeAddNameValue);
        GAddCourseSem = findViewById(R.id.ETCoGradeAddSemesterValue);
        GAddAssignmentMarks = findViewById(R.id.ETCoGradeAddAssignMarksValue);
        GAddLabMarks = findViewById(R.id.ETCoGradeAddLabMarksValue);
        GAddProjectMarks = findViewById(R.id.ETCoGradeAddProjectMarksValue);
        GAddMidMarks = findViewById(R.id.ETCoGradeAddMidMarksValue);
        GAddEndMarks = findViewById(R.id.ETCoGradeAddEndMarksValue);
        GAddGPA = findViewById(R.id.ETCoGradeAddGPAValue);
        GAddGRADE = findViewById(R.id.ETCoGradeAddGRADEValue);

        //linking the progress bar
        progressBarCGAdd = findViewById(R.id.progBarCoGradeAddPage);

        //linking the checkboxes
        CoGAddIDCheck = findViewById(R.id.CourseGradeAddCourseIDCheck);
        CoGAddNameCheck = findViewById(R.id.CourseGradeAddNameCheck);
        CoGAddSemesterCheck = findViewById(R.id.CourseGradeAddSemesterCheck);
        CoGAddAssignMarksCheck = findViewById(R.id.CourseGradeAddAssignMarksCheck);
        CoGAddLabMarksCheck = findViewById(R.id.CourseGradeAddLabMarksCheck);
        CoGAddProjectCheck = findViewById(R.id.CourseGradeAddProjectMarksCheck);
        CoGAddMidMarksCheck = findViewById(R.id.CourseGradeAddMidMarksCheck);
        CoGAddEndMarksCheck = findViewById(R.id.CourseGradeAddEndMarksCheck);
        CoGAddGPACheck = findViewById(R.id.CourseGradeAddGPACheck);
        CoGAddGRADECheck = findViewById(R.id.CourseGradeAddGRADECheck);



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

        progressBarCGAdd.setVisibility(View.INVISIBLE);

        //get the details of the student to compare the uni,fac,sem,field to get the courses under this sem for the user

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




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



          //comparing the obtain details with admin details to find the admin userid
        FirebaseDatabase.getInstance().getReference().child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           uniVar = snapshot.child("University").getValue().toString();
                           facVar = snapshot.child("Faculty").getValue().toString();
                           fieldVar = snapshot.child("Field").getValue().toString();
                            yearVar = snapshot.child("Year").getValue().toString();
                            semVar = snapshot.child("Semester").getValue().toString();



                            if(uniVar.equals(uniStr) && facVar.equals(facStr) && fieldVar.equals(fieldStr) && yearVar.equals(yearStr) && semVar.equals(semStr)){

                                    AdminuserID= snapshot.getKey().toString().trim();

                                DatabaseReference refAdmin = FirebaseDatabase.getInstance().getReference().child("Admin").child(AdminuserID).child("Courses");

                                refAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                                        listGradeAdditems=new ArrayList<String>();
                                        listGradeAdditems.add("");
                                        listGradeAdditems.add("new");
                                        for (DataSnapshot dsp : snapshot.getChildren()) {
                                            String userkey  = dsp.getKey();
                                            listGradeAdditems.add(userkey); //add result into array list
                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MarksAddPage.this, android.R.layout.simple_spinner_item,listGradeAdditems);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinnerMarksAddPage.setAdapter(adapter);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });





                            }




                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });









        spinnerMarksAddPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                progressBarCGAdd.setVisibility(View.VISIBLE);



                resultFromSpinner = spinnerMarksAddPage.getSelectedItem().toString().trim();




                DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Admin").child(AdminuserID).child("Courses").child(resultFromSpinner);

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
                                GAddCourseName.setHint(CourseNameStr);
                                GAddCourseSem.setHint(CourseSemesterStr);
                                GAddAssignmentMarks.setHint(CourseAssignMarksStr);
                                GAddLabMarks.setHint(CourseLabMarksStr);
                                GAddProjectMarks.setHint(CourseProjectStr);
                                GAddMidMarks.setHint(CourseMidMarksStr);
                                GAddEndMarks.setHint(CourseEndMarksStr);
                                progressBarCGAdd.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                                Toast.makeText(MarksAddPage.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

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
                //On nothing selected
            }

        });




        GAddPageAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reftoAddExistingCourse = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("Courses").child(resultFromSpinner);
                DatabaseReference reftoAddNonExistingCourse = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("PrevCourses");





                if(resultFromSpinner.equals("new")){

                    if(!CoGAddIDCheck.isChecked()){

                        Toast.makeText(MarksAddPage.this, "Enter a valid CourseId ", Toast.LENGTH_SHORT).show();
                        return;



                    }



                    if(CoGAddGPACheck.isChecked()){

                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("FinalGPA").setValue(GAddGPA.getText().toString().trim());



                    }

                    if(CoGAddNameCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("Name").setValue(GAddCourseName.getText().toString().trim());

                    }
                    if(CoGAddSemesterCheck.isChecked()){
                        flag=1;
                        reftoAddExistingCourse.child(GAddCourseID.getText().toString().trim()).child("Semester").setValue(GAddCourseSem.getText().toString().trim());


                    }
                    if(CoGAddAssignMarksCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("AssignmentMarks").setValue(GAddAssignmentMarks.getText().toString().trim());
                    }
                    if(CoGAddLabMarksCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("LabMarks").setValue(GAddLabMarks.getText().toString().trim());

                    }
                    if(CoGAddProjectCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("ProjectMarks").setValue(GAddProjectMarks.getText().toString().trim());


                    }

                    if(CoGAddEndMarksCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("EndMarks").setValue(GAddEndMarks.getText().toString().trim());

                    }

                    if(CoGAddMidMarksCheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("MidMarks").setValue(GAddMidMarks.getText().toString().trim());
                    }

                    if(CoGAddGRADECheck.isChecked()){
                        flag=1;
                        reftoAddNonExistingCourse.child(GAddCourseID.getText().toString().trim()).child("FinalGrade").setValue(GAddGRADE.getText().toString().trim());
                    }

                    if(flag==0){
                        Toast.makeText(MarksAddPage.this, "None of the checkboxes were checked,No update will happen!", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(MarksAddPage.this, "Details added Successfully", Toast.LENGTH_SHORT).show();
                }


                else {

                    if (CoGAddGPACheck.isChecked()) {

                        flag = 1;
                        reftoAddExistingCourse.child("FinalGPA").setValue(GAddGPA.getText().toString().trim());


                    }

                    if (CoGAddNameCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("Name").setValue(GAddCourseName.getText().toString().trim());

                    }
                    if (CoGAddSemesterCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("Semester").setValue(GAddCourseSem.getText().toString().trim());


                    }
                    if (CoGAddAssignMarksCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("AssignmentMarks").setValue(GAddAssignmentMarks.getText().toString().trim());
                    }
                    if (CoGAddLabMarksCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("LabMarks").setValue(GAddLabMarks.getText().toString().trim());

                    }
                    if (CoGAddProjectCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("ProjectMarks").setValue(GAddProjectMarks.getText().toString().trim());


                    }

                    if (CoGAddEndMarksCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("EndMarks").setValue(GAddEndMarks.getText().toString().trim());

                    }

                    if (CoGAddMidMarksCheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("MidMarks").setValue(GAddMidMarks.getText().toString().trim());
                    }

                    if (CoGAddGRADECheck.isChecked()) {
                        flag = 1;
                        reftoAddExistingCourse.child("FinalGrade").setValue(GAddGRADE.getText().toString().trim());
                    }

                    if (flag == 0) {
                        Toast.makeText(MarksAddPage.this, "None of the checkboxes were checked,No update will happen!", Toast.LENGTH_LONG).show();
                    }


                    Toast.makeText(MarksAddPage.this, "Details added Successfully", Toast.LENGTH_SHORT).show();



                }
                //clear the edit text boxes once details are added
                GAddCourseName.setText("");
                GAddCourseSem.setText("");
                GAddAssignmentMarks.setText("");
                GAddLabMarks.setText("");
                GAddProjectMarks.setText("");
                GAddMidMarks.setText("");
                GAddEndMarks.setText("");
                GAddCourseID.setText("");
                GAddGPA.setText("");
                GAddGRADE.setText("");







                //clear the checkboxes
                CoGAddIDCheck.setChecked(false);
                CoGAddNameCheck.setChecked(false);
                CoGAddSemesterCheck.setChecked(false);
                CoGAddAssignMarksCheck.setChecked(false);
                CoGAddLabMarksCheck.setChecked(false);
                CoGAddProjectCheck.setChecked(false);
                CoGAddMidMarksCheck.setChecked(false);
                CoGAddEndMarksCheck.setChecked(false);
                CoGAddGPACheck.setChecked(false);
                CoGAddGRADECheck.setChecked(false);


            }
        });






        BtnBackMarksAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),GradePage.class));
                finish();


            }
        });


    }
}