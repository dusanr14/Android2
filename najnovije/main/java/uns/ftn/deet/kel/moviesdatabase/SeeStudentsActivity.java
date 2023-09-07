package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class SeeStudentsActivity extends AppCompatActivity {
    //DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    Button btnSeeStudent;
    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    Button btnBack;

    TextView txtStudUser;
    TextView txtStudPass;

    Spinner spnSubjects;
    Spinner spnStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_students);

        spnStudents = findViewById(R.id.spnStudents);
        txtStudUser = findViewById(R.id.txtStudUserName);
        txtStudPass = findViewById(R.id.txtStudPass);
        txtStudName =  findViewById(R.id.txtAdminUser);
        txtStudLastName =  findViewById( R.id.txtAdminPass);
        txtIndex =  findViewById(R.id.txtIndex);
        txtJmbg =  findViewById(R.id.txtJmbg);

        spnSubjects = findViewById(R.id.spnSubjects);

        Student student = new Student();

        ArrayList<Student> st = new ArrayList<>();
        st = databaseHelper.getAllStudents();
        loadSpinnerStudents(st);

        btnSeeStudent = (Button) findViewById(R.id.btnSeeStudent);
        btnSeeStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studUs = spnStudents.getSelectedItem().toString();
                ArrayList<Student> students= new ArrayList<>();
                Student stud = new Student();
                students = databaseHelper.getAllStudents();
                for(Student s: students){
                    if(s.getUserName().equals(studUs) ){
                        stud = s;
                        break;
                    }
                }
                txtStudUser.setText(stud.getUserName());
                txtStudPass.setText(stud.getPassword());
                txtStudName.setText(stud.getName());
                txtStudLastName.setText(stud.getLastName());
                txtIndex.setText(stud.getIndex());
                txtJmbg.setText(stud.getJmbg());
                loadSpinnerSubjects(stud.getUserName());
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeStudentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void loadSpinnerSubjects (String userName){
        ArrayList<Subject> subjects = new ArrayList<>();
        ArrayList<String> subjectsNames = new ArrayList<>();
        subjects = databaseHelper.getAllSubjectsOfStudent(userName);
        for(Subject s: subjects){
            subjectsNames.add(s.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectsNames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnSubjects.setAdapter(dataAdapter);
    }

    void loadSpinnerStudents(ArrayList<Student> st) {
        ArrayList<String> studentnames = new ArrayList<>();
        for (Student student : st) {
            studentnames.add(student.getUserName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentnames);

        // Drop down layout
        // style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnStudents.setAdapter(dataAdapter);
    }
}