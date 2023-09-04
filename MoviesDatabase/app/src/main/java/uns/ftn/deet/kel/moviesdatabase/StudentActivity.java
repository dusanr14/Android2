package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class StudentActivity extends AppCompatActivity {
    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    Button btnShow;
    Spinner spnSubjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtStudName = (TextView) findViewById(R.id.txtAdminUser);
        txtStudLastName = (TextView) findViewById(R.id.txtAdminPass);
        txtIndex = (TextView) findViewById(R.id.txtIndex);
        txtJmbg = (TextView) findViewById(R.id.txtJmbg);
        btnShow = (Button) findViewById(R.id.btnAddAdmin);
        spnSubjects = (Spinner) findViewById(R.id.spnSubjects);
        Intent receivedIntent = getIntent();
        String recUser = receivedIntent.getStringExtra("key_username"); // Replace "key" with the same key used to send the data
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHelper.findAdmin("admin","admin")){
                    Toast.makeText(StudentActivity.this, "PUSSSSSSSS", Toast.LENGTH_SHORT).show();
                }
                int studID = databaseHelper.getStudentIDWithUserName(recUser);
                Student student = new Student();
                student = databaseHelper.getStudent(studID);
                txtStudName.setText(student.getName());
                txtStudLastName.setText(student.getLastName());
                txtIndex.setText(student.getIndex());
                txtJmbg.setText(student.getJmbg());
                loadSpinnerSubjects(databaseHelper.getAllSubjectsOfStudent(student.getName()));
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
            Toast.makeText(StudentActivity.this, "Prazno da ga jebes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(StudentActivity.this, "Ima nesto", Toast.LENGTH_SHORT).show();
        }
        for (Subject subject : su){
            subjectnames.add(subject.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, subjectnames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnSubjects.setAdapter(dataAdapter);
    }
}