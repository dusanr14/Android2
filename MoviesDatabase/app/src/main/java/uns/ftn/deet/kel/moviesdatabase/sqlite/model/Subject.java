package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

import java.util.ArrayList;

public class Subject {
    long id;
    String name;
    String year;

    ArrayList<Student> students;
    int obtainedPoints;
    public Subject(){this.students = new ArrayList<>();}

    public Subject(String name, String year) {
        this.name = name;
        this.year = year;
        this.obtainedPoints = 0;
        this.students = new ArrayList<>();
    }
    public Subject(long id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.obtainedPoints = 0;
        this.students = new ArrayList<>();
    }

    public Subject(String name, String year, ArrayList<Student> students, ArrayList<SubjectPart> parts) {
        this.name = name;
        this.year = year;
        this.obtainedPoints = 0;
        this.students = students;
    }
    public Subject(long id, String name, String year, ArrayList<Student> students, ArrayList<SubjectPart> parts) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.obtainedPoints = 0;
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getObtainedPoints() {
        return obtainedPoints;
    }

    public void setObtainedPoints(int obtainedPoints) {
        this.obtainedPoints = obtainedPoints;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void addStudent(Student stud) {this.students.add(stud); }

}
