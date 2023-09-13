package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class SearchActivity extends AppCompatActivity {

    Button btnBackToAdmin;
    Spinner spnAllStudents;
    Spinner spnAllSubjects;
    EditText txtGrade;
    Button btnStudentSubject;
    Button btnStudentSubjectPassed;
    Button btnStudentSubjectGrade;
    Button btnSubjectsStudentPassed;
    Button btnSubjectsStudentNotPassed;
    Button btnDeleteSelectedStudent;
    Spinner spnAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spnAllStudents = findViewById(R.id.spnAllStudents);

        spnAllSubjects = findViewById(R.id.spnAllSubjects);

        spnAnswer = findViewById(R.id.spnAnswer);

        txtGrade = findViewById(R.id.txtGrade);

        //Pretrazi sve studente koji pohadjaju izabranui predmet
        btnStudentSubject = (Button) findViewById(R.id.btnStudentSubject);
        btnStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectNameYear = spnAllSubjects.getSelectedItem().toString();
                String[] str = subjectNameYear.split("\\s+");
                String subjName = str[0];
                String subjYear = str[1];

                int subjID = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);

                List<Student> studentList = databaseHelper.getAllStudentsInSubject(subjID);
                ArrayList<Student> students = ((ArrayList<Student>)studentList);

                if(students.size() == 0){
                    spnAnswer.setAdapter(null);
                }

                loadAnswerSpinnerWithStudents(students);
            }
        });

        //Pretrazi sve studente koji su polozili izabrani predmet
        btnStudentSubjectPassed = (Button) findViewById(R.id.btnStudentSubjectPassed);
        btnStudentSubjectPassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectNameYear = spnAllSubjects.getSelectedItem().toString();
                String[] str = subjectNameYear.split("\\s+");
                String subjName = str[0];
                String subjYear = str[1];
                int subjID = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);

                List<Student> studentList = databaseHelper.getAllStudentsInSubject(subjID);
                ArrayList<Student> students = ((ArrayList<Student>)studentList);

                ArrayList<Student> studentsPassed = new ArrayList<Student>();

                for(Student s: students){
                    int obtainedPoints = databaseHelper.getObtainedPoints((int)s.getId(),subjID);
                    if(obtainedPoints > 50 && obtainedPoints < 101){
                        studentsPassed.add(s);
                    }
                }

                if(studentsPassed.size() == 0){
                    spnAnswer.setAdapter(null);
                }
                loadAnswerSpinnerWithStudents(studentsPassed);
            }
        });

        // Vrati sve studente koji su polozili predmet sa unetom ocenom
        btnStudentSubjectGrade = (Button) findViewById(R.id.btnStudentSubjectGrade);
        btnStudentSubjectGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectNameYear = spnAllSubjects.getSelectedItem().toString();
                String[] str = subjectNameYear.split("\\s+");

                String subjName = str[0];
                String subjYear = str[1];
                int subjID = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);

                List<Student> studentList = databaseHelper.getAllStudentsInSubject(subjID);
                ArrayList<Student> students = ((ArrayList<Student>)studentList);

                ArrayList<Student> studentsPassed = new ArrayList<Student>();

                int inputGrade = 0;
                try {
                    inputGrade = Integer.parseInt(txtGrade.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(SearchActivity.this, "Nije validan unos ocene", Toast.LENGTH_SHORT).show();
                }

                for(Student s: students){
                    int obtainedPoints = databaseHelper.getObtainedPoints((int)s.getId(),subjID);
                    int grade = calculateGrade(obtainedPoints);

                    if( obtainedPoints > 50 && obtainedPoints < 101 && grade == inputGrade){
                        studentsPassed.add(s);
                    }
                }

                if(studentsPassed.size() == 0){
                    spnAnswer.setAdapter(null);
                }
                loadAnswerSpinnerWithStudents(studentsPassed);
            }
        });

        // Vrati sve predmete koji je odabrani student polozio
        btnSubjectsStudentPassed = (Button) findViewById(R.id.btnSubjectsStudentPassed);
        btnSubjectsStudentPassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studUserName = spnAllStudents.getSelectedItem().toString();
                int studID = databaseHelper.getStudentIDWithUserName(studUserName);
                ArrayList<Subject> subjects = databaseHelper.getAllSubjectsOfStudent(studUserName);
                ArrayList<Subject> passedSubjects = new ArrayList<Subject>();
                for (Subject s: subjects){
                    int obtainedPoints = databaseHelper.getObtainedPoints(studID,(int)s.getId());
                    if(obtainedPoints > 50 && obtainedPoints <= 100){
                        passedSubjects.add(s);
                    }
                }

                if(passedSubjects.size() == 0){
                    spnAnswer.setAdapter(null);
                }
            loadAnswerSpinnerWithSubjects(passedSubjects);
            }
        });

        // Vrati sve predmete koji je odabrani student polozio
        btnSubjectsStudentNotPassed = (Button) findViewById(R.id.btnSubjectsStudentNotPassed);
        btnSubjectsStudentNotPassed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studUserName = spnAllStudents.getSelectedItem().toString();
                int studID = databaseHelper.getStudentIDWithUserName(studUserName);
                ArrayList<Subject> subjects = databaseHelper.getAllSubjectsOfStudent(studUserName);
                ArrayList<Subject> notPassedSubjects = new ArrayList<Subject>();
                for (Subject s: subjects){
                    int obtainedPoints = databaseHelper.getObtainedPoints(studID,(int)s.getId());
                    if(obtainedPoints >= 0 && obtainedPoints <= 50){
                        notPassedSubjects.add(s);
                    }
                }

                if(subjects.size() == 0){
                    spnAnswer.setAdapter(null);
                }
                loadAnswerSpinnerWithSubjects(notPassedSubjects);
            }
        });


        btnDeleteSelectedStudent = (Button) findViewById(R.id.btnDeleteSelectedStudent);
        btnDeleteSelectedStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studUserName = spnAllStudents.getSelectedItem().toString();
                int studID = databaseHelper.getStudentIDWithUserName(studUserName);
                Toast.makeText(SearchActivity.this, "Student "+ studUserName + " obrisan.", Toast.LENGTH_SHORT).show();
                databaseHelper.deleteStudent(studID);
                loadSpinnerAllStudents ();
                loadSpinnerAllSubjects ();
            }
        });

        btnBackToAdmin = (Button) findViewById(R.id.btnBackToAdmin);
        btnBackToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        loadSpinnerAllStudents ();
        loadSpinnerAllSubjects ();
    }

    void loadSpinnerAllStudents (){
        ArrayList<String> subjectsNames = new ArrayList<>();
        ArrayList<Student> students = databaseHelper.getAllStudents();
        if(students.size() != 0) {
            for (Student s : students) {
                subjectsNames.add(s.getUserName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectsNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnAllStudents.setAdapter(dataAdapter);
        }
    }

    void loadSpinnerAllSubjects (){
        ArrayList<String> subjectsNames = new ArrayList<>();
        ArrayList<Subject> subjects = databaseHelper.getAllSubjects();
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectsNames.add(s.getName()+ " "+ s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectsNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnAllSubjects.setAdapter(dataAdapter);
        }
    }

    void loadAnswerSpinnerWithStudents (ArrayList<Student> students){
        ArrayList<String> studentNames = new ArrayList<>();
        if(students.size() != 0) {
            for (Student s : students) {
                studentNames.add(s.getName()+ " "+ s.getLastName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnAnswer.setAdapter(dataAdapter);
        }
    }

    void loadAnswerSpinnerWithSubjects (ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        if(subjects.size() != 0) {
            for (Subject s : subjects) {
                subjectNames.add(s.getName()+ " "+ s.getYear());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectNames);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spnAnswer.setAdapter(dataAdapter);
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
}



//    String studUserName = spnAllStudents.getSelectedItem().toString();
//    String subjectNameYear = spnAllSubjects.getSelectedItem().toString();
//    String[] str = subjectNameYear.split("\\s+");
//    String subjName = str[0];
//    String subjYear = str[1];
//    int studID = databaseHelper.getStudentIDWithUserName(studUserName);
//    int subjID = databaseHelper.getSubjectIDWithNameAndYear(subjName,subjYear);