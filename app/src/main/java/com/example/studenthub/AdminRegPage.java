package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminRegPage extends AppCompatActivity  implements View.OnClickListener {

    private Button check, make;
    private ProgressBar progressBar;
    private String userEmail, userID;

    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String AdminStr;
    private String LastCumGPAStr;
    private String NameStr;
    private String keys;


    private String uniVar;
    private String facVar;
    private String fieldVar;
    private String yearVar;
    private String semVar;
    private String AdminVar;
    private String LastCumGPAVar;
    private String NameStrVar;
    private String keysVar;

    private DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reg_page);

        check = findViewById(R.id.BtnCheckAvailability);
        make = findViewById(R.id.BtnMakeMeAdmin);
        progressBar = findViewById(R.id.progressBarAdminreg);

        progressBar.setVisibility(View.INVISIBLE);
        check.setOnClickListener(this);
        make.setOnClickListener(this);

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

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Student");

        //getting the reference to the child "Student" under root
        ref1.orderByKey().equalTo(userID).addValueEventListener(new ValueEventListener() {


            //getting a data sanpshot under current user details and extracting the details as necessary
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    uniStr = datas.child("University").getValue(String.class);
                    facStr = datas.child("Faculty").getValue(String.class);
                    fieldStr = datas.child("Field").getValue(String.class);
                    yearStr = datas.child("Year").getValue(String.class);
                    semStr = datas.child("Semester").getValue(String.class);
                    AdminStr = datas.child("Admin").getValue(String.class);
                    LastCumGPAStr = datas.child("LastCumGPA").getValue(String.class);
                    NameStr = datas.child("Name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });



    }

    @Override
    public void onClick(View vAdminReg) {

        if (vAdminReg.getId() == R.id.BtnCheckAvailability) {


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

                                    Toast.makeText(AdminRegPage.this, "There is already an admin", Toast.LENGTH_LONG).show();



                                }
                                else{

                                    Toast.makeText(AdminRegPage.this, "You are qualified to be an Admin", Toast.LENGTH_LONG).show();


                                }




                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });




        } else if (vAdminReg.getId() == R.id.BtnMakeMeAdmin) {

            progressBar.setVisibility(View.VISIBLE);
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

                                    Toast.makeText(AdminRegPage.this, "There is already an admin", Toast.LENGTH_LONG).show();



                                }
                                else{

                                    //This will give the reference to the root in the database
                                    dref= FirebaseDatabase.getInstance().getReference();

                                    //now we are going to add details

                                    dref.child("Admin").child(userID).child("University").setValue(uniStr);
                                    dref.child("Admin").child(userID).child("Faculty").setValue(facStr);
                                    dref.child("Admin").child(userID).child("Field").setValue(fieldStr);
                                    dref.child("Admin").child(userID).child("Year").setValue(yearStr);
                                    dref.child("Admin").child(userID).child("Semester").setValue(semStr);
                                    dref.child("Student").child(userID).child("Admin").setValue("1");



                                    Toast.makeText(AdminRegPage.this, "You are added as an Admin Successfully", Toast.LENGTH_LONG).show();

                                    progressBar.setVisibility(View.INVISIBLE);

                                    //startActivity(new Intent(getApplicationContext(), AdminArea.class));
                                    //finish();


                                }




                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();

                        }
                    });




        }
    }
}