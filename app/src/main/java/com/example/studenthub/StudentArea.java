package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ImageButton gradCal, stdDetails,Adddetails,RemoveDetailsBtn;
    private Button logOutDashBtn,toStudentZoneBtn;
    private String emailFromlog;
    private String emailFromSign;
    private String nameFromSel;
    private String userID,userEmail;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private Button mainPopUpClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_area);

        //linking the variables in the java script with the components in the xml script
        dispMsg = findViewById(R.id.TVstudentdash);
        stdDetails = findViewById(R.id.StdDashViewBtn);
        Adddetails = findViewById(R.id.AddDetailsBtn);
        gradCal=findViewById(R.id.GradCalBtn);
        toStudentZoneBtn=findViewById(R.id.toStdZoneBtn);
        RemoveDetailsBtn=findViewById(R.id.StdDashBoardDeleteBtn);



        stdDetails.setOnClickListener(this);
        Adddetails.setOnClickListener(this);
        gradCal.setOnClickListener(this);
        toStudentZoneBtn.setOnClickListener(this);
        RemoveDetailsBtn.setOnClickListener(this);


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
                Toast.makeText(StudentArea.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View vStdArea) {

        if (vStdArea.getId() == R.id.GradCalBtn) {

            Intent toAStd = new Intent(StudentArea.this, GradePage.class);
            startActivity(toAStd);
            finish();
        }  else if (vStdArea.getId() == R.id.StdDashViewBtn) {
            Intent toStdView = new Intent(StudentArea.this,ViewStudentDetail.class );
            startActivity(toStdView);
            finish();
        }

       else if (vStdArea.getId() == R.id.AddDetailsBtn) {
        Intent toStdAddDetails = new Intent(StudentArea.this,AddStdDetailsPage.class );
        toStdAddDetails.putExtra("fromStudentArea","StudentArea");
        startActivity(toStdAddDetails);
        finish();
       }

        else if (vStdArea.getId() == R.id.toStdZoneBtn) {
            Intent toStdZone = new Intent(StudentArea.this,SelectorPage.class);
            startActivity(toStdZone);
            finish();
        }



        else if(vStdArea.getId() == R.id.StdDashBoardDeleteBtn){

            startActivity(new Intent(getApplicationContext(), StudentCourseRemovePage.class));
            finish();


        }



    }


    public void createNewContactDialog(){
        dialogbuilder =new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.helppopup_student_area,null);


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