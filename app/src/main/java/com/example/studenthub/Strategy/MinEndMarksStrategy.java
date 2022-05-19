package com.example.studenthub.Strategy;

import android.text.TextUtils;

import com.example.studenthub.Constants;

import java.util.Objects;

public class MinEndMarksStrategy implements CalculationStrategy {
    private Double marks=0.0;
    private Double reqEndMarks;

    @Override
    public String calculate(String CurrentSemStr, String CurrentCumGPAStr, String TotalSemCountStr, String CourseAssignMarksStr, String CourseLabMarksStr, String CourseMidMarksStr, String CourseProjectStr, String ExpectSemGPAStr, String ExpectFinalGPAStr, String TotalEndMarksStr, String key) {



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


        reqEndMarks=Integer.parseInt(Objects.requireNonNull(Constants.GPA_GRADE_MARKS.get(key)))- marks;

        return reqEndMarks.toString();
    }
}
