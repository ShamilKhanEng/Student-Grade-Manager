package com.example.studenthub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //declaring the button variable
    private Button GetStartBtn;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));

        //linking the declared button object with the button in the xml file
        GetStartBtn=findViewById(R.id.ServerAdd);

        //calling the setOnClickListener method to trigger operation when the get Start button is clicked
        //in the display page
        GetStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if button was clicked the user will directed to login page

                startActivity(new Intent(getApplicationContext(),LoginPage.class));
                finish();




            }
        });

    }
}