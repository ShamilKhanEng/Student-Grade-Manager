package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class SelectorPage extends AppCompatActivity implements View.OnClickListener {

    private TextView dispName;
    private ImageButton stdAnonymous, adminArea, stdArea;
    private Button logOutBtn;
    private String emailFromlog;
    private String emailFromSign;
    private String nameFromSign;
    private String email,userID,userEmail;

    private DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_page);

        //linking the variables in the java script with the components in the xml script
        dispName = findViewById(R.id.TVStudentName);
        stdAnonymous = findViewById(R.id.GradCalBTn);
        stdArea = findViewById(R.id.ViewpreBtn);
        adminArea = findViewById(R.id.AdminDetailsBtn);
        logOutBtn = findViewById(R.id.DashLogoutBtn);


        //calling the methods set on setOnClickListener to trigger the Onclick method when these buttons are clicked
        logOutBtn.setOnClickListener(this);
        stdAnonymous.setOnClickListener(this);
        stdArea.setOnClickListener(this);
        adminArea.setOnClickListener(this);


        String dispStr=getIntent().getStringExtra("nameOfUser");

        if(dispStr !=null && !(TextUtils.isEmpty(dispStr)) && dispStr.substring(0,9).equals("loginPage")){
            dispName.setText(dispStr.substring(9));
        }
        else if(dispStr !=null && !(TextUtils.isEmpty(dispStr)) && dispStr.substring(0,8).equals("SignPage")){
            dispName.setText(dispStr.substring(8));
        }





    }


    @Override
    public void onClick(View vSelector) {

        if (vSelector.getId() == R.id.GradCalBTn) {

            Intent toAStd = new Intent(SelectorPage.this, AnonymousArea.class);
            startActivity(toAStd);
            finish();
        } else if (vSelector.getId() == R.id.AdminDetailsBtn) {



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

            dref= FirebaseDatabase.getInstance().getReference();

           dref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String status=snapshot.child("Student").child(userID).child("Admin").getValue(String.class);

                   if(status.equals("1")){

                       startActivity(new Intent(getApplicationContext(),AdminArea.class));


                   }else{

                       startActivity(new Intent(getApplicationContext(),AdminRegPage.class));


                   }
                   finish();


               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });




        } else if (vSelector.getId() == R.id.ViewpreBtn) {
            Intent toAdminArea = new Intent(SelectorPage.this,AddStdDetailsPage.class );
            toAdminArea.putExtra("stdEmail", email);
            startActivity(toAdminArea);
            finish();
        } else if (vSelector.getId() == R.id.DashLogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(SelectorPage.this, "Logged out Successfully", Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
