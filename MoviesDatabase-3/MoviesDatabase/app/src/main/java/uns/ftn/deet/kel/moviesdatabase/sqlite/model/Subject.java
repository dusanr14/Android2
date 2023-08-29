package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

public class Subject {
    long id;
    String name;
    String year;

    public Subject(){
    }
    public Subject(String name, String year) {
        this.name = name;
        this.year = year;
    }
    public Subject(long id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
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
}
