package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class StudentCourseRemovePage extends AppCompatActivity {


    private String userEmail,userID;
    private Spinner StdCourseRemoveSpCourses,StdCourseRemoveSpPrevCourse;
    private Button BtnBackStdCourseRemovePage,StdCourseRemoveBtn;
    private ArrayList<String> listDelCourse,listDelPrevCourse;

    private String resultFromSpinner;
    private ProgressBar progressBarStdDelCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_remove_page);

        //linking the spinner
        StdCourseRemoveSpCourses=findViewById(R.id.StdCourseSpCourse);
        StdCourseRemoveSpPrevCourse=findViewById(R.id.StdCourseSpPrevCourse);



        //linking the buttons
        StdCourseRemoveBtn=findViewById(R.id.StdCourseDelDeleteButton);
        BtnBackStdCourseRemovePage=findViewById(R.id.StdCourseDelBackButton);

        //linking the progress bar
        progressBarStdDelCourse = findViewById(R.id.proStdCourseDel);

        progressBarStdDelCourse.setVisibility(View.INVISIBLE);

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

        listDelCourse=new ArrayList<String>();
        listDelCourse.add("");
        listDelPrevCourse=new ArrayList<String>();
        listDelPrevCourse.add("");

        DatabaseReference refStdCourses = FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("Courses");

        refStdCourses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    listDelCourse.add(userkey); //add result into array list
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentCourseRemovePage.this, android.R.layout.simple_spinner_item,listDelCourse);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                StdCourseRemoveSpCourses.setAdapter(adapter);


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
                    listDelPrevCourse.add(userkey); //add result into array list
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentCourseRemovePage.this, android.R.layout.simple_spinner_item,listDelPrevCourse);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                StdCourseRemoveSpPrevCourse.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        StdCourseRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the user selected item from the drop down menu
              String resultCourse = StdCourseRemoveSpCourses.getSelectedItem().toString().trim();
              String resultPrevCourse = StdCourseRemoveSpCourses.getSelectedItem().toString().trim();

              if(!TextUtils.isEmpty(resultCourse)){
                  //removing the relevant Course from database
                  DatabaseReference removeRef=FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("Courses").child(resultCourse);
                  removeRef.setValue(null);
                  Toast.makeText(StudentCourseRemovePage.this,resultCourse +" has been successfully removed!", Toast.LENGTH_SHORT).show();


              }
                if(!TextUtils.isEmpty(resultPrevCourse)){
                    //removing the relevant Course from database
                    DatabaseReference removeRef=FirebaseDatabase.getInstance().getReference().child("Student").child(userID).child("PrevCourses").child(resultCourse);
                    removeRef.setValue(null);
                    Toast.makeText(StudentCourseRemovePage.this,resultPrevCourse +" has been successfully removed!", Toast.LENGTH_SHORT).show();


                }







            }
        });

        BtnBackStdCourseRemovePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(), StudentArea.class));
                finish();

            }
        });





    }
}