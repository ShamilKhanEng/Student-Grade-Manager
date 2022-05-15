package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseDelPage extends AppCompatActivity {


    ArrayList<String> CourseItems;
    private String userEmail,userID;
    private String result;
    private Button BtnDelBack,BtnDelRemove;
    private Spinner CourseDelspinner;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_del_page);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        CourseDelspinner=findViewById(R.id.CourseSpinnerView);

        BtnDelRemove=findViewById(R.id.CourseDelDeleteButton);
        BtnDelBack=findViewById(R.id.CourseDelBackButton);

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

        DatabaseReference refAdmin = FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses");

        refAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                CourseItems=new ArrayList<String>();
                CourseItems.add("");
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    CourseItems.add(userkey); //add result into array list
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseDelPage.this, android.R.layout.simple_spinner_item,CourseItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CourseDelspinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        BtnDelRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the user selected item from the drop down menu
                result = CourseDelspinner.getSelectedItem().toString().trim();

                //removing the relevant Course from database
                DatabaseReference removeRef=FirebaseDatabase.getInstance().getReference().child("Admin").child(userID).child("Courses").child(result);
                removeRef.setValue(null);

                Toast.makeText(CourseDelPage.this,result +" has been successfully removed!", Toast.LENGTH_SHORT).show();



            }
        });





        BtnDelBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AdminArea.class));
                finish();

            }

        });




    }
}