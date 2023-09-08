package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

import java.util.ArrayList;

public class Subject {
    long id;
    String name;
    String year;

    ArrayList<Student> students;
    ArrayList<SubjectPart> parts;
    public Subject(){this.students = new ArrayList<>(); this.parts = new ArrayList<>();}

    public Subject(String name, String year) {
        this.name = name;
        this.year = year;
        this.students = new ArrayList<>();
        this.parts = new ArrayList<>();
    }
    public Subject(long id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.students = new ArrayList<>();
        this.parts = new ArrayList<>();
    }

    public Subject(String name, String year, ArrayList<Student> students, ArrayList<SubjectPart> parts) {
        this.name = name;
        this.year = year;
        this.students = students;
        this.parts = parts;
    }
    public Subject(long id, String name, String year, ArrayList<Student> students, ArrayList<SubjectPart> parts) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.students = students;
        this.parts = parts;
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



    public ArrayList<SubjectPart> getParts() {
        return parts;
    }

    public void setParts(ArrayList<SubjectPart> parts) {
        this.parts = parts;
    }
    public void addParts(SubjectPart part) {this.parts.add(part); }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void addStudent(Student stud) {this.students.add(stud); }

}
