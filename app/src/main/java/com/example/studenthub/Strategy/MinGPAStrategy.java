package com.example.studenthub.Strategy;

public class MinGPAStrategy implements CalculationStrategy{


    private double minGPA = 0;
    private double RemTotalGPA = 0;
    private double TotalCumGPA = 0;

    private int RemSem = 0;




    public double getMinGPA() {
        return minGPA;
    }

    public void setMinGPA(double minGPA) {
        this.minGPA = minGPA;
    }





    @Override
    public String calculate(String CurrentSemStr, String CurrentCumGPAStr, String TotalSemCountStr,
                            String CourseAssignMarksStr, String CourseLabMarksStr, String CourseMidMarksStr,
                            String CourseProjectStr, String ExpectSemGPAStr, String ExpectFinalGPAStr,
                            String TotalEndMarksStr,String key) {

        //finding how many semesters remaining to come including current semester
         RemSem = Integer.parseInt(TotalSemCountStr) - Integer.parseInt(CurrentSemStr) + 1;

        //finding the total cumulative GPA expected with this Final GPA expectation
         TotalCumGPA= Double.parseDouble(TotalSemCountStr) * Double.parseDouble(key);

        //Calculating the remaining Total GPA to achieve
         RemTotalGPA=TotalCumGPA-Double.parseDouble(CurrentCumGPAStr)*(Integer.parseInt(CurrentSemStr)-1);
        //calculating the min GPA by dividing the RemTotalGPA by RemSem
        minGPA=RemTotalGPA/RemSem;
        // Implement the calculation logic here
        return String.valueOf(minGPA); // Replace with actual calculation result
    }


}
