package uns.ftn.deet.kel.moviesdatabase;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;
import java.util.ArrayList;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class StudentActivity extends AppCompatActivity {
    //DatabaseHelper databaseHelper = MainActivity.databaseHelper;
    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    TextView txtObtainedPoints;
    EditText txtInputYear;
    Button btnPassedNotPassed;
    Spinner spnPassedSubjects;
    Spinner spnNotPassedSubjects;
    Button btnBack;
    Spinner spnStudSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtStudName =  findViewById(R.id.txtAdminUser);
        txtStudLastName =  findViewById( R.id.txtAdminPass);
        txtIndex =  findViewById(R.id.txtIndex);
        txtJmbg =  findViewById(R.id.txtJmbg);
        txtObtainedPoints = findViewById(R.id.txtObtainedPoints);

        spnStudSubjects = findViewById(R.id.spnStudSubjects);
        spnNotPassedSubjects = findViewById(R.id.spnNotPassedSubjects);

        spnPassedSubjects = findViewById(R.id.spnPassedSubjects);
        txtInputYear = findViewById(R.id.txtInputYear);
        Intent receivedIntent = getIntent();
        String recUser = receivedIntent.getStringExtra("key_username");
        Toast.makeText(StudentActivity.this, "Cao, "+recUser, Toast.LENGTH_SHORT).show();

        int studID = databaseHelper.getStudentIDWithUserName(recUser);
        Student student = new Student();
        student = databaseHelper.getStudent(studID);
        txtStudName.setText(student.getName());
        txtStudLastName.setText(student.getLastName());
        txtIndex.setText(student.getIndex());
        txtJmbg.setText(student.getJmbg());
        loadSpinnerSubjects(student.getUserName());

        spnStudSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (spnStudSubjects.getSelectedItem() != null) {
                    String selectedSubject = spnStudSubjects.getSelectedItem().toString();

                    String[] str = selectedSubject.split("\\s+");
                    Toast.makeText(StudentActivity.this, selectedSubject, Toast.LENGTH_SHORT).show();
                    String subjName = str[0];
                    String subjYear = str[1];
                    txtObtainedPoints.setText(subjName + " " + subjYear);
                    int subID = databaseHelper.getSubjectIDWithNameAndYear(subjName, subjYear);
                    int stdID = databaseHelper.getStudentIDWithUserName(recUser);
                    int obtainedPoints = databaseHelper.getObtainedPoints(stdID, subID);
                    int grade = calculateGrade(obtainedPoints);
                    txtObtainedPoints.setText("Broj bodova: " + obtainedPoints + " Ocena: " + grade);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Code to execute when nothing is selected
            }
        });

        btnPassedNotPassed = (Button) findViewById(R.id.btnPassedNotPassed);
        btnPassedNotPassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studUserName = recUser;
                int studID = databaseHelper.getStudentIDWithUserName(studUserName);

                String inputYear = txtInputYear.getText().toString();

                ArrayList<Subject> subjects = databaseHelper.getAllSubjectsOfStudent(studUserName);
                ArrayList<Subject> passedSubjects = new ArrayList<Subject>();
                ArrayList<Subject> notPassedSubjects = new ArrayList<Subject>();
                for (Subject s: subjects) {
                    int obtainedPoints = databaseHelper.getObtainedPoints(studID, (int) s.getId());
                    if (obtainedPoints > 50 && obtainedPoints <= 100 && s.getYear().equals(inputYear)) {
                        passedSubjects.add(s);
                    } else if (obtainedPoints >= 0 && obtainedPoints <= 50 && s.getYear().equals(inputYear)) {
                        notPassedSubjects.add(s);
                    }
                }
                if(passedSubjects.size() == 0){
                    spnPassedSubjects.setAdapter(null);
                }
                if(notPassedSubjects.size() == 0){
                    spnNotPassedSubjects.setAdapter(null);
                }

                loadPassedSubjectsSpinner(passedSubjects);
                loadNotPassedSubjectsSpinner(notPassedSubjects);
            }
        });
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    void loadSpinnerSubjects (String userName){
        ArrayList<String> subjectsNames = new ArrayList<>();
        ArrayList<Subject> subjects = databaseHelper.getAllSubjectsOfStudent(userName);
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectsNames.add(s.getName() + " " + s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectsNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnStudSubjects.setAdapter(dataAdapter);
        }
    }

    int calculateGrade(int points){
        int grade = 5;
        if(points >50 && points <= 60){
            grade = 6;
        } else if(points >60 && points <= 70) {
            grade = 7;
        } else if(points >70 && points <= 80) {
            grade = 8;
        } else if(points >80 && points <= 90) {
            grade = 9;
        } else if(points >90 && points <= 100) {
            grade = 10;
        } else{
            grade = 5;
        }
        return grade;
    }

    void loadPassedSubjectsSpinner (ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectNames.add(s.getName()+ " "+ s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnPassedSubjects.setAdapter(dataAdapter);
        }
    }

    void loadNotPassedSubjectsSpinner (ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectNames.add(s.getName()+ " "+ s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnNotPassedSubjects.setAdapter(dataAdapter);
        }
    }
}