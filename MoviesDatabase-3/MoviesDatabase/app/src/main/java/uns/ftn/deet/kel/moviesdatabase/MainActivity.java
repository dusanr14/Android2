package uns.ftn.deet.kel.moviesdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.sqlite.helper.DatabaseHelper;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Director;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Movie;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;
public class MainActivity extends AppCompatActivity {
    // Database Helper
    DatabaseHelper databaseHelper;
    Spinner spnActors;
    Spinner spnDirectors;
    Spinner spnMovies;
    Button btnDeleteDatabase;
    Button btnFindActors;
    Button btnRestoreDatabase;

    EditText txtUserName;
    EditText txtPassword;
    Button btnLogIn;
    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.closeDB();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnActors = (Spinner) findViewById(R.id.spnActors);
        spnDirectors = (Spinner) findViewById(R.id.spnDirectors);
        spnMovies = (Spinner) findViewById(R.id.spnMovies);

        btnDeleteDatabase = (Button) findViewById(R.id.btnDeleteDatabase);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogIn = (Button) findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHelper.findAdmin(txtUserName.getText().toString(),txtPassword.getText().toString())){
                    Toast.makeText(MainActivity.this, "Uspesno logovanje", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Neuspesno logovanje "+txtUserName.getText().toString()+" "+txtPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        );
        btnDeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper != null) {
                    databaseHelper.dropTables();
                    loadSpinnerDataActors(new ArrayList<Actor>());
                    loadSpinnerDataDirectors(new ArrayList<>());
                    loadSpinnerDataMovies(new ArrayList<>());
                }

            }
        }

        );

        btnFindActors = (Button) findViewById(R.id.btnActorsInMovie);
        btnFindActors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spnMovies.getSelectedItem() != null) {
                    String movie = spnMovies.getSelectedItem().toString();
                    List<Actor> lst = databaseHelper.getAllActorsInMovie(movie);
                    loadSpinnerDataActors((ArrayList<Actor>) lst);
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
        databaseHelper = new DatabaseHelper(getApplicationContext());

        createTablesAndInitData();

    }

    void createTablesAndInitData(){
        databaseHelper.createTables();

        if (databaseHelper.getAllActors().size() == 0) {
            Admin admin1 = new Admin("admin", "admin");

            databaseHelper.createAdmin(admin1);
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

            databaseHelper.createActor(a1);
            databaseHelper.createActor(a2);
            databaseHelper.createActor(a3);
            databaseHelper.createActor(a4);
            databaseHelper.createActor(a5);
            databaseHelper.createActor(a6);
            databaseHelper.createDirector(d1);
            databaseHelper.createDirector(d2);
            databaseHelper.createDirector(d3);
            databaseHelper.createDirector(d4);
            Movie m1 = new Movie("Pulp fiction", "23-09-1994");
            m1.setDirector(d1);
            databaseHelper.createMovie(m1);
            m1.addActor(a4);
            m1.addActor(a3);
            databaseHelper.addActorsInMovie(m1);
            Movie m2 = new Movie("Fight club", "15-10-1999");
            m2.setDirector(d3);
            databaseHelper.createMovie(m2);
            m2.addActor(a1);
            m2.addActor(a2);
            databaseHelper.addActorsInMovie(m2);
            Movie m3 = new Movie("12 monkeys", "29-12-1995");
            m3.setDirector(d4);
            databaseHelper.createMovie(m3);
            m3.addActor(a1);
            m3.addActor(a4);
            databaseHelper.addActorsInMovie(m3);
            Movie m4 = new Movie("Django Unchained", "11-12-2012");
            m4.setDirector(d1);
            databaseHelper.createMovie(m4);
            m4.addActor(a3);
            m4.addActor(a5);
            databaseHelper.addActorsInMovie(m4);
            Movie m5 = new Movie("Shutter island", "13-02-2010");
            m5.setDirector(d2);
            databaseHelper.createMovie(m5);
            m5.addActor(a5);
            databaseHelper.addActorsInMovie(m5);
            Movie m6 = new Movie("The departed", "26-09-2006");
            m6.setDirector(d2);
            databaseHelper.createMovie(m6);
            m6.addActor(a5);
            m6.addActor(a6);
            databaseHelper.addActorsInMovie(m6);
            Movie m7 = new Movie("Once upon a time in Hollywood", "21-05-2019");
            m7.setDirector(d1);
            databaseHelper.createMovie(m7);
            m7.addActor(a1);
            m7.addActor(a5);
            databaseHelper.addActorsInMovie(m7);
        }

        List<Actor> la = databaseHelper.getAllActors();
        loadSpinnerDataActors((ArrayList<Actor>) la);
        List<Director> ld = databaseHelper.getAllDirectors();
        loadSpinnerDataDirectors((ArrayList<Director>) ld);
        List<Movie> mv = databaseHelper.getAllMovies();
        loadSpinnerDataMovies((ArrayList<Movie>) mv);

    }
    void loadSpinnerDataActors (ArrayList<Actor> al){
        ArrayList<String> actornames = new ArrayList<>();
        for (Actor actor : al){
            actornames.add(actor.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, actornames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnActors.setAdapter(dataAdapter);
    }

    void loadSpinnerDataDirectors (ArrayList<Director> al){
        ArrayList<String> directornames = new ArrayList<>();
        for (Director director : al){
            directornames.add(director.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, directornames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnDirectors.setAdapter(dataAdapter);
    }

    void loadSpinnerDataMovies (ArrayList<Movie> al){
        ArrayList<String> movienames = new ArrayList<>();
        for (Movie movie : al){
            movienames.add(movie.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movienames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnMovies.setAdapter(dataAdapter);
    }



}