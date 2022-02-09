package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StudentArea extends AppCompatActivity implements View.OnClickListener  {


    private TextView dispMsg;
    private ImageButton gradCal, adminDetails, stdDetails,Adddetails;
    private Button logOutDashBtn,toStudentZoneBtn;
    private String emailFromlog;
    private String emailFromSign;
    private String nameFromSel;
    private String email;

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


        adminDetails.setOnClickListener(this);
        stdDetails.setOnClickListener(this);
        Adddetails.setOnClickListener(this);
        logOutDashBtn.setOnClickListener(this);
        gradCal.setOnClickListener(this);
        toStudentZoneBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View vStdArea) {

        if (vStdArea.getId() == R.id.GradCalBtn) {

            Intent toAStd = new Intent(StudentArea.this, GradePage.class);
            startActivity(toAStd);
            finish();
        } else if (vStdArea.getId() == R.id.AdminDetailsBtn) {

            Intent toStdArea = new Intent(StudentArea.this, AdminRegPage.class);
            toStdArea.putExtra("stdEmail", email);
            startActivity(toStdArea);
            finish();
        } else if (vStdArea.getId() == R.id.StdDashViewBtn) {
            Intent toAdminArea = new Intent(StudentArea.this,ViewDetailsPage.class );
            toAdminArea.putExtra("stdEmail", email);
            startActivity(toAdminArea);
            finish();
        }
      else if (vStdArea.getId() == R.id.AddDetailsBtn) {
        Intent toAdminArea = new Intent(StudentArea.this,AddStdDetailsPage.class );
        toAdminArea.putExtra("stdEmail", email);
        startActivity(toAdminArea);
        finish();
    }
        else if (vStdArea.getId() == R.id.toStdZoneBtn) {
            Intent toAdminArea = new Intent(StudentArea.this,SelectorPage.class);
            toAdminArea.putExtra("stdEmail", email);
            startActivity(toAdminArea);
            finish();
        }



        else if (vStdArea.getId() == R.id.DashLogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(StudentArea.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
            finish();
        }



    }
}