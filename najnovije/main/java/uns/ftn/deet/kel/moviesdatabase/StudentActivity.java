package uns.ftn.deet.kel.moviesdatabase;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;
import java.util.ArrayList;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class StudentActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;
    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    Button btnBack;
    Spinner spnSubjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtStudName =  findViewById(R.id.txtAdminUser);
        txtStudLastName =  findViewById( R.id.txtAdminPass);
        txtIndex =  findViewById(R.id.txtIndex);
        txtJmbg =  findViewById(R.id.txtJmbg);

        spnSubjects = (Spinner) findViewById(R.id.spnStudents);
        Intent receivedIntent = getIntent();
        String recUser = receivedIntent.getStringExtra("key_username");
        Toast.makeText(StudentActivity.this, "Cao, "+recUser, Toast.LENGTH_SHORT).show();
        if(databaseHelper.findAdmin("admin","admin")){
            Toast.makeText(StudentActivity.this, "Ima nesto", Toast.LENGTH_SHORT).show();
        }
        int studID = databaseHelper.getStudentIDWithUserName(recUser);
        Student student = new Student();
        student = databaseHelper.getStudent(studID);
        txtStudName.setText(student.getName());
        txtStudLastName.setText(student.getLastName());
        txtIndex.setText(student.getIndex());
        txtJmbg.setText(student.getJmbg());
        loadSpinnerSubjects(databaseHelper.getAllSubjectsOfStudent(student.getName()));

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    void loadSpinnerSubjects (ArrayList<Subject> su){
        ArrayList<String> subjectnames = new ArrayList<>();
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        ArrayList<Student> students = new ArrayList<Student>();
        students = databaseHelper.getAllStudents();
        subjects = databaseHelper.getAllSubjects();
        if (su.isEmpty()){
            Toast.makeText(StudentActivity.this, "Prazno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(StudentActivity.this, "Ima nesto", Toast.LENGTH_SHORT).show();
        }
        for (Subject subject : su){
            subjectnames.add(subject.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, subjectnames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnSubjects.setAdapter(dataAdapter);
    }
}