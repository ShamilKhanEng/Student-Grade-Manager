package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ArrayListMultimap;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Multimap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankViewerPage extends AppCompatActivity {

    private Button viewRankBtn,viewRankBackBtn;
    private ProgressBar proViewRankPage;
    private TextView RankTV,SemTV,SemGPATV,StdCountTV;
    private int studentCount=0,courseCount=0;

    //variables to get and store the current user details
    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String cumGPAStr;
    private String nameStr;
    private String regNumStr;
    private String AdminStatusStr;
    private String userID,userEmail;


    private boolean listener1Completed=false;
    private boolean listener2Completed=false;
    private boolean listener3Completed=false;
    private Double CurrentUserAgGPA=0.0;






    //variables to compare the student details
    private String uniVar1,facVar1,fieldVar1,yearVar1,semVar1;

    //variables to compare the student details
    private String uniVar,facVar,fieldVar,yearVar,semVar;

    //array list conatiner to store the retrived GPA
    ArrayList<String> GPAlistOfCourses=new ArrayList<>();
    ArrayList<Double> avgGPA=new ArrayList<>();
    Multimap<String, String> myMultimap = ArrayListMultimap.create();

    //variables to do the calculation
    private double sum=0;
    private double userIDTotalGPA;
    private String userSem;
    private int flag1=0;
    private String  stdUsersem,stdUserGPA,userName;
    private int newUser=0,flag=0,StdCheckCount,expectCount,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_viewer_page);


        //linking the buttons in xml script with the java class button variable
        viewRankBtn=findViewById(R.id.BtnViewRank);
        viewRankBackBtn=findViewById(R.id.BtnAdminRegBack);

        //linking the progressBar
        proViewRankPage=findViewById(R.id.progressBarGradeViewRank);

        proViewRankPage.setVisibility(View.INVISIBLE);


        //linking the textviews
        RankTV=findViewById(R.id.TVRankValueRankView);
        SemTV=findViewById(R.id.TVSemesterValueRankView);
        SemGPATV=findViewById(R.id.TVGPAValueRankView);
        StdCountTV=findViewById(R.id.TVStudentCountValueRankView);

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


        //getting the current user details
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
        FirebaseDatabase.getInstance().getReference().child("Student")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            uniVar1 = snapshot.child("University").getValue().toString();
                            facVar1 = snapshot.child("Faculty").getValue().toString();
                            fieldVar1 = snapshot.child("Field").getValue().toString();
                            yearVar1 = snapshot.child("Year").getValue().toString();
                            semVar1 = snapshot.child("Semester").getValue().toString();


                            if (uniVar1.equals(uniStr) && facVar1.equals(facStr) && fieldVar1.equals(fieldStr) && yearVar1.equals(yearStr) && semVar1.equals(semStr)) {


                                GPAlistOfCourses = new ArrayList<String>();

                                String StduserID = snapshot.getKey().toString().trim();

                                DatabaseReference refAdmin = FirebaseDatabase.getInstance().getReference().child("Student").child(StduserID).child("Courses");

                                refAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        for (DataSnapshot dsp : snapshot.getChildren()) {
                                            String userkey = dsp.getKey();


                                            DatabaseReference reftoCo = FirebaseDatabase.getInstance().getReference().child("Student").child(StduserID).child("Courses").child(userkey);


                                            reftoCo.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    for (DataSnapshot courseChild : snapshot.getChildren()) {


                                                        if (courseChild.getKey().equals("FinalGPA")) {

                                                            stdUserGPA = courseChild.getValue().toString().trim();


                                                            GPAlistOfCourses.add(stdUserGPA);


                                                            myMultimap.put(StduserID, stdUserGPA);





                                                        }


                                                    }
                                                    listener3Completed=true;

                                                }


                                                //Toast.makeText(RankViewerPage.this, GPAlistOfCourses.size() + "", Toast.LENGTH_SHORT).show();


                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }

                                        listener2Completed=true;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                        listener1Completed=true;


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });












        //once the user click on the View Rank button ion the activity this block of codes is triggered
        viewRankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proViewRankPage.setVisibility(View.VISIBLE);

                if (listener1Completed && listener2Completed && listener3Completed ) {


                int StudentCount=0;
                int CourseCount=0;

                    for(String key:myMultimap.keySet()){
                        StudentCount++;
                        sum=0;
                        CourseCount=0;
                        for(String value:myMultimap.get(key)){
                            CourseCount++;

                            sum=sum+ Double.parseDouble(value);

                        }
                        if(key.equals(userID)){
                            CurrentUserAgGPA=sum/CourseCount;
                        }

                        avgGPA.add(sum/CourseCount);


                    }

                    // Sorting ArrayList in descending Order
                    // using Collection.sort() method
                    // by passing Collections.reverseOrder() as comparator
                    Collections.sort(avgGPA, Collections.reverseOrder());

                    // using indexOf() to find index of CurrentUserAgGPA
                    position =avgGPA.indexOf(CurrentUserAgGPA)+1;
                    //Toast.makeText(RankViewerPage.this,position+"", Toast.LENGTH_SHORT).show();


                    //setting the rank
                    RankTV.setText(position+"");
                    SemTV.setText(semStr);
                    SemGPATV.setText(CurrentUserAgGPA+"");
                    StdCountTV.setText(StudentCount+"");



                    proViewRankPage.setVisibility(View.INVISIBLE);




                }


            }




        });




        viewRankBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),GradePage.class));
                finish();


            }
        });



    }
}