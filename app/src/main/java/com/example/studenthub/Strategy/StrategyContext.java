package com.example.studenthub.Strategy;

public class StrategyContext {

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key="";

    private CalculationStrategy strategy;


    public StrategyContext(CalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy(){

        return strategy.calculate(CurrentSemStr, CurrentCumGPAStr, TotalSemCountStr, CourseAssignMarksStr,
                CourseLabMarksStr, CourseMidMarksStr, CourseProjectStr, ExpectSemGPAStr,
                ExpectFinalGPAStr, TotalEndMarksStr , key);
    }

    public String getCurrentSemStr() {
        return CurrentSemStr;
    }

    public void setCurrentSemStr(String currentSemStr) {
        CurrentSemStr = currentSemStr;
    }

    public String getCurrentCumGPAStr() {
        return CurrentCumGPAStr;
    }

    public void setCurrentCumGPAStr(String currentCumGPAStr) {
        CurrentCumGPAStr = currentCumGPAStr;
    }

    public String getTotalSemCountStr() {
        return TotalSemCountStr;
    }

    public void setTotalSemCountStr(String totalSemCountStr) {
        TotalSemCountStr = totalSemCountStr;
    }

    public String getCourseAssignMarksStr() {
        return CourseAssignMarksStr;
    }

    public void setCourseAssignMarksStr(String courseAssignMarksStr) {
        CourseAssignMarksStr = courseAssignMarksStr;
    }

    public String getCourseLabMarksStr() {
        return CourseLabMarksStr;
    }

    public void setCourseLabMarksStr(String courseLabMarksStr) {
        CourseLabMarksStr = courseLabMarksStr;
    }

    public String getCourseMidMarksStr() {
        return CourseMidMarksStr;
    }

    public void setCourseMidMarksStr(String courseMidMarksStr) {
        CourseMidMarksStr = courseMidMarksStr;
    }

    public String getCourseProjectStr() {
        return CourseProjectStr;
    }

    public void setCourseProjectStr(String courseProjectStr) {
        CourseProjectStr = courseProjectStr;
    }

    public String getExpectSemGPAStr() {
        return ExpectSemGPAStr;
    }

    public void setExpectSemGPAStr(String expectSemGPAStr) {
        ExpectSemGPAStr = expectSemGPAStr;
    }

    public String getExpectFinalGPAStr() {
        return ExpectFinalGPAStr;
    }

    public void setExpectFinalGPAStr(String expectFinalGPAStr) {
        ExpectFinalGPAStr = expectFinalGPAStr;
    }

    public String getTotalEndMarksStr() {
        return TotalEndMarksStr;
    }

    public void setTotalEndMarksStr(String totalEndMarksStr) {
        TotalEndMarksStr = totalEndMarksStr;
    }


}
