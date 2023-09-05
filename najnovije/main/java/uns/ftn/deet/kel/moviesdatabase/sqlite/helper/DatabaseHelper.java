package uns.ftn.deet.kel.moviesdatabase.sqlite.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uns.ftn.deet.kel.moviesdatabase.MainActivity;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Actor;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Director;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Movie;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Admin;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Student;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.Subject;
import uns.ftn.deet.kel.moviesdatabase.sqlite.model.SubjectPart;

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MoviesManager";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    // Table Names
    /*************************************************************/  /***/
    private static final String TABLE_ACTORS = "actors";             /***/
    private static final String TABLE_DIRECTORS = "directors";       /***/
    private static final String TABLE_MOVIES = "movies";             /***/
    private static final String TABLE_MOVIE_ACTORS = "movie_actors"; /***/
    /*************************************************************/  /***/
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_ADMINS = "admins";
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String TABLE_PARTS = "parts";
    private static final String TABLE_STUDENTS_SUBJECTS = "students_subjects";
    private static final String TABLE_STUDENTS_PARTS = "students_parts";
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BIRTHDATE = "birth_date";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    // STUDENTS Table
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_INDEX = "stud_index";
    private static final String KEY_JMBG = "jmbg";
    // ADMIN Table -- in common section
    // SUBJECT Table
    private static final String KEY_YEAR = "key_year";
    // PART Table
    private static final String KEY_MIN_POINTS = "min_points";
    private static final String KEY_MAX_POINTS = "max_points";
    // PART_STUDENTS Table, STUDENTS_SUBJECTS Table
    private static final String KEY_OBTAINED_POINTS = "obtained_points";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_PART_ID = "part_id";
    private static final String KEY_SUBJECT_ID = "subject_id";
    // MOVIES Table - column names
    private static final String KEY_DIRECTOR_ID = "director_id";
    private static final String KEY_RELEASEDATE = "release_date";

    //MOVIE_ACTORS Table - column names
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_ACTOR_ID = "actor_id";

    // Table Create Statements
    // Students table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME  + " TEXT," +
            KEY_LAST_NAME + " TEXT," +
            KEY_INDEX + " TEXT," +
            KEY_JMBG + " TEXT," +
            KEY_USERNAME + " TEXT," +
            KEY_PASSWORD + " TEXT" +")";
    // Subjects table create statement
    private static final String CREATE_TABLE_SUBJECTS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_SUBJECTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME  + " TEXT," +
            KEY_YEAR + " TEXT" + ")";
    // Admins table create statement
    private static final String CREATE_TABLE_ADMINS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADMINS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_USERNAME  + " TEXT," +
            KEY_PASSWORD  + " TEXT" +")";
    // Parts table create statement
    private static final String CREATE_TABLE_PARTS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_PARTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME  + " TEXT," +
            KEY_MIN_POINTS  + " INTEGER," +
            KEY_MAX_POINTS  + " INTEGER," +
            KEY_SUBJECT_ID + " INTEGER" +")";
    private static final String CREATE_TABLE_STUDENTS_SUBJECTS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS_SUBJECTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_STUDENT_ID  + " INTEGER," +
            KEY_SUBJECT_ID  + " INTEGER" +")";
    private static final String CREATE_TABLE_STUDENTS_PARTS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS_PARTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_STUDENT_ID  + " INTEGER," +
            KEY_PART_ID  + " INTEGER," +
            KEY_OBTAINED_POINTS  + " INTEGER" +")";
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Actors table create statement
    private static final String CREATE_TABLE_ACTORS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ACTORS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_BIRTHDATE + " TEXT" + ")";

    // Directors table create statement
    private static final String CREATE_TABLE_DIRECTORS = "CREATE TABLE IF NOT EXISTS " + TABLE_DIRECTORS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_BIRTHDATE + " TEXT" + ")";

    // Movies table create statement
    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MOVIES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT," + KEY_DIRECTOR_ID + " INTEGER,"
            + KEY_RELEASEDATE + " TEXT" + ")";

    // Movie_actors table create statement
    private static final String CREATE_TABLE_MOVIE_ACTORS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MOVIE_ACTORS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_MOVIE_ID + " INTEGER,"
            + KEY_ACTOR_ID + " INTEGER" + ")";
    /******************************************************************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_PARTS);
        db.execSQL(CREATE_TABLE_STUDENTS_SUBJECTS);
        db.execSQL(CREATE_TABLE_STUDENTS_PARTS);
        db.execSQL(CREATE_TABLE_ADMINS);
        ///////////////////////////////////////
        db.execSQL(CREATE_TABLE_ACTORS);
        db.execSQL(CREATE_TABLE_DIRECTORS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_MOVIE_ACTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        //////////////////////////////////////////////////////////////
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIRECTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE_ACTORS);

        // create new tables
        onCreate(db);
    }

    //This to be used when needed to create tables from scratch (maybe after tables are deleted)
    public void createTables() {
        if (db == null)
            db = getWritableDatabase();

        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_PARTS);
        db.execSQL(CREATE_TABLE_STUDENTS_SUBJECTS);
        db.execSQL(CREATE_TABLE_STUDENTS_PARTS);
        db.execSQL(CREATE_TABLE_ADMINS);
        ////////////////////////////////////////////////////
        db.execSQL(CREATE_TABLE_ACTORS);
        db.execSQL(CREATE_TABLE_DIRECTORS);
        db.execSQL(CREATE_TABLE_MOVIES);
        db.execSQL(CREATE_TABLE_MOVIE_ACTORS);
    }

    //Delete all tables
    public void dropTables(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIRECTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE_ACTORS);
    }
    /*
     * Creating an admin
     */
    public long createAdmin(Admin admin) {

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, admin.getUserName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_PASSWORD, admin.getPassword());

        // insert row
        long admin_id = db.insert(TABLE_ADMINS, null, values);

        //now we know id obtained after writing actor to a database, update existing actor
        admin.setId(admin_id);

        return admin_id;
    }
    public boolean findAdmin(String un, String pass) {
        boolean admin_found = false;

        ArrayList<Admin> admins = new ArrayList<Admin>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADMINS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        String inputUserName;
        String inputPassword;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Admin a = new Admin();
                inputUserName = (c.getString(c.getColumnIndex(KEY_USERNAME)));
                inputPassword = (c.getString(c.getColumnIndex(KEY_PASSWORD)));
                if(inputUserName.equals(un)  && inputPassword.equals(pass)){
                    admin_found = true;
                    break;
                }
            } while (c.moveToNext());
        }
        return admin_found;
    }
    /*
     * getting all Subjects
     * SELECT * FROM subjects;
     * */
    public ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> admins = new ArrayList<Admin>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADMINS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Admin a = new Admin();
                a.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                a.setUserName((c.getString(c.getColumnIndex(KEY_USERNAME))));
                a.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                // adding to todo list
                admins.add(a);
            } while (c.moveToNext());
        }
        return admins;
    }
    public boolean findStudent(String un, String pass) {
        boolean student_found = false;

        ArrayList<Student> students = new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        String inputUserName;
        String inputPassword;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                inputUserName = (c.getString(c.getColumnIndex(KEY_USERNAME)));
                Log.i("MyTag", "username ="+ inputUserName);
                inputPassword = (c.getString(c.getColumnIndex(KEY_PASSWORD)));
                Log.i("MyTag", "password ="+ inputPassword);
                if(inputUserName.equals(un)  && inputPassword.equals(pass)){
                    student_found = true;
                    break;
                }
            } while (c.moveToNext());
        }
        return student_found;
    }
    public int getStudentIDWithUserName(String un) {
        int tempID = 500;
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        String inputUserName;
        String inputPassword;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                inputUserName = (c.getString(c.getColumnIndex(KEY_USERNAME)));
                inputPassword = (c.getString(c.getColumnIndex(KEY_PASSWORD)));
                if(inputUserName.equals(un)){
                    tempID = c.getInt(c.getColumnIndex(KEY_ID));
                    break;
                }
            } while (c.moveToNext());
        }
        return tempID;
    }
    /*
     * Creating a student
     */
    public long createStudent(Student student) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_INDEX, student.getIndex());
        values.put(KEY_JMBG, student.getJmbg());
        values.put(KEY_USERNAME, student.getUserName());
        values.put(KEY_PASSWORD, student.getPassword());
        // insert row
        long student_id = db.insert(TABLE_STUDENTS, null, values);

        //now we know id obtained after writing actor to a database, update existing actor
        student.setId(student_id);

        return student_id;
    }

    public long createSubject(Subject subject) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, subject.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_YEAR, subject.getYear());
        // insert row
        long subject_id = db.insert(TABLE_SUBJECTS, null, values);

        //now we know id obtained after writing actor to a database, update existing actor
        subject.setId(subject_id);

        return subject_id;
    }

    public long createPart(SubjectPart part) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, part.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_MIN_POINTS, part.getMinPoints());
        values.put(KEY_MAX_POINTS, part.getMaxPoints());
        // insert row
        long subject_id = db.insert(TABLE_PARTS, null, values);

        //now we know id obtained after writing actor to a database, update existing actor
        part.setId(subject_id);

        return subject_id;
    }

    public void addStudentsInSubject(Subject subject) {
        //read all actors for a given movie and for each actor insert a row in a table movie_actors
        for (Student student : subject.getStudents()){
            ContentValues values = new ContentValues();
            values.put(KEY_STUDENT_ID, student.getId());
            values.put(KEY_SUBJECT_ID, subject.getId());
            // insert row
            db.insert(TABLE_STUDENTS_SUBJECTS, null, values);
        }
    }

    public Student getStudent(long student_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE "
                + KEY_ID + " = " + student_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        //create student based on data read from a database
        Student st = new Student();
        st.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        st.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        st.setLastName((c.getString(c.getColumnIndex(KEY_LAST_NAME))));
        st.setIndex(c.getString(c.getColumnIndex(KEY_INDEX)));
        st.setJmbg(c.getString(c.getColumnIndex(KEY_JMBG)));
        st.setUserName(c.getString(c.getColumnIndex(KEY_USERNAME)));
        st.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        return st;
    }
    /*
     * Updating an Actor using data in an object actor
     */
    public int updateStudent(Student student) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_INDEX, student.getIndex());
        values.put(KEY_JMBG, student.getJmbg());
        values.put(KEY_USERNAME, student.getUserName());
        values.put(KEY_PASSWORD, student.getPassword());
        // updating row
        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }

    /*
     * Deleting an Student using actor id
     */
    public void deleteStudent(long student_id) {

        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(student_id) });
    }

    public Subject getSubject(long subject_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS + " WHERE "
                + KEY_ID + " = " + subject_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Subject subj = new Subject();
        subj.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        subj.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        subj.setYear(c.getString(c.getColumnIndex(KEY_YEAR)));

        return subj;
    }

    /*
     * getting all Students
     * SELECT * FROM students;
     * */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Student st = new Student();
                st.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                st.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                st.setLastName((c.getString(c.getColumnIndex(KEY_LAST_NAME))));
                st.setIndex(c.getString(c.getColumnIndex(KEY_INDEX)));
                st.setJmbg(c.getString(c.getColumnIndex(KEY_JMBG)));
                st.setUserName(c.getString(c.getColumnIndex(KEY_USERNAME)));
                st.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                // adding to todo list
                students.add(st);
            } while (c.moveToNext());
        }

        return students;
    }
    /*
     * getting all Subjects
     * SELECT * FROM subjects;
     * */
    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Subject subj = new Subject();
                subj.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                subj.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                subj.setYear(c.getString(c.getColumnIndex(KEY_YEAR)));
                // adding to todo list
                subjects.add(subj);
            } while (c.moveToNext());
        }

        return subjects;
    }

    /*
     * getting all students attending a subject
     * SELECT a.id, m.name FROM actors a, movies m, movie_actors ma WHERE m.name LIKE ‘Pulp%’ AND m.id = ma.movie_id AND a.id = ma.actor_id;
     * SELECT s.id, c.name FROM studen s, subjec c, stuce_subjec sc WHERE c.name LIKE ‘Razv%’ AND c.id = sc.subje_id AND s.id = sc.student_id
     * */
    public List<Student> getAllStudentsInSubject(String subjectName) {
        List<Student> students = new ArrayList<Student>();

        String selectQuery = "SELECT  st." + KEY_ID + ", sb." + KEY_NAME + " FROM " + TABLE_STUDENTS + " st, "
                + TABLE_SUBJECTS + " sb, " + TABLE_STUDENTS_SUBJECTS + " sc " +
                "WHERE UPPER(sb." + KEY_NAME + ") " +
                "LIKE '" + subjectName.toUpperCase() + "%'" +
                "AND sb." + KEY_ID + " = " + "sc." + KEY_SUBJECT_ID + " " +
                "AND st." + KEY_ID + " = " + "sc." + KEY_STUDENT_ID;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                long student_id = c.getInt(c.getColumnIndex(KEY_ID));
                Student s = getStudent(student_id);
                students.add(s);
            } while (c.moveToNext());
        }

        return students;
    }

    //SELECT s.id, c.name FROM studen s, subjec c, stuce_subjec sc WHERE c.name LIKE ‘Razv%’ AND c.id = sc.subje_id AND s.id = sc.student_id

    public ArrayList<Subject> getAllSubjectsOfStudent(String studentName) {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        String selectQuery = "SELECT  sb." + KEY_ID + ", st." + KEY_NAME + " FROM " + TABLE_SUBJECTS + " sb, "
                + TABLE_STUDENTS + " st, " + TABLE_STUDENTS_SUBJECTS + " sc " +
                "WHERE UPPER(st." + KEY_NAME + ") " +
                "LIKE '" + studentName.toUpperCase() + "%'" +
                "AND sb." + KEY_ID + " = " + "sc." + KEY_SUBJECT_ID + " " +
                "AND st." + KEY_ID + " = " + "sc." + KEY_STUDENT_ID;


        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                long subject_id = c.getInt(c.getColumnIndex(KEY_ID));
                Subject s = getSubject(subject_id);
                subjects.add(s);
            } while (c.moveToNext());
        }

        return subjects;
    }
    /**********************************************************************************************/
    /*
     * Creating an actor
     */
    public long createActor(Actor actor) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, actor.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_BIRTHDATE, actor.getBirthDate());

        // insert row
        long actor_id = db.insert(TABLE_ACTORS, null, values);

        //now we know id obtained after writing actor to a database, update existing actor
        actor.setId(actor_id);

        return actor_id;
    }

    /*
     * Creating a director
     */
    public long createDirector(Director director) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, director.getName());
        values.put(KEY_BIRTHDATE, director.getBirthDate());

        // insert row
        long director_id = db.insert(TABLE_DIRECTORS, null, values);
        director.setId(director_id);

        return director_id;
    }


    /*
     * Creating a movie
     */
    public long createMovie(Movie movie) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, movie.getName());
        values.put(KEY_DIRECTOR_ID, movie.getDirector().getId());
        values.put(KEY_RELEASEDATE, movie.getReleaseDate());

        // insert row
        long movie_id = db.insert(TABLE_MOVIES, null, values);

        movie.setId(movie_id);

        return movie_id;
    }

    /*
     * Creating a movie
     */
    public void addActorsInMovie(Movie movie) {
        //read all actors for a given movie and for each actor insert a row in a table movie_actors
        for (Actor actor : movie.getActors()){
            ContentValues values = new ContentValues();
            values.put(KEY_MOVIE_ID, movie.getId());
            values.put(KEY_ACTOR_ID, actor.getId());
            // insert row
            db.insert(TABLE_MOVIE_ACTORS, null, values);
        }
    }


    /*
     * get single actor like
     * SELECT * FROM actors WHERE id = 1;
     */
    public Actor getActor(long actor_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_ACTORS + " WHERE "
                + KEY_ID + " = " + actor_id;
        //Alternative to use selectionArgs in rawQuery
        //String selectQuery = "SELECT  * FROM " + TABLE_ACTORS + " WHERE "
        //        + KEY_ID + " = ?";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        //If using selectionArgs, the number of passed strings must match the number of ? characters
        //within selectQuery string
        //Cursor c = db.rawQuery(selectQuery, new String[]{Long.toString(actor_id)});

        if (c != null)
            c.moveToFirst();

        //create actor based on data read from a database
        Actor ac = new Actor();
        ac.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        ac.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        ac.setBirthDate(c.getString(c.getColumnIndex(KEY_BIRTHDATE)));

        return ac;
    }


    /*
     * Updating an Actor using data in an object actor
     */
    public int updateActor(Actor actor) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, actor.getName());
        values.put(KEY_BIRTHDATE, actor.getBirthDate());

        // updating row
        return db.update(TABLE_ACTORS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(actor.getId()) });
    }

    /*
     * Deleting an Actor using actor id
     */
    public void deleteActor(long actor_id) {

        db.delete(TABLE_ACTORS, KEY_ID + " = ?",
                new String[] { String.valueOf(actor_id) });
    }


    /*
     * get single director like
     * SELECT * FROM directors WHERE id = 1;
     */
    public Director getDirector(long director_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_DIRECTORS + " WHERE "
                + KEY_ID + " = " + director_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Director d = new Director();
        d.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        d.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        d.setBirthDate(c.getString(c.getColumnIndex(KEY_BIRTHDATE)));

        return d;
    }

    /*
     * get single movie like
     * SELECT * FROM movies WHERE id = 1;
     */

    public Movie getMovie(long movie_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES + " WHERE "
                + KEY_ID + " = " + movie_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Movie m = new Movie();
        m.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        m.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        //obtain director based on director id read from table movies
        Director d = getDirector(c.getInt(c.getColumnIndex(KEY_DIRECTOR_ID)));
        m.setDirector(d);
        m.setReleaseDate(c.getString(c.getColumnIndex(KEY_RELEASEDATE)));

        return m;
    }


    /*
     * getting all actors
     * SELECT * FROM actors;
     * */
    public ArrayList<Actor> getAllActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACTORS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Actor a = new Actor();
                a.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                a.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                a.setBirthDate(c.getString(c.getColumnIndex(KEY_BIRTHDATE)));

                // adding to todo list
                actors.add(a);
            } while (c.moveToNext());
        }

        return actors;
    }


    /*
     * getting all directors;
     * SELECT * FROM directors;
     * */
    public ArrayList<Director> getAllDirectors() {
        ArrayList<Director> directors = new ArrayList<Director>();
        String selectQuery = "SELECT  * FROM " + TABLE_DIRECTORS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Director d = new Director();
                d.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                d.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                d.setBirthDate(c.getString(c.getColumnIndex(KEY_BIRTHDATE)));
                directors.add(d);
            } while (c.moveToNext());
        }

        return directors;
    }

    /*
     * getting all actors acting in a movie
     * SELECT a.id, m.name FROM actors a, movies m, movie_actors ma WHERE m.name LIKE ‘Pulp%’ AND
     * m.id = ma.movie_id AND a.id = ma.actor_id;
     * */
    public List<Actor> getAllActorsInMovie(String movieName) {
        List<Actor> actors = new ArrayList<Actor>();

        String selectQuery = "SELECT  a." + KEY_ID + ", m." + KEY_NAME + " FROM " + TABLE_ACTORS + " a, "
                + TABLE_MOVIES + " m, " + TABLE_MOVIE_ACTORS + " ma WHERE UPPER(m."
                + KEY_NAME + ") LIKE '" + movieName.toUpperCase() + "%'" + " AND m." + KEY_ID
                + " = " + "ma." + KEY_MOVIE_ID + " AND a." + KEY_ID
                + " = " + "ma." + KEY_ACTOR_ID;


        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                long actor_id = c.getInt(c.getColumnIndex(KEY_ID));
                Actor a = getActor(actor_id);
                actors.add(a);
            } while (c.moveToNext());
        }

        return actors;
    }


    /*
     * getting all movies
     * SELECT * FROM movies;
     * */
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Movie m = new Movie();
                m.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                m.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                m.setReleaseDate((c.getString(c.getColumnIndex(KEY_RELEASEDATE))));
                Director d = getDirector((c.getInt(c.getColumnIndex(KEY_DIRECTOR_ID))));
                m.setDirector(d);

                movies.add(m);
            } while (c.moveToNext());
        }

        return movies;
    }

    // closing database
    public void closeDB() {
        if (db != null && db.isOpen())
            db.close();
    }

}