package uns.ftn.deet.kel.moviesdatabase.sqlite.model;

public class SubjectPart {
    long id;
    String name;
    int minPoints;
    int maxPoints;

    public SubjectPart(String name, int minPoints, int maxPoints) {
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }
    public SubjectPart(long id, String name, int minPoints, int maxPoints) {
        this.id = id;
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }
}
