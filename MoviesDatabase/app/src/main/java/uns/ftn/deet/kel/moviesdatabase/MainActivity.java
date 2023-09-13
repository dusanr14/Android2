package uns.ftn.deet.kel.moviesdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Director;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Movie;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;

public class MainActivity extends AppCompatActivity {
    // Database Helper
    static public DatabaseHelper databaseHelper;

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogIn;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Spinner spnActors;
    Spinner spnDirectors;
    Button btnDeleteDatabase;
    Button btnRestoreDatabase;
    Button btnExit;
    @Override
    protected void onStop() {
        super.onStop();
        //databaseHelper.closeDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                boolean adminAdmin = (txtUserName.getText().toString().equals("admin") && txtPassword.getText().equals("admin"));
                if (databaseHelper.findAdmin(txtUserName.getText().toString(), txtPassword.getText().toString()) || adminAdmin) {
                    Toast.makeText(MainActivity.this, "Uspesno logovanje: Admin", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else if (databaseHelper.findStudent(txtUserName.getText().toString(), txtPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Uspesno logovanje: Student", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                    intent.putExtra("key_username", txtUserName.getText().toString()); // Replace "key" with a meaningful identifier and "value" with the actual data you want to pass
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Neuspesno logovanje " + txtUserName.getText().toString() + " " + txtPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
         });

        spnActors = (Spinner) findViewById(R.id.spnActors);
        spnDirectors = (Spinner) findViewById(R.id.spnDirectors);

        btnDeleteDatabase = (Button) findViewById(R.id.btnDeleteDatabase);
        btnDeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper != null) {
                    databaseHelper.dropTables();
                    loadSpinnerDataActors(new ArrayList<Admin>());
                    loadSpinnerStudents(new ArrayList<>());
                }

            }
        });

        btnRestoreDatabase = (Button) findViewById(R.id.btnRestoreDatabase);
        btnRestoreDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTablesAndInitData();
            }
        });

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.closeDB();
                finish();
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());

        createTablesAndInitData();

    }

    void createTablesAndInitData(){
        databaseHelper.createTables();

        if (databaseHelper.getAllStudents().size() == 0) {

            Admin admin1 = new Admin("admin","admin");
            Admin admin2 = new Admin("admo","admii");

            Student s1 = new Student("Aleksa", "Aleksic", "e1-2018", "0101999800068", "alek", "goku123");
            Student s2 = new Student("Petar", "Petrovic", "e1-2017", "0102998810062", "pero", "asdf123");

            databaseHelper.createAdmin(admin1);
            databaseHelper.createAdmin(admin2);
            databaseHelper.createStudent(s1);
            databaseHelper.createStudent(s2);

            Subject subject1 = new Subject("RSZEOS","2022/2023");
            Subject subject2 = new Subject("MPS","2022/2023");
            databaseHelper.createSubject(subject1);
            databaseHelper.createSubject(subject2);
            //databaseHelper.createSubject(subject2);
            subject1.addStudent(s1);
            subject2.addStudent(s1);
            subject2.addStudent(s2);
            databaseHelper.addStudentsInSubject(subject1);
            databaseHelper.addStudentsInSubject(subject2);

            //subject1.addStudent(s1);
            //subject1.addStudent(s2);
            //databaseHelper.addStudentsInSubject(subject1);

            ////////////////////////////////////////////////////////////////////////////////////////
            Actor a1 = new Actor("Brad Pitt", "18-12-1963");
            Actor a2 = new Actor("Edward Norton", "18-08-1969");
            Actor a3 = new Actor("Samuel L. Jackson", "21-12-1948");
            Actor a4 = new Actor("Bruce Willis", "19-03-1955");
            Actor a5 = new Actor("Leonardo DiCaprio", "11-11-1974");
            Actor a6 = new Actor("Matt Damon", "08-10-1970");
            Director d1 = new Director("Quentin Tarantino", "27-03-1963");
            Director d2 = new Director("Martin Scorsese", "17-11-1942");
            Director d3 = new Director("David Fincher", "28-08-1962");
            Director d4 = new Director("Terry Gilliam", "22-11-1940");


        }

        List<Admin> ad = databaseHelper.getAllAdmins();
        loadSpinnerDataActors((ArrayList<Admin>) ad);
        List<Student> ld = databaseHelper.getAllStudents();
        loadSpinnerStudents((ArrayList<Student>) ld);

    }
    void loadSpinnerDataActors (ArrayList<Admin> ad){
        ArrayList<String> adminnames = new ArrayList<>();
        for (Admin admin : ad){
            adminnames.add(admin.getUserName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, adminnames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnActors.setAdapter(dataAdapter);
    }

    void loadSpinnerStudents (ArrayList<Student> st){
        ArrayList<String> studentnames = new ArrayList<>();
        for (Student student : st){
            studentnames.add(student.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentnames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnDirectors.setAdapter(dataAdapter);
    }



}