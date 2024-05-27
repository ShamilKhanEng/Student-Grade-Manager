package com.example.studenthub;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
    public static final HashMap<String, String> GPA_GRADE_MARKS;
    public static final ArrayList<String> GPAlist;



    static {
        GPA_GRADE_MARKS = new HashMap<>();
        GPA_GRADE_MARKS.put("4.0", "80");
        GPA_GRADE_MARKS.put("3.7", "75");
        GPA_GRADE_MARKS.put("3.3", "70");
        GPA_GRADE_MARKS.put("3.0", "65");
        GPA_GRADE_MARKS.put("2.7", "60");
        GPA_GRADE_MARKS.put("2.3", "55");
        GPA_GRADE_MARKS.put("2.0", "50");
        GPA_GRADE_MARKS.put("1.7", "45");
        GPA_GRADE_MARKS.put("1.3", "40");
        GPA_GRADE_MARKS.put("1.0", "35");

        GPAlist = new ArrayList<>();
        GPAlist.add("4.0");
        GPAlist.add("3.7");
        GPAlist.add("3.3");
        GPAlist.add("3.0");
        GPAlist.add("2.7");
        GPAlist.add("2.3");
        GPAlist.add("2.0");
        GPAlist.add("1.3");
        GPAlist.add("1.0");
    }



}

