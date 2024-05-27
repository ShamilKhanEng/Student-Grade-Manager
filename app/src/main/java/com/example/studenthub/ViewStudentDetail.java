package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ViewStudentDetail extends AppCompatActivity {

    private String userEmail,userID;
    private Button BtnStdViewBack;
    private TextView NameTV, UniversityTV,FacultyTV, FieldTV, YearTV, SemesterTV, LastCumGPATV, AdminStatusTV, EmailTV,RegNumTV;
    private ProgressBar progressBarStdview;
    private String uniStr;
    private String facStr;
    private String fieldStr;
    private String yearStr;
    private String semStr;
    private String cumGPAStr;
    private String nameStr;
    private String regNumStr;
    private String AdminStatusStr;
    ActionBar actionBar;

    private Button PDFbutton;

    private FirebaseDatabaseManager firebaseDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_detail);
        PDFbutton = findViewById(R.id.pdfbutton);
        //actiobar colour change
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BDAC78")));


        BtnStdViewBack=findViewById(R.id.StdViewBackButton);






        NameTV = findViewById(R.id.TVStdViewNameValue);
        UniversityTV = findViewById(R.id.TVStdUniversityValue);
        FacultyTV = findViewById(R.id.TVStdFacultyValue);
        FieldTV = findViewById(R.id.TVStdFieldValue);
        YearTV = findViewById(R.id.TVstdYearValue);
        SemesterTV = findViewById(R.id.TVStdSemesterValue);
        LastCumGPATV = findViewById(R.id.TVStdGPAValue);
        AdminStatusTV = findViewById(R.id.TVStdAdminStatusValue);
        EmailTV = findViewById(R.id.TVStdEmailViewValue);
        RegNumTV = findViewById(R.id.TVStdRegNumValue);

        progressBarStdview = findViewById(R.id.proStdViewPage);


        //get the email of the current user and get the userID by splitting the
        //email by @
        // Initialize FirebaseDatabaseManager
        firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
        FirebaseUser user = firebaseDatabaseManager.getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            userID = userEmail.substring(0, userEmail.indexOf("@"));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
            finish();

        }

        progressBarStdview.setVisibility(View.INVISIBLE);

        DatabaseReference refStdView = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
        refStdView.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    if (child.getKey().equals("RegNum")) {
                        regNumStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Name")) {
                        nameStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("University")) {
                        uniStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Faculty")) {
                        facStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Field")) {
                        fieldStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Year")) {
                        yearStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Semester")) {
                        semStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("LastCumGPA")) {
                        cumGPAStr = child.getValue().toString();
                    }
                    if (child.getKey().equals("Admin")) {
                        AdminStatusStr = child.getValue().toString();
                    }


                    try {
                        NameTV.setText(nameStr);
                        UniversityTV.setText(uniStr);
                        FacultyTV.setText(facStr);
                        FieldTV.setText(fieldStr);
                        YearTV.setText(yearStr);
                        SemesterTV.setText(semStr);
                        LastCumGPATV.setText(cumGPAStr);
                        AdminStatusTV.setText(AdminStatusStr);
                        EmailTV.setText(userEmail);
                        RegNumTV.setText(regNumStr);
                        progressBarStdview.setVisibility(View.INVISIBLE);

                    } catch (NullPointerException e) {
                        Toast.makeText(ViewStudentDetail.this, "Error the requested data cannot be viewed", Toast.LENGTH_LONG).show();

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnStdViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),StudentArea.class));
                finish();
            }
        });
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }
    private void createPDF(){
        PDFbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(250,350,1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(12.0f);
                canvas.drawText("STUDENT HUB",myPageInfo1.getPageWidth()/2,30,myPaint);

                myPaint.setTextSize(6.0f);
                myPaint.setColor(Color.rgb(122,119,119));
                canvas.drawText("Academic Deails",myPageInfo1.getPageWidth()/2,40,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(9.0f);
                myPaint.setColor(Color.rgb(122,119,119));
                canvas.drawText("Student information",10,70,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(8.0f);
                myPaint.setColor(Color.BLACK);

                int startXPosition = 10;
                int endXPosition = myPageInfo1.getPageWidth()-10;
                int startYPosition = 100;

                canvas.drawText("Name",startXPosition,startYPosition,myPaint);
                canvas.drawText(nameStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("University",startXPosition,startYPosition,myPaint);
                canvas.drawText(uniStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Faculty",startXPosition,startYPosition,myPaint);
                canvas.drawText(facStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Field",startXPosition,startYPosition,myPaint);
                canvas.drawText(fieldStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Year",startXPosition,startYPosition,myPaint);
                canvas.drawText(yearStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Semester",startXPosition,startYPosition,myPaint);
                canvas.drawText(semStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Registration Number",startXPosition,startYPosition,myPaint);
                canvas.drawText(regNumStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Cumulative GPA",startXPosition,startYPosition,myPaint);
                canvas.drawText(cumGPAStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Admin Status",startXPosition,startYPosition,myPaint);
                canvas.drawText(regNumStr,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;
                canvas.drawText("Reistered Email",startXPosition,startYPosition,myPaint);
                canvas.drawText(userEmail,90,startYPosition,myPaint);
                canvas.drawLine(startXPosition,startYPosition+3,endXPosition,startYPosition+3,myPaint);
                startYPosition+=20;


                canvas.drawLine(85,92,85,300,myPaint);


                //canvas.drawBitmap(bmp,40,50,myPaint);
                myPdfDocument.finishPage(myPage1);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/FifthPDF.pdf");
                try{
                    myPdfDocument.writeTo(new FileOutputStream(file));
                }catch (IOException e){
                    e.printStackTrace();
                }
                myPdfDocument.close();

            }
        });
    }
}