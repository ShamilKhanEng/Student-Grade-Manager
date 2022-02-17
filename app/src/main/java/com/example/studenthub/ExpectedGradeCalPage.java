package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpectedGradeCalPage extends AppCompatActivity {


    private Button BtnBackExpectGradePage, GExpectComputeBtn;

    private EditText GExpectCumGPA, GExpectTotalCountSem, GExpectCurrentSem, GExpectAssignmentMarks, GExpectLabMarks, GExpectProjectMarks, GExpectMidMarks,GEAllocatedEndMarks;
    private ProgressBar progressBarCGExpect;
    private String CurrentSemStr="";
    private String CurrentCumGPAStr="";
    private String TotalSemCountStr="";
    private String CourseAssignMarksStr="";
    private String CourseLabMarksStr="";
    private String CourseMidMarksStr="";
    private String CourseProjectStr="";
    private String ExpectSemGPAStr="";
    private String ExpectFinalGPAStr="";
    private String TotalEndMarksStr="";
    private String receivedStr;

    private CheckBox GExpectEndMarksCheck;
    private CheckBox GExpectMinSemGPACheck;

    //populating a hash map for the purpose of iterating through key value pair
    HashMap<String,String> GPAmapMarks;

    private Spinner expectSemGPASp,expectFinalGPASp;


    private TextView MinExpectedEndMarks,MinExpectedSemGPA;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_grade_cal_page);


        //linking the textview
        MinExpectedEndMarks=findViewById(R.id.TVExpectGradeEndMarksValue);
        MinExpectedSemGPA=findViewById(R.id.TVExpectGradeMinGPAValue);


        //linking the buttons
        BtnBackExpectGradePage=findViewById(R.id.CourseExpectGradeBack);
        GExpectComputeBtn=findViewById(R.id.ExpectGradeComputeButton);


        //linking the edit texts

        GExpectCumGPA = findViewById(R.id.ETExpectGradeCumGPAValue);
        GExpectTotalCountSem=findViewById(R.id.ETCoExpectGradeTotalSemCountValue);
        GExpectCurrentSem = findViewById(R.id.ETCoExpectGradeCurrentSemesterValue);
        GExpectLabMarks = findViewById(R.id.ETCoExpectGradeLabMarksValue);
        GExpectAssignmentMarks = findViewById(R.id.ETCoExpectGradeAssignMarksValue);
        GExpectProjectMarks = findViewById(R.id.ETCoExpectGradeProjectMarksValue);
        GExpectMidMarks = findViewById(R.id.ETCoExpectGradeMidMarksValue);
        GEAllocatedEndMarks=findViewById(R.id.ETCoExpectGradeTotEndMarksValue);


        //linking the spinners
        expectSemGPASp = findViewById(R.id.SpCoExpectGradeExpectedSemGPAValue);
        expectFinalGPASp = findViewById(R.id.SpCoExpectGradeExpectedFinalGPAValue);



        //linking the progress bar
        progressBarCGExpect = findViewById(R.id.progBarCoExpectGradePage);

        //linking the checkboxes
        GExpectEndMarksCheck = findViewById(R.id.ExpectGradeEndMarksCheck);
        GExpectMinSemGPACheck = findViewById(R.id.ExpectGrademinGPACheck);

        progressBarCGExpect.setVisibility(View.INVISIBLE);

        //populating a hash map for the purpose of iterating through key value pair
        GPAmapMarks=new HashMap<>();
        GPAmapMarks.put("4.0","80");
        GPAmapMarks.put("3.7","75");
        GPAmapMarks.put("3.3","70");
        GPAmapMarks.put("3.0","65");
        GPAmapMarks.put("2.7","60");
        GPAmapMarks.put("2.3","55");
        GPAmapMarks.put("2.0","50");
        GPAmapMarks.put("1.7","45");
        GPAmapMarks.put("1.3","40");
        GPAmapMarks.put("1.0","35");

        //populating the GPA spinners
        //first creating an arraylist consisting of the relevant Course GPA
        ArrayList<String> GPAlist=new ArrayList<>();
        GPAlist.add("4.0");
        GPAlist.add("3.7");
        GPAlist.add("3.3");
        GPAlist.add("3.0");
        GPAlist.add("2.7");
        GPAlist.add("2.3");
        GPAlist.add("2.0");
        GPAlist.add("1.3");
        GPAlist.add("1.0");



        //setting the spinners with the populated GPAlist
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExpectedGradeCalPage.this, android.R.layout.simple_spinner_item,GPAlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expectSemGPASp.setAdapter(adapter);
        expectFinalGPASp.setAdapter(adapter);



        GExpectComputeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBarCGExpect.setVisibility(View.VISIBLE);

                CurrentSemStr=GExpectCurrentSem.getText().toString().trim();
                CurrentCumGPAStr=GExpectCumGPA.getText().toString().trim();
                TotalSemCountStr=GExpectTotalCountSem.getText().toString().trim();
                CourseAssignMarksStr=GExpectAssignmentMarks.getText().toString().trim();
                CourseLabMarksStr=GExpectLabMarks.getText().toString().trim();
                CourseMidMarksStr=GExpectMidMarks.getText().toString().trim();
                CourseProjectStr=GExpectProjectMarks.getText().toString().trim();
                TotalEndMarksStr=GEAllocatedEndMarks.getText().toString().trim();



                ExpectSemGPAStr=expectSemGPASp.getSelectedItem().toString().trim();
                ExpectFinalGPAStr=expectFinalGPASp.getSelectedItem().toString().trim();

                if(GExpectMinSemGPACheck.isChecked()){
                    if(TextUtils.isEmpty(CurrentCumGPAStr)){
                        Toast.makeText(ExpectedGradeCalPage.this,"Current Cumulative GPA is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;
                    }
                    else if(TextUtils.isEmpty(TotalSemCountStr)){

                        Toast.makeText(ExpectedGradeCalPage.this,"Total Count of Semesters is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;
                    }
                    else if(TextUtils.isEmpty(CurrentSemStr)){

                        Toast.makeText(ExpectedGradeCalPage.this,"Current Semester is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;
                    }
                    else if(TextUtils.isEmpty(ExpectFinalGPAStr)){


                        Toast.makeText(ExpectedGradeCalPage.this,"Expected Final GPA is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;

                    }
                    else{



                        for(String key:GPAmapMarks.keySet()){

                            if(ExpectFinalGPAStr.equals(key)){


                                //finding how many semesters remaining to come including current semester
                                int RemSem = Integer.parseInt(TotalSemCountStr) - Integer.parseInt(CurrentSemStr) + 1;

                                //finding the total cumulative GPA expected with this Final GPA expectation
                                double TotalCumGPA= Double.parseDouble(TotalSemCountStr) * Double.parseDouble(key);

                                //Calculating the remaining Total GPA to achieve
                                double RemTotalGPA=TotalCumGPA-Double.parseDouble(CurrentCumGPAStr)*(Integer.parseInt(CurrentSemStr)-1);

                                //calculating the min GPA by dividing the RemTotalGPA by RemSem
                                double minGPA=RemTotalGPA/RemSem;


                                MinExpectedSemGPA.setText(""+minGPA);


                            }




                        }





                    }





                }


                if(GExpectEndMarksCheck.isChecked()){

                    //checking whether the fields are empty or not
                    if(TextUtils.isEmpty(ExpectSemGPAStr)){
                        Toast.makeText(ExpectedGradeCalPage.this,"Expected Sem GPA is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;
                    }
                    else if(TextUtils.isEmpty(CourseAssignMarksStr) && TextUtils.isEmpty(CourseLabMarksStr) && TextUtils.isEmpty(CourseMidMarksStr) && TextUtils.isEmpty(CourseProjectStr)){

                        Toast.makeText(ExpectedGradeCalPage.this,"Atleast one of lab,assignment,mid,project marks is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;
                    }
                    else if(TextUtils.isEmpty(TotalEndMarksStr)){


                        Toast.makeText(ExpectedGradeCalPage.this,"Allocated End Marks is required",Toast.LENGTH_SHORT).show();
                        progressBarCGExpect.setVisibility(View.INVISIBLE);
                        return;

                    }
                    else{

                        for(String key:GPAmapMarks.keySet()){

                            if(ExpectSemGPAStr.equals(key)){

                                Double marks=0.0;
                                Double reqEndMarks=0.0;

                                if(!TextUtils.isEmpty(CourseAssignMarksStr)){

                                    marks=marks+Double.parseDouble(CourseAssignMarksStr);

                                }
                                if(!TextUtils.isEmpty(CourseLabMarksStr)){

                                    marks=marks+Double.parseDouble(CourseLabMarksStr);
                                }
                                if(!TextUtils.isEmpty(CourseProjectStr)){

                                    marks=marks+Double.parseDouble(CourseProjectStr);


                                }
                                if(!TextUtils.isEmpty(CourseMidMarksStr)){

                                    marks=marks+Double.parseDouble(CourseMidMarksStr);


                                }

                                reqEndMarks=Integer.parseInt(GPAmapMarks.get(key))-marks;
                                if(reqEndMarks>Integer.parseInt(TotalEndMarksStr)){

                                    Toast.makeText(ExpectedGradeCalPage.this, "That Achievement is impossible", Toast.LENGTH_SHORT).show();
                                    MinExpectedEndMarks.setText(reqEndMarks+"");


                                }
                                else{
                                    Toast.makeText(ExpectedGradeCalPage.this, "You can do it, work hard For it!", Toast.LENGTH_LONG).show();
                                    MinExpectedEndMarks.setText(reqEndMarks+"");


                                }



                            }




                        }













                    }






                }




                progressBarCGExpect.setVisibility(View.INVISIBLE);

                GExpectCumGPA.setText("");
                GExpectTotalCountSem.setText("");
                GExpectCurrentSem.setText("");
                GExpectLabMarks.setText("");
                GExpectAssignmentMarks.setText("");
                GExpectProjectMarks.setText("");
                GExpectMidMarks.setText("");
                GEAllocatedEndMarks.setText("");

            }




        });


        BtnBackExpectGradePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to identify the intent from which the user entered this activity
                receivedStr=getIntent().getStringExtra("intentFind");


                if(receivedStr !=null && !(TextUtils.isEmpty(receivedStr)) && receivedStr.equals("SelectorPage")){

                    startActivity(new Intent(getApplicationContext(),SelectorPage.class));
                    finish();

                }
                else{

                    startActivity(new Intent(getApplicationContext(),GradePage.class));
                    finish();

                }


            }
        });








    }
}