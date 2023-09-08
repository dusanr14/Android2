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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.SubjectActivity;

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
    Button btnBackToMain;
    Button btnSubjects;
    Button btnSeeStudents;

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

        spnStudents = (Spinner) findViewById(R.id.spnStudents);

        boolean admin = databaseHelper.findAdmin("admin", "admin");
        btnAddAdmin = (Button) findViewById(R.id.btnAddAdmin);
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdmin();
            }
        });
        btnAddStudent = (Button) findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean studentExists = false;

                String studName = txtStudName.getText().toString();
                String studLastName = txtStudLastName.getText().toString();
                String studJMBG = txtStudJMBG.getText().toString();
                String studIndex = txtStudIndex.getText().toString();
                String studUsername = txtUser.getText().toString();
                String studPass = txtPass.getText().toString();
                Student s = new Student(studName, studLastName, studJMBG, studIndex, studUsername, studPass);
                ArrayList<Student> stud = databaseHelper.getAllStudents();
                ArrayList<Admin> admi = databaseHelper.getAllAdmins();
                for (Student st : stud) {
                    if (st.getUserName().equals(studUsername)) {
                        studentExists = true;
                        break;
                    }
                }
                for (Admin ad : admi) {
                    if (ad.getUserName().equals(studUsername)) {
                        studentExists = true;
                        break;
                    }
                }
                if (studJMBG.length() == 13 && !studentExists) {
                    databaseHelper.createStudent(s);
                    Toast.makeText(AdminActivity.this, "Dodat student " + studName, Toast.LENGTH_SHORT).show();
                    ;
                } else {
                    Toast.makeText(AdminActivity.this, "Neispravan unos JMBG-a, ili username zauzet", Toast.LENGTH_SHORT).show();
                }
                List<Student> ld = databaseHelper.getAllStudents();
                loadSpinnerStudents((ArrayList<Student>) ld);

            }
        });

        btnBackToMain = (Button) findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSubjects = (Button) findViewById(R.id.btnSubjects);
        btnSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, SubjectActivity.class);
                startActivity(intent);
            }
        });
        btnSeeStudents = (Button) findViewById(R.id.btnSeeStudents);
        btnSeeStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenta = new Intent(AdminActivity.this, SeeStudentsActivity.class);
                startActivity(intenta);
            }
        });
        List<Student> ld = databaseHelper.getAllStudents();
        loadSpinnerStudents((ArrayList<Student>) ld);
    }

    void loadSpinnerStudents(ArrayList<Student> st) {
        ArrayList<String> studentnames = new ArrayList<>();
        for (Student student : st) {
            studentnames.add(student.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentnames);

        // Drop down layout
        // style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnStudents.setAdapter(dataAdapter);
    }

    public void addAdmin() {
        boolean adminExists = false;

        String userAdmin = txtUser.getText().toString();
        String passAdmin = txtPass.getText().toString();
        Admin a = new Admin(userAdmin, passAdmin);
        ArrayList<Admin> admins = databaseHelper.getAllAdmins();
        ArrayList<Student> students = databaseHelper.getAllStudents();
        for (Admin ad : admins) {
            if (ad.getUserName().equals(userAdmin)) {
                adminExists = true;
                break;
            }
        }
        for (Student st : students) {
            if (st.getUserName().equals(userAdmin)) {
                adminExists = true;
                break;
            }
        }
        if (adminExists == false) {
            databaseHelper.createAdmin(a);
            Toast.makeText(AdminActivity.this, "Dodat administrator " + userAdmin, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminActivity.this, "Neuspesno dodavanje administratora. Username zauzet " + userAdmin, Toast.LENGTH_SHORT).show();
        }


    }

}