package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminArea extends AppCompatActivity implements View.OnClickListener  {

    private ImageButton CourseDel,CourseUpdate,CourseView,CourseAdd;
    private Button logout,toslector;
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private Button mainPopUpClose;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        CourseDel=findViewById(R.id.AdminAreaDeleteBtn);
        CourseUpdate=findViewById(R.id.adminDashUpdateBtn);
        CourseView=findViewById(R.id.adminDashViewbtn);
        toslector=findViewById(R.id.toStdZoneBtnfromadmin);
        CourseAdd=findViewById(R.id.admindashAddBtn);
        CourseDel.setOnClickListener(this);
        CourseUpdate.setOnClickListener(this);
        CourseView.setOnClickListener(this);
        toslector.setOnClickListener(this);
        CourseAdd.setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_help:
                createNewContactDialog();
                return true;

            case R.id.menu_logout:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(AdminArea.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);

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

        else if(view.getId()==R.id.toStdZoneBtnfromadmin){

            startActivity(new Intent(getApplicationContext(),SelectorPage.class));
            finish();

        }

        else if(view.getId()==R.id.admindashAddBtn){

            startActivity(new Intent(getApplicationContext(),AddCoursePage.class));




        }


    }

    public void createNewContactDialog(){
        dialogbuilder =new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.helppopup_admin_area,null);


        mainPopUpClose=(Button) contactPopupView.findViewById(R.id.mainMenuBtnClose);

        dialogbuilder.setView(contactPopupView);
        dialog=dialogbuilder.create();
        dialog.show();





        mainPopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}