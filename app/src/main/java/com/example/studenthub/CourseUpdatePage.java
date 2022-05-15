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

public class CourseUpdatePage extends AppCompatActivity {

    private String userEmail,userID;
    private int flag=0;
    private Spinner spinnerUpdatePage;
    private Button BtnBackUpdatePage,UpadtePageUpadateBtn;
    private EditText UCourseCredit, UCourseName, UCourseSem, UAssignmentMarks, ULabMarks, UProjectMarks, UMidMarks, UEndMarks;
    private ProgressBar progressBarCUpdate;
    private StringBuilder updatedDetails=new StringBuilder();
    ArrayList<String> listUpdateitems;
    private String CourseNameStr="";
    private String CourseSemesterStr="";
    private String CourseCreditStr="";
    private String CourseAssignMarksStr="";
    private String CourseLabMarksStr="";
    private String CourseMidMarksStr="";
    private String CourseEndMarksStr="";
    private String CourseProjectStr="";
    private String result;
    private CheckBox CoNameCheck;
    private CheckBox CoSemesterCheck;
    private CheckBox CoCreditCheck;
    private CheckBox CoAssignMarksCheck;
    private CheckBox CoLabMarksCheck;
    private CheckBox CoMidMarksCheck;
    private CheckBox CoEndMarksCheck;
    private CheckBox CoProjectCheck;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update_page);
        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        //linking the spinner
        spinnerUpdatePage=findViewById(R.id.CourseSpinnerUpdate);



        //linking the buttons
        BtnBackUpdatePage=findViewById(R.id.CourseUpdateBack);
        UpadtePageUpadateBtn=findViewById(R.id.CourseUpdateButton);


        //linking the edit texts

        UCourseCredit = findViewById(R.id.ETCoUpdateCreditsValue);
        UCourseName = findViewById(R.id.ETCoUpdateNameValue);
        UCourseSem = findViewById(R.id.ETCoUpdateSemesterValue);
        UAssignmentMarks = findViewById(R.id.ETCoUpdateAssignMarksValue);
        ULabMarks = findViewById(R.id.ETCoUpdateLabMarksValue);
        UProjectMarks = findViewById(R.id.ETCoUpdateProjectMarksValue);
        UMidMarks = findViewById(R.id.ETCoUpdateMidMarksValue);
        UEndMarks = findViewById(R.id.ETCoUpdateEndMarksValue);

        //linking the progress bar
        progressBarCUpdate = findViewById(R.id.progBarCoUpdatePage);

        //linking the checkboxes

        CoCreditCheck = findViewById(R.id.CourseCreditsCheck);
        CoNameCheck = findViewById(R.id.CourseNameCheck);
        CoSemesterCheck = findViewById(R.id.CourseSemesterCheck);
        CoAssignMarksCheck = findViewById(R.id.CourseAssignMarksCheck);
        CoLabMarksCheck = findViewById(R.id.CourseLabMarksCheck);
        CoProjectCheck = findViewById(R.id.CourseProjectMarksCheck);
        CoMidMarksCheck = findViewById(R.id.CourseMidMarksCheck);
        CoEndMarksCheck = findViewById(R.id.CourseEndMarksCheck);



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

        progressBarCUpdate.setVisibility(View.INVISIBLE);

        DatabaseReference refAdmin = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses");

        refAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                listUpdateitems=new ArrayList<String>();
                listUpdateitems.add("");
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    listUpdateitems.add(userkey); //add result into array list
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseUpdatePage.this, android.R.layout.simple_spinner_item,listUpdateitems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUpdatePage.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        spinnerUpdatePage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                progressBarCUpdate.setVisibility(View.VISIBLE);



                result = spinnerUpdatePage.getSelectedItem().toString().trim();




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
                                UCourseCredit.setHint(CourseCreditStr);
                                UCourseName.setHint(CourseNameStr);
                                UCourseSem.setHint(CourseSemesterStr);
                                UAssignmentMarks.setHint(CourseAssignMarksStr);
                                ULabMarks.setHint(CourseLabMarksStr);
                                UProjectMarks.setHint(CourseProjectStr);
                                UMidMarks.setHint(CourseMidMarksStr);
                                UEndMarks.setHint(CourseEndMarksStr);
                                progressBarCUpdate.setVisibility(View.INVISIBLE);

                            } catch (NullPointerException e) {
                                Toast.makeText(CourseUpdatePage.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

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




        UpadtePageUpadateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference refUpdate = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses").child(result);
                //what the isChecked does is that it the user clicked
                //that checkbox it return true

                updatedDetails.append("The entities updated are ");

                if(CoCreditCheck.isChecked()){

                    flag=1;
                    refUpdate.child("Credits").setValue(UCourseCredit.getText().toString().trim());
                    updatedDetails.append("Credits");
                    updatedDetails.append(",");


                }
                if(CoNameCheck.isChecked()){
                    flag=1;
                    refUpdate.child("Name").setValue(UCourseName.getText().toString().trim());
                    updatedDetails.append("Name");
                    updatedDetails.append(",");

                }
                if(CoSemesterCheck.isChecked()){
                    flag=1;
                    refUpdate.child("Semester").setValue(UCourseSem.getText().toString().trim());
                    updatedDetails.append("Semester");
                    updatedDetails.append(",");

                }
                if(CoAssignMarksCheck.isChecked()){
                    flag=1;
                    refUpdate.child("AssignmentMarks").setValue(UAssignmentMarks.getText().toString().trim());
                    updatedDetails.append("AssignmentMarks");
                    updatedDetails.append(",");
                }
                if(CoLabMarksCheck.isChecked()){
                    flag=1;
                    refUpdate.child("LabMarks").setValue(ULabMarks.getText().toString().trim());
                    updatedDetails.append("LabMarks");
                    updatedDetails.append(",");
                }
                if(CoProjectCheck.isChecked()){
                    flag=1;
                    refUpdate.child("ProjectMarks").setValue(UProjectMarks.getText().toString().trim());
                    updatedDetails.append("ProjectMarks");
                    updatedDetails.append(",");

                }

                if(CoEndMarksCheck.isChecked()){
                    flag=1;
                    refUpdate.child("EndMarks").setValue(UEndMarks.getText().toString().trim());
                    updatedDetails.append("EndMarks");
                    updatedDetails.append(",");
                }

                if(CoMidMarksCheck.isChecked()){
                    flag=1;
                    refUpdate.child("MidMarks").setValue(UMidMarks.getText().toString().trim());
                    updatedDetails.append("MidMarks");
                    updatedDetails.append(",");
                }

                if(flag==0){
                    Toast.makeText(CourseUpdatePage.this, "None of the checkboxes were checked,No update will happen!", Toast.LENGTH_LONG).show();
                }

                //to remove the last comma that will be appended
                updatedDetails.setLength(updatedDetails.length()-1);
                Toast.makeText(CourseUpdatePage.this,updatedDetails.toString(), Toast.LENGTH_LONG).show();


            }
        });



        BtnBackUpdatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),AdminArea.class));
                finish();


            }
        });


    }
}