package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class SeeStudentsActivity extends AppCompatActivity {
    //DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtIndex;
    TextView txtJmbg;
    Button btnBack;
    Button btnAddSubjectToStudent;
    TextView txtStudPass;

    Spinner spnSubjects;
    TextView txtObtainedPontsGrade;
    EditText txtUpdatePoints;
    Button btnUpdatePoints;
    Spinner spnStudents;
    Spinner spnAddSubjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_students);

        spnStudents = findViewById(R.id.spnStudSubjects);
        spnStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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
                txtStudPass.setText(stud.getPassword());
                txtStudName.setText(stud.getName());
                txtStudLastName.setText(stud.getLastName());
                txtIndex.setText(stud.getIndex());
                txtJmbg.setText(stud.getJmbg());
                loadSpinnerSubjects(stud.getUserName());
                if(spnSubjects.getSelectedItem() != null)
                    updatePointsAndGrade();
                else
                    txtObtainedPontsGrade.setText(" ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Code to execute when nothing is selected
            }
        });
        txtStudPass = findViewById(R.id.txtStudPass);
        txtStudName =  findViewById(R.id.txtAdminUser);
        txtStudLastName =  findViewById( R.id.txtAdminPass);
        txtIndex =  findViewById(R.id.txtIndex);
        txtJmbg =  findViewById(R.id.txtJmbg);
        txtObtainedPontsGrade =  findViewById(R.id.txtObtainedPontsGrade);
        txtUpdatePoints = findViewById(R.id.txtUpdatePoints);
        btnUpdatePoints = findViewById(R.id.btnUpdatePoints);

        spnAddSubjects = findViewById(R.id.spnAddSubjects);
        Student student = new Student();

        ArrayList<Student> st = new ArrayList<>();
        st = databaseHelper.getAllStudents();
        loadSpinnerStudents(st);
        loadSpinnerAddSubjects();

        spnSubjects = findViewById(R.id.spnSubjects);
        spnSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spnSubjects.getSelectedItem() != null)
                updatePointsAndGrade();
                else
                    txtObtainedPontsGrade.setText(" ");
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
                Intent intent = new Intent(SeeStudentsActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        btnAddSubjectToStudent = (Button) findViewById(R.id.btnAddSubjectToStudent);
        btnAddSubjectToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean subjectExists = false;
                String subjectNameYear = spnAddSubjects.getSelectedItem().toString();
                String[] str = subjectNameYear.split("\\s+");
                String subjName = str[0];
                String subjYear = str[1];
                ArrayList<Subject> allSubjects= databaseHelper.getAllSubjects();
                ArrayList<Subject> studentSubjects= databaseHelper.getAllSubjectsOfStudent(spnStudents.getSelectedItem().toString());
                int id = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);
                Subject selectedSubject = databaseHelper.getSubject(id);
                // check if subject is already assigned to student
                for(Subject s: studentSubjects){
                    if(s.getName().equals(subjName) && s.getYear().equals(subjYear)){
                        subjectExists = true;
                    }
                }
                Student selectedStudent = databaseHelper.getStudent(databaseHelper.getStudentIDWithUserName(spnStudents.getSelectedItem().toString()));

                if(!subjectExists) {
                    selectedSubject.addStudent(selectedStudent);
                    databaseHelper.addStudentsInSubject(selectedSubject);
                    Toast.makeText(SeeStudentsActivity.this, "Predmet " +selectedSubject.getName()+ " je dodat studentu" + selectedStudent.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SeeStudentsActivity.this, "Odabrani predmet odabrani student vec pohadja ", Toast.LENGTH_SHORT).show();
                }
                loadSpinnerSubjects(spnStudents.getSelectedItem().toString());
            }
        });

        btnUpdatePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spnSubjects.getSelectedItem() != null) {
                    String selectedSubject = spnSubjects.getSelectedItem().toString();

                    String[] str = selectedSubject.split("\\s+");
                    //Toast.makeText(SeeStudentsActivity.this, selectedSubject, Toast.LENGTH_SHORT).show();
                    String subjName = str[0];
                    String subjYear = str[1];
                    String studUs = spnStudents.getSelectedItem().toString();

                    int subID = databaseHelper.getSubjectIDWithNameAndYear(subjName, subjYear);
                    int stdID = databaseHelper.getStudentIDWithUserName(studUs);

                    String obtainedPointsString = txtUpdatePoints.getText().toString();

                    int obtainedPoints = 0;
                    Toast.makeText(SeeStudentsActivity.this, "Obrained poins: " + obtainedPointsString, Toast.LENGTH_SHORT).show();
                    try {
                        obtainedPoints = Integer.parseInt(obtainedPointsString);
                    } catch (NumberFormatException e) {
                        Toast.makeText(SeeStudentsActivity.this, "Nije validan unos ", Toast.LENGTH_SHORT).show();
                    }
                    databaseHelper.updateObtainedPoints(stdID, subID, obtainedPoints);
                    updatePointsAndGrade();
                }
            }
        });
    }

    void loadSpinnerAddSubjects (){
        ArrayList<String> subjectsNames = new ArrayList<>();
        ArrayList<Subject> subjects = databaseHelper.getAllSubjects();
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectsNames.add(s.getName() + " " + s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectsNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnAddSubjects.setAdapter(dataAdapter);
        }
    }
    void loadSpinnerSubjects (String userName){
        ArrayList<Subject> subjects = new ArrayList<>();
        ArrayList<String> subjectsNames = new ArrayList<>();
        subjects = databaseHelper.getAllSubjectsOfStudent(userName);
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectsNames.add(s.getName() + " " + s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectsNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnSubjects.setAdapter(dataAdapter);
        } else {
            spnSubjects.setAdapter(null);
        }
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

    void updatePointsAndGrade(){
        String selectedSubject = spnSubjects.getSelectedItem().toString();

        if(selectedSubject != null) {
            String[] str = selectedSubject.split("\\s+");
            String subjName = str[0];
            String subjYear = str[1];
            String studUs = spnStudents.getSelectedItem().toString();
            int subID = databaseHelper.getSubjectIDWithNameAndYear(subjName, subjYear);
            int stdID = databaseHelper.getStudentIDWithUserName(studUs);
            int obtainedPoints = databaseHelper.getObtainedPoints(stdID, subID);

            int grade = calculateGrade(obtainedPoints);
            txtObtainedPontsGrade.setText("Osvojeo bodova: " + Integer.toString(obtainedPoints) + " Ocena: " + Integer.toString(grade));
        } else {
            Toast.makeText(SeeStudentsActivity.this, "Student ne pohadja ni jedan predmet", Toast.LENGTH_SHORT).show();
        }
    }
}