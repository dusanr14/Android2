package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

public class Student {
    long id;
    String name;
    String lastName;
    String stud_index;
    String jmbg;
    String userName;
    String password;

    public Student() {
    }

    public Student(String name, String lastName, String stud_index, String jmbg, String userName, String password) {
        this.name = name;
        this.lastName = lastName;
        this.stud_index = stud_index;
        this.jmbg = jmbg;
        this.userName = userName;
        this.password = password;
    }

    public Student(long id, String name, String lastName, String stud_index, String jmbg, String userName, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.stud_index = stud_index;
        this.jmbg = jmbg;
        this.userName = userName;
        this.password = password;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndex() {
        return stud_index;
    }

    public void setIndex(String stud_index) {
        this.stud_index = stud_index;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
