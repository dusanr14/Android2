package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

import java.util.ArrayList;

public class Subject {
    long id;
    String name;
    String year;

    ArrayList<Student> students;
    public Subject(){
    }


    public Subject(String name, String year, ArrayList<Student> students) {
        this.name = name;
        this.year = year;
        this.students = students;
    }
    public Subject(long id, String name, String year, ArrayList<Student> students) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {this.students.add(student); }
}
