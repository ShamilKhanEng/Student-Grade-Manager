package com.example.studenthub.Model;

public class Student {
    private String userId;
    private String university;
    private String faculty;
    private String field;
    private String year;
    private String semester;
    private String lastCumGPA;
    private String name;
    private String regNum;

    private Student(Builder builder) {
        this.userId = builder.userId;
        this.university = builder.university;
        this.faculty = builder.faculty;
        this.field = builder.field;
        this.year = builder.year;
        this.semester = builder.semester;
        this.lastCumGPA = builder.lastCumGPA;
        this.name = builder.name;
        this.regNum = builder.regNum;
    }

    public static class Builder {
        private String userId;
        private String university;
        private String faculty;
        private String field;
        private String year;
        private String semester;
        private String lastCumGPA;
        private String name;
        private String regNum;

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUniversity(String university) {
            this.university = university;
            return this;
        }

        public Builder setFaculty(String faculty) {
            this.faculty = faculty;
            return this;
        }

        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        public Builder setYear(String year) {
            this.year = year;
            return this;
        }

        public Builder setSemester(String semester) {
            this.semester = semester;
            return this;
        }

        public Builder setLastCumGPA(String lastCumGPA) {
            this.lastCumGPA = lastCumGPA;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRegNum(String regNum) {
            this.regNum = regNum;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    // Getters for all fields
    public String getUserId() {
        return userId;
    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getField() {
        return field;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public String getLastCumGPA() {
        return lastCumGPA;
    }

    public String getName() {
        return name;
    }

    public String getRegNum() {
        return regNum;
    }
}
