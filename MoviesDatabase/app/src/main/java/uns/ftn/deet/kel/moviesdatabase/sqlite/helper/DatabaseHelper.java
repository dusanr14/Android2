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
            KEY_SUBJECT_ID  + " INTEGER," +
            KEY_OBTAINED_POINTS  + " INTEGER" +")";
    private static final String CREATE_TABLE_STUDENTS_PARTS= "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS_PARTS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_STUDENT_ID  + " INTEGER," +
            KEY_PART_ID  + " INTEGER," +
            KEY_OBTAINED_POINTS  + " INTEGER" +")";
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    }

    //Delete all tables
    public void dropTables(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS_PARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
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

        //now we know id obtained after writing admin to a database, update existing admin
        admin.setId(admin_id);

        return admin_id;
    }
    /*
     * Find admin using username and password
     */
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
    /*
     * getting Students with given username and password
     * SELECT * FROM subjects;
     * */
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

        //now we know id obtained after writing student to a database, update existing student
        student.setId(student_id);

        return student_id;
    }

    public long createSubject(Subject subject) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, subject.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_YEAR, subject.getYear());
        // insert row
        long subject_id = db.insert(TABLE_SUBJECTS, null, values);

        //now we know id obtained after writing subject to a database, update existing subject
        subject.setId(subject_id);

        return subject_id;
    }
    /*
     * Find subject using name and year
     */
    public boolean findSubject(String name, String year) {
        boolean subject_found = false;

        ArrayList<Subject> subjects = new ArrayList<Subject>();
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        String inputName;
        String inputYear;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Admin a = new Admin();
                inputName = (c.getString(c.getColumnIndex(KEY_NAME)));
                inputYear = (c.getString(c.getColumnIndex(KEY_YEAR)));
                if(inputName.equals(name)  && inputYear.equals(year)){
                    subject_found = true;
                    break;
                }
            } while (c.moveToNext());
        }
        return subject_found;
    }

    public int getSubjectIDWithNameAndYear(String name, String year) {
        int tempID = 500;
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        String inputName;
        String inputYear;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                inputName = (c.getString(c.getColumnIndex(KEY_NAME)));
                inputYear = (c.getString(c.getColumnIndex(KEY_YEAR)));
                if(inputName.equals(name) && inputYear.equals(year)){
                    tempID = c.getInt(c.getColumnIndex(KEY_ID));
                    break;
                }
            } while (c.moveToNext());
        }
        return tempID;
    }
    public long createPart(SubjectPart part) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, part.getName());//kljuc mora da se slaze sa nazivom kolone
        values.put(KEY_MIN_POINTS, part.getMinPoints());
        values.put(KEY_MAX_POINTS, part.getMaxPoints());
        // insert row
        long subject_id = db.insert(TABLE_PARTS, null, values);

        //now we know id obtained after writing part to a database, update existing part
        part.setId(subject_id);

        return subject_id;
    }

    public void addStudentsInSubject(Subject subject) {
        for (Student student : subject.getStudents()){
            ContentValues values = new ContentValues();
            values.put(KEY_STUDENT_ID, student.getId());
            values.put(KEY_SUBJECT_ID, subject.getId());
            values.put(KEY_OBTAINED_POINTS, 0);
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
     * Updating an Student using data in an object student
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
     * Updating an Points using data
     */
    public int updateObtainedPoints(int studID, int subjID, int obtPoints) {
        ContentValues values = new ContentValues();
        int obtainedPoints = 0;
        if(obtPoints > 100){
            obtainedPoints = 100;
        } else if (obtPoints < 0) {
            obtainedPoints = 0;
        } else {
            obtainedPoints = obtPoints;
        }
        values.put(KEY_OBTAINED_POINTS, obtainedPoints);

        // Properly format the WHERE clause with spaces
        String whereClause = KEY_STUDENT_ID + " = ? AND " + KEY_SUBJECT_ID + " = ?";

        // Provide the values for the placeholders in the WHERE clause
        String[] whereArgs = { String.valueOf(studID), String.valueOf(subjID) };

        // Update the row
        return db.update(TABLE_STUDENTS_SUBJECTS, values, whereClause, whereArgs);
    }

    /*
     * Deleting an Student using student id
     */
    public void deleteStudent(long student_id) {

        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(student_id) });
        db.delete(TABLE_STUDENTS_SUBJECTS, KEY_STUDENT_ID + " = ?",
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
     * SELECT s.id, c.name FROM studen s, subjec c, stuce_subjec sc WHERE c.name LIKE ‘Razv%’ AND c.id = sc.subje_id AND s.id = sc.student_id
     * */
    public List<Student> getAllStudentsInSubject(int subjectId) {
        List<Student> students = new ArrayList<Student>();

        String selectQuery = "SELECT  st." + KEY_ID + ", sb." + KEY_NAME + " FROM " + TABLE_STUDENTS + " st, "
                + TABLE_SUBJECTS + " sb, " + TABLE_STUDENTS_SUBJECTS + " sc " +
                "WHERE sb." + KEY_ID +
                " = " + subjectId +
                " AND sb." + KEY_ID + " = " + "sc." + KEY_SUBJECT_ID + " " +
                " AND st." + KEY_ID + " = " + "sc." + KEY_STUDENT_ID;

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

    public ArrayList<Subject> getAllSubjectsOfStudent(String studentUserName) {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        String selectQuery = "SELECT  sb." + KEY_ID + ", st." + KEY_NAME + " FROM " + TABLE_SUBJECTS + " sb, "
                + TABLE_STUDENTS + " st, " + TABLE_STUDENTS_SUBJECTS + " sc " +
                "WHERE UPPER(st." + KEY_USERNAME + ") " +
                "LIKE '" + studentUserName.toUpperCase() + "%'" +
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

    //SELECT s.id, c.name FROM studen s, subjec c, stuce_subjec sc WHERE c.name LIKE ‘Razv%’ AND c.id = sc.subje_id AND s.id = sc.student_id

    public int getObtainedPoints(int studID, int subjID) {
        int obtainedPoints = 5000000;

        //String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS_SUBJECTS;
        String selectQuery = "SELECT  sc." + KEY_OBTAINED_POINTS + " FROM " + TABLE_STUDENTS_SUBJECTS + " sc " +
                "WHERE sc." + KEY_STUDENT_ID + " = " + studID + " AND sc." + KEY_SUBJECT_ID + " = " + subjID;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                obtainedPoints = c.getInt(c.getColumnIndex(KEY_OBTAINED_POINTS));
            } while (c.moveToNext());
            return obtainedPoints;
        }

        return obtainedPoints;
    }

    // closing database
    public void closeDB() {
        if (db != null && db.isOpen())
            db.close();
    }

}