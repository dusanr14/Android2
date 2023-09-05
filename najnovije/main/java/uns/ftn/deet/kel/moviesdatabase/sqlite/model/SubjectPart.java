package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

public class SubjectPart {
    long id;
    String name;
    int minPoints;
    int maxPoints;

    Subject subject;
    public SubjectPart(){
    }
    public SubjectPart(String name, int minPoints, int maxPoints,Subject subject) {
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
        this.subject = subject;
    }
    public SubjectPart(long id, String name, int minPoints, int maxPoints,Subject subject) {
        this.id = id;
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
        this.subject = subject;
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

    public int getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
