package com.example.studenthub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthub.Model.Student;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddStdDetailsPage extends AppCompatActivity {

    private EditText year, sem, cumGPA, name, regNum;
    private Button addDetails, toDashBoardBTn;
    private String userEmail, userID;
    private ProgressBar proBarAddStd;
    private AutoCompleteTextView uni, fac, field;
    private ArrayList<String> ExistinguniStrlist, ExistingfacStrlist, ExistingfieldStrlist;
    private ActionBar actionBar;

    private FirebaseDatabaseManager firebaseDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_std_details_page);
        // ActionBar color change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        proBarAddStd = findViewById(R.id.pbStdAddPage);
        uni = findViewById(R.id.ETUniversity);
        fac = findViewById(R.id.ETFaculty);
        field = findViewById(R.id.ETField);
        year = findViewById(R.id.ETYear);
        sem = findViewById(R.id.ETSemester);
        cumGPA = findViewById(R.id.ETLastCumGPA);
        addDetails = findViewById(R.id.ServerAdd);
        name = findViewById(R.id.ETAddStdNameOfStd);
        regNum = findViewById(R.id.ETRegNum);
        toDashBoardBTn = findViewById(R.id.FromStdDetailstoStddash);

        // Initialize FirebaseDatabaseManager
        firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();

        // Get the current user
        FirebaseUser user = firebaseDatabaseManager.getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            userID = userEmail.substring(0, userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
            finish();
        }

        // Retrieve admin data
        firebaseDatabaseManager.getAdminData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ExistinguniStrlist = new ArrayList<>();
                ExistingfacStrlist = new ArrayList<>();
                ExistingfieldStrlist = new ArrayList<>();

                ExistinguniStrlist.add("");
                ExistingfacStrlist.add("");
                ExistingfieldStrlist.add("");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uniVar = snapshot.child("University").getValue(String.class);
                    String facVar = snapshot.child("Faculty").getValue(String.class);
                    String fieldVar = snapshot.child("Field").getValue(String.class);

                    if (!ExistinguniStrlist.contains(uniVar)) {
                        ExistinguniStrlist.add(uniVar);
                    }
                    if (!ExistingfacStrlist.contains(facVar)) {
                        ExistingfacStrlist.add(facVar);
                    }
                    if (!ExistingfieldStrlist.contains(fieldVar)) {
                        ExistingfieldStrlist.add(fieldVar);
                    }
                }

                ArrayAdapter<String> adapterUni = new ArrayAdapter<>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line, ExistinguniStrlist);
                uni.setAdapter(adapterUni);

                ArrayAdapter<String> adapterfac = new ArrayAdapter<>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line, ExistingfacStrlist);
                fac.setAdapter(adapterfac);

                ArrayAdapter<String> adapterfield = new ArrayAdapter<>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line, ExistingfieldStrlist);
                field.setAdapter(adapterfield);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        // Add details button click listener
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String uniStr = uni.getText().toString();
                String facStr = fac.getText().toString();
                String fieldStr = field.getText().toString();
                String yearStr = year.getText().toString();
                String semStr = sem.getText().toString();
                String cumGPAStr = cumGPA.getText().toString();
                String nameStr = name.getText().toString();
                String regNumStr = regNum.getText().toString();

                // Check if fields are empty
                if (TextUtils.isEmpty(uniStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "University is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(facStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Faculty is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(fieldStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Field is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(yearStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Year is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(semStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Current Semester is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(cumGPAStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Last Cumulative GPA is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(nameStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Student name is required", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(regNumStr)) {
                    Toast.makeText(AddStdDetailsPage.this, "Student registration number is required", Toast.LENGTH_LONG).show();
                    return;
                }

                // Show progress bar
                proBarAddStd.setVisibility(View.VISIBLE);

                // Create Student object using Builder
                Student student = new Student.Builder()
                        .setUserId(userID)
                        .setUniversity(uniStr)
                        .setFaculty(facStr)
                        .setField(fieldStr)
                        .setYear(yearStr)
                        .setSemester(semStr)
                        .setLastCumGPA(cumGPAStr)
                        .setName(nameStr)
                        .setRegNum(regNumStr)
                        .setAdmin("0")
                        .build();

                // Add student details to the database
                firebaseDatabaseManager.addStudentDetails(student);

                Toast.makeText(AddStdDetailsPage.this, "Details added successfully", Toast.LENGTH_LONG).show();
                proBarAddStd.setVisibility(View.INVISIBLE);
            }
        });

        // Dashboard button click listener
        toDashBoardBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receivedStr = getIntent().getStringExtra("fromStudentArea");

                if (receivedStr != null && !TextUtils.isEmpty(receivedStr) && receivedStr.equals("StudentArea")) {
                    startActivity(new Intent(getApplicationContext(), SelectorPage.class));
                    finish();
                } else {
                    // Check if student is registered
                    firebaseDatabaseManager.isStudentRegistered(userID, new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                startActivity(new Intent(getApplicationContext(), StudentArea.class));
                                finish();
                            } else {
                                Toast.makeText(AddStdDetailsPage.this, "You need to fill and register!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                }
            }
        });
    }
}


