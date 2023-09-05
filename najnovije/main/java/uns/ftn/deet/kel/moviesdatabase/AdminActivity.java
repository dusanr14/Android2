package uns.ftn.deet.kel.moviesdatabase;

import static uns.ftn.deet.kel.moviesdatabase.MainActivity.databaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Director;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;

public class AdminActivity extends AppCompatActivity {

    TextView txtUser;
    TextView txtPass;
    TextView txtStudName;
    TextView txtStudLastName;
    TextView txtStudJMBG;
    TextView txtStudIndex;
    Button btnAddAdmin;
    Button btnAddStudent;
    Spinner spnStudents;
    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);
        txtStudName = (TextView) findViewById(R.id.txtStudName);
        txtStudLastName = (TextView) findViewById(R.id.txtStudLastName);
        txtStudJMBG = (TextView) findViewById(R.id.txtStudJMBG);
        txtStudIndex = (TextView) findViewById(R.id.txtStudIndex);

        btnAddAdmin = (Button) findViewById(R.id.btnAddAdmin);
        spnStudents = (Spinner) findViewById(R.id.spnStudents);
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAdmin = txtUser.getText().toString();
                String passAdmin = txtPass.getText().toString();
                Admin a = new Admin(userAdmin,passAdmin);
                databaseHelper.createAdmin(a);
                Toast.makeText(AdminActivity.this, "Dodat administrator "+userAdmin, Toast.LENGTH_SHORT).show();
            }
        });
        btnAddStudent = (Button) findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studName = txtStudName.getText().toString();
                String studLastName = txtStudLastName.getText().toString();
                String studJMBG = txtStudJMBG.getText().toString();
                String studIndex = txtStudIndex.getText().toString();
                String studUsername = txtUser.getText().toString();
                String studPass = txtPass.getText().toString();
                Student s = new Student(studName, studLastName, studJMBG, studIndex, studUsername, studPass);
                databaseHelper.createStudent(s);
                Toast.makeText(AdminActivity.this, "Dodat administrator "+studName, Toast.LENGTH_SHORT).show();
            }
        });
        List<Student> ld = databaseHelper.getAllStudents();
        loadSpinnerStudents((ArrayList<Student>) ld);
    }

    void loadSpinnerStudents (ArrayList<Student> st){
        ArrayList<String> studentnames = new ArrayList<>();
        for (Student student : st){
            studentnames.add(student.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentnames);

        // Drop down layout
        // style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnStudents.setAdapter(dataAdapter);
    }
}