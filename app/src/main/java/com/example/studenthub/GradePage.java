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

public class GradePage extends AppCompatActivity implements View.OnClickListener {


    private ImageButton ExpectedGradeCal, BtnRankViewer, ViewStdGradeDetails,AddStdCourseDetail;
    private Button logOutgradeDashBtn,toStudentAreaBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_page);

        //linking the variables in the java script with the components in the xml script
        BtnRankViewer = findViewById(R.id.RankViewerBtn);
        ViewStdGradeDetails = findViewById(R.id.ViewPrevCourseGradeBtn);
        AddStdCourseDetail = findViewById(R.id.GradeDashAddMarkBtn);
        logOutgradeDashBtn = findViewById(R.id.GradeDashlogoutBtn);
        ExpectedGradeCal=findViewById(R.id.ExpectMarkBtn);
        toStudentAreaBtn=findViewById(R.id.gradeToStdAreaBtn);


        BtnRankViewer.setOnClickListener(this);
        ViewStdGradeDetails.setOnClickListener(this);
        AddStdCourseDetail.setOnClickListener(this);
        logOutgradeDashBtn.setOnClickListener(this);
        ExpectedGradeCal.setOnClickListener(this);
        toStudentAreaBtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View vGradeDash) {

        if (vGradeDash.getId() == R.id.RankViewerBtn) {


            startActivity(new Intent(getApplicationContext(),RankViewerPage.class));
            finish();

        } else if (vGradeDash.getId() == R.id.ViewPrevCourseGradeBtn) {

            startActivity(new Intent(getApplicationContext(),GradeCourseViewerPage.class));
            finish();





        } else if (vGradeDash.getId() == R.id.GradeDashAddMarkBtn) {

            startActivity(new Intent(getApplicationContext(),MarksAddPage.class));
            finish();
        }

        else if (vGradeDash.getId() == R.id.ExpectMarkBtn) {

            Intent toEGPage=new Intent(GradePage.this,ExpectedGradeCalPage.class);
            toEGPage.putExtra("intent","GradePage");
            startActivity(new Intent(getApplicationContext(),ExpectedGradeCalPage.class));
            finish();

        }



        else if (vGradeDash.getId() == R.id.gradeToStdAreaBtn) {
            startActivity(new Intent(getApplicationContext(),StudentArea.class));
            finish();

        }



        else if (vGradeDash.getId() == R.id.GradeDashlogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(GradePage.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
            finish();
        }




    }
}