package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddStdDetailsPage extends AppCompatActivity {

    private EditText year,sem,cumGPA,name,regNum;
    private Button addDetails,toDashBoardBTn;
    private String userEmail,userID;
    private ProgressBar proBarAddStd;
    //this string is to store the message from the receiving page
    private String receivedStr;
    private AutoCompleteTextView uni,fac,field;
    private Spinner uniSp,facSp,fieldSp;
    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String cumGPAStr;
    private String nameStr;
    private String regNumStr;
    private ArrayList<String> ExistinguniStrlist;
    private ArrayList<String> ExistingfacStrlist;
    private ArrayList<String> ExistingfieldStrlist;
    private DatabaseReference mdatabaseRef;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_std_details_page);
        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        proBarAddStd=findViewById(R.id.pbStdAddPage);
        uni=findViewById(R.id.ETUniversity);
        fac=findViewById(R.id.ETFaculty);
        field=findViewById(R.id.ETField);
        year=findViewById(R.id.ETYear);
        sem=findViewById(R.id.ETSemester);
        cumGPA=findViewById(R.id.ETLastCumGPA);
        addDetails=findViewById(R.id.ServerAdd);
        name=findViewById(R.id.ETAddStdNameOfStd);
        regNum=findViewById(R.id.ETRegNum);
        toDashBoardBTn=findViewById(R.id.FromStdDetailstoStddash);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                userEmail = user.getEmail();
                userID=userEmail.substring(0,userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
            finish();

        }








        //comparing the obtain details with admin details to find the admin userid
        FirebaseDatabase.getInstance().getReference().child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ExistinguniStrlist=new ArrayList<>();
                        ExistingfacStrlist=new ArrayList<>();
                        ExistingfieldStrlist=new ArrayList<>();

                        ExistinguniStrlist.add("");
                        ExistingfacStrlist.add("");
                        ExistingfieldStrlist.add("");


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String uniVar = snapshot.child("University").getValue().toString();
                            String facVar = snapshot.child("Faculty").getValue().toString();
                            String fieldVar = snapshot.child("Field").getValue().toString();

                            if(!ExistinguniStrlist.contains(uniVar)){
                                ExistinguniStrlist.add(uniVar);

                            }
                            if(!ExistingfacStrlist.contains(facVar)){

                                ExistingfacStrlist.add(facVar);

                            }
                            if(!ExistingfieldStrlist.contains(fieldVar)){
                                ExistingfieldStrlist.add(fieldVar);

                            }




                        }

                        ArrayAdapter<String> adapterUni = new ArrayAdapter<String>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line,ExistinguniStrlist);
                        uni.setAdapter(adapterUni);



                        ArrayAdapter<String> adapterfac = new ArrayAdapter<String>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line,ExistingfacStrlist);
                        fac.setAdapter(adapterfac);



                        ArrayAdapter<String> adapterfield = new ArrayAdapter<String>(AddStdDetailsPage.this, android.R.layout.simple_dropdown_item_1line,ExistingfieldStrlist);
                        field.setAdapter(adapterfield);





                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });








        //This will give the reference to the root in the database
        mdatabaseRef= FirebaseDatabase.getInstance().getReference();

        //when the add button is clicked this method will be called
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the string inputted by the user
                //using the getText methods
                 uniStr=uni.getText().toString();
                 facStr=fac.getText().toString();
                 fieldStr=field.getText().toString();
                 yearStr=year.getText().toString();
                 semStr=sem.getText().toString();
                 cumGPAStr=cumGPA.getText().toString();
                 nameStr=name.getText().toString();
                 regNumStr=regNum.getText().toString();

                //checking whether the fields are empty or not
                if(TextUtils.isEmpty(uniStr)){
                    Toast.makeText(AddStdDetailsPage.this,"University is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(facStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Faculty is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(fieldStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Field is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(yearStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Year is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(semStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Current Semester is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(cumGPAStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Last Cumulative GPA is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(nameStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Student name  is required",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(TextUtils.isEmpty(regNumStr)){
                    Toast.makeText(AddStdDetailsPage.this,"Student registration number  is required",Toast.LENGTH_LONG).show();

                    return;
                }

                else{

                    proBarAddStd.setVisibility(View.VISIBLE);

                    //now we are going to add details

                    mdatabaseRef.child("Student").child(userID).child("University").setValue(uniStr);
                    mdatabaseRef.child("Student").child(userID).child("Faculty").setValue(facStr);
                    mdatabaseRef.child("Student").child(userID).child("Field").setValue(fieldStr);
                    mdatabaseRef.child("Student").child(userID).child("Name").setValue(nameStr);
                    mdatabaseRef.child("Student").child(userID).child("Year").setValue(yearStr);
                    mdatabaseRef.child("Student").child(userID).child("RegNum").setValue(regNumStr);
                    mdatabaseRef.child("Student").child(userID).child("LastCumGPA").setValue(cumGPAStr);
                    mdatabaseRef.child("Student").child(userID).child("Semester").setValue(semStr);
                    mdatabaseRef.child("Student").child(userID).child("Admin").setValue("0");

                    Toast.makeText(AddStdDetailsPage.this,"Details added successfully",Toast.LENGTH_LONG).show();

                    proBarAddStd.setVisibility(View.INVISIBLE);
                }


            }
        });

        toDashBoardBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the addition is finished we can go back to std Dashboard


                //to identify the intent from which the user entered this activity
                receivedStr=getIntent().getStringExtra("fromStudentArea");

                if(receivedStr !=null && !(TextUtils.isEmpty(receivedStr)) && receivedStr.equals("StudentArea")){

                    startActivity(new Intent(getApplicationContext(),SelectorPage.class));
                    finish();

                }
                else{




                    //getting and storing the student details to check if he already has registered as a user
                    //comparing the obtain details with admin details to find the admin userid
                    FirebaseDatabase.getInstance().getReference().child("Student")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshotstd) {


                                 int flag=0;

                                    for (DataSnapshot snapshotStd : dataSnapshotstd.getChildren()) {


                                        if(snapshotStd.getKey().toString().equals(userID)) {

                                            startActivity(new Intent(getApplicationContext(),StudentArea.class));
                                            flag=1;
                                            finish();

                                        }

                                    }

                                    if(flag==0){

                                        Toast.makeText(AddStdDetailsPage.this, "You need to fill and register!", Toast.LENGTH_LONG).show();

                                    }







                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    throw databaseError.toException();
                                }
                            });









                }






            }
        });


    }
}