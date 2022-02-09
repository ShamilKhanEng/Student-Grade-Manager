package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdminArea extends AppCompatActivity implements View.OnClickListener  {

    private ImageButton CourseDel,CourseUpdate,CourseView,CourseAdd;
    private Button logout,toslector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        CourseDel=findViewById(R.id.AdminAreaDeleteBtn);
        CourseUpdate=findViewById(R.id.adminDashUpdateBtn);
        CourseView=findViewById(R.id.adminDashViewbtn);
        logout=findViewById(R.id.adminDashlogoutBtn);
        toslector=findViewById(R.id.toStdZoneBtnfromadmin);
        CourseAdd=findViewById(R.id.admindashAddBtn);




        CourseDel.setOnClickListener(this);
        CourseUpdate.setOnClickListener(this);
        CourseView.setOnClickListener(this);
        logout.setOnClickListener(this);
        toslector.setOnClickListener(this);
        CourseAdd.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.AdminAreaDeleteBtn){

            startActivity(new Intent(getApplicationContext(),CourseDelPage.class));
            finish();

        }
        else if(view.getId()==R.id.adminDashUpdateBtn){

            startActivity(new Intent(getApplicationContext(),CourseUpdatePage.class));
            finish();

        }
        else if(view.getId()==R.id.adminDashViewbtn){

            startActivity(new Intent(getApplicationContext(),CourseViewPage.class));
            finish();

        }
        else if(view.getId()==R.id.adminDashlogoutBtn){

            startActivity(new Intent(getApplicationContext(),LoginPage.class));
            Toast.makeText(AdminArea.this,"successfully logged out",Toast.LENGTH_LONG).show();
            finish();

        }
        else if(view.getId()==R.id.toStdZoneBtnfromadmin){

            startActivity(new Intent(getApplicationContext(),SelectorPage.class));
            finish();

        }

        else if(view.getId()==R.id.admindashAddBtn){

            startActivity(new Intent(getApplicationContext(),CourseAddPage.class));
            finish();

        }


    }
}