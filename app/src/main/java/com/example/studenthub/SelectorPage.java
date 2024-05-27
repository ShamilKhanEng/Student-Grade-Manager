package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectorPage extends AppCompatActivity implements View.OnClickListener {

    private TextView dispName;
    private ImageButton stdAnonymous, adminArea, stdArea;
    private Button logOutBtn;
    private String emailFromlog;
    private String emailFromSign;
    private String nameFromSign;
    private String userkey,userID,userEmail;
    private int flagExist=0;
    private DatabaseReference dref;
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private Button mainPopUpClose;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_page);
        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        //linking the variables in the java script with the components in the xml script
        dispName = findViewById(R.id.TVStudentName);
        stdAnonymous = findViewById(R.id.GradCalBTn);
        stdArea = findViewById(R.id.ViewpreBtn);
        adminArea = findViewById(R.id.AdminDetailsBtn);



        //calling the methods set on setOnClickListener to trigger the Onclick method when these buttons are clicked
        stdAnonymous.setOnClickListener(this);
        stdArea.setOnClickListener(this);
        adminArea.setOnClickListener(this);

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


        String dispStr=getIntent().getStringExtra("nameOfUser");


        if(dispStr !=null && !(TextUtils.isEmpty(dispStr)) && dispStr.substring(0,8).equals("SignPage")){
            dispName.setText(dispStr.substring(8));
        }
        else{
            dispName.setText(userID);
        }





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
                Toast.makeText(SelectorPage.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onClick(View vSelector) {

        if (vSelector.getId() == R.id.GradCalBTn) {

            Intent toEGPageFromSelector=new Intent(SelectorPage.this,ExpectedGradeCalPage.class);
            toEGPageFromSelector.putExtra("intentFind","SelectorPage");
            startActivity(toEGPageFromSelector);
            finish();

        } else if (vSelector.getId() == R.id.AdminDetailsBtn) {



            DatabaseReference reftoAdd=FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
            reftoAdd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() == null){
                        Toast.makeText(SelectorPage.this, "Please register as a student!", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    else{


                        String status=snapshot.child("Admin").getValue(String.class);

                        if(status.equals("1")){


                            startActivity(new Intent(getApplicationContext(),AdminArea.class));

                        }else{

                            startActivity(new Intent(getApplicationContext(),AdminRegPage.class));


                        }
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });












        } else if (vSelector.getId() == R.id.ViewpreBtn) {


            DatabaseReference reftoStd = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
            reftoStd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                        Intent tostdAddDetail = new Intent(SelectorPage.this, AddStdDetailsPage.class);
                        startActivity(tostdAddDetail);
                        finish();

                    } else {


                        Intent tostdArea = new Intent(SelectorPage.this, StudentArea.class);
                        startActivity(tostdArea);
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }

    public void createNewContactDialog(){
        dialogbuilder =new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.helppopup_selector_page,null);


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
