package com.example.studenthub;

import com.example.studenthub.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseManager {
    private static FirebaseDatabaseManager instance;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Student currentStudent;

    private FirebaseDatabaseManager() {
        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseDatabaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseManager();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }


    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    // Example method to sign in a user
    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    // Example method to register a new user
    public void signUp(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    // Method to sign out
    public void signOut() {
        firebaseAuth.signOut();
    }

    // Add student method
    public void addStudentDetails(Student student) {
        String userId = student.getUserId();
        DatabaseReference studentRef = databaseReference.child("Student").child(userId);
        studentRef.child("University").setValue(student.getUniversity());
        studentRef.child("Faculty").setValue(student.getFaculty());
        studentRef.child("Field").setValue(student.getField());
        studentRef.child("Year").setValue(student.getYear());
        studentRef.child("Semester").setValue(student.getSemester());
        studentRef.child("LastCumGPA").setValue(student.getLastCumGPA());
        studentRef.child("Name").setValue(student.getName());
        studentRef.child("RegNum").setValue(student.getRegNum());
        studentRef.child("Admin").setValue("0");

        // Cache the student details
        this.currentStudent = student;
    }

    // Method to retrieve admin data
    public void getAdminData(ValueEventListener listener) {
        databaseReference.child("Admin").addListenerForSingleValueEvent(listener);
    }

    // Method to check if student is registered
    public void isStudentRegistered(String userId, ValueEventListener listener) {
        databaseReference.child("Student").child(userId).addListenerForSingleValueEvent(listener);
    }

    // Method to retrieve student data
    public void getStudentData(String studentId, ValueEventListener listener) {
        databaseReference.child("Student").child(studentId).addListenerForSingleValueEvent(listener);
    }

    // Example method to add a student
    public void addStudent(String studentId, Student student) {
        databaseReference.child("students").child(studentId).setValue(student);
    }

    public DatabaseReference getStudentReference(String studentId) {
        return databaseReference.child("students").child(studentId);
    }

    public DatabaseReference getCoursesReference() {
        return databaseReference.child("courses");
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    // Add other methods to manage courses, admins, etc.
}
