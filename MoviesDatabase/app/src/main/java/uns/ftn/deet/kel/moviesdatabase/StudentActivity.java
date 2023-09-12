package uns.ftn.deet.kel.moviesdatabase;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
                String selectedSubject = spnStudSubjects.getSelectedItem().toString();

                String[] str = selectedSubject.split("\\s+");
                Toast.makeText(StudentActivity.this, selectedSubject, Toast.LENGTH_SHORT).show();
                String subjName = str[0];
                String subjYear = str[1];
                txtObtainedPoints.setText(subjName + " " + subjYear);
                int subID = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);
                int stdID = databaseHelper.getStudentIDWithUserName(recUser);
                int obtainedPoints = databaseHelper.getObtainedPoints(stdID,subID);
                txtObtainedPoints.setText(Integer.toString(obtainedPoints));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Code to execute when nothing is selected
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
        for(Subject s: subjects){
            subjectsNames.add(s.getName()+" "+s.getYear());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectsNames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnStudSubjects.setAdapter(dataAdapter);
    }
}