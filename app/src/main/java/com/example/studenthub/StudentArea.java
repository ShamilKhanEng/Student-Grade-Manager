package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentArea extends AppCompatActivity implements View.OnClickListener  {


    private TextView dispMsg;
    private ImageButton gradCal, adminDetails, stdDetails,Adddetails,RemoveDetailsBtn;
    private Button logOutDashBtn,toStudentZoneBtn;
    private String emailFromlog;
    private String emailFromSign;
    private String nameFromSel;
    private String userID,userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        //linking the variables in the java script with the components in the xml script
        dispMsg = findViewById(R.id.TVstudentdash);
        adminDetails = findViewById(R.id.AdminDetailsBtn);
        stdDetails = findViewById(R.id.StdDashViewBtn);
        Adddetails = findViewById(R.id.AddDetailsBtn);
        logOutDashBtn = findViewById(R.id.DashlogoutBtn);
        gradCal=findViewById(R.id.GradCalBtn);
        toStudentZoneBtn=findViewById(R.id.toStdZoneBtn);
        RemoveDetailsBtn=findViewById(R.id.StdDashBoardDeleteBtn);


        adminDetails.setOnClickListener(this);
        stdDetails.setOnClickListener(this);
        Adddetails.setOnClickListener(this);
        logOutDashBtn.setOnClickListener(this);
        gradCal.setOnClickListener(this);
        toStudentZoneBtn.setOnClickListener(this);
        RemoveDetailsBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View vStdArea) {

        if (vStdArea.getId() == R.id.GradCalBtn) {

            Intent toAStd = new Intent(StudentArea.this, GradePage.class);
            startActivity(toAStd);
            finish();
        } else if (vStdArea.getId() == R.id.AdminDetailsBtn) {


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

            DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String status=snapshot.child("Student").child(userID).child("Admin").getValue(String.class);

                    if(status.equals("1")){


                        startActivity(new Intent(getApplicationContext(),AdminArea.class));

                    }else{

                        startActivity(new Intent(getApplicationContext(),AdminRegPage.class));


                    }
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        } else if (vStdArea.getId() == R.id.StdDashViewBtn) {
            Intent toStdView = new Intent(StudentArea.this,ViewStudentDetail.class );
            startActivity(toStdView);
            finish();
        }

       else if (vStdArea.getId() == R.id.AddDetailsBtn) {
        Intent toStdAddDetails = new Intent(StudentArea.this,AddStdDetailsPage.class );
        startActivity(toStdAddDetails);
        finish();
       }

        else if (vStdArea.getId() == R.id.toStdZoneBtn) {
            Intent toStdZone = new Intent(StudentArea.this,SelectorPage.class);
            startActivity(toStdZone);
            finish();
        }



        else if (vStdArea.getId() == R.id.DashlogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(StudentArea.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(vStdArea.getId() == R.id.StdDashBoardDeleteBtn){

            startActivity(new Intent(getApplicationContext(), StudentCourseRemovePage.class));
            finish();


        }



    }
}