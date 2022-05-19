package com.example.studenthub.Strategy;

public interface CalculationStrategy {
    String calculate(String CurrentSemStr, String CurrentCumGPAStr, String TotalSemCountStr,
                     String CourseAssignMarksStr, String CourseLabMarksStr, String CourseMidMarksStr,
                     String CourseProjectStr, String ExpectSemGPAStr, String ExpectFinalGPAStr,
                     String TotalEndMarksStr, String key);
}
