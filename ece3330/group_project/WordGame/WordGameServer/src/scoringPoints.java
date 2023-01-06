// scoringPoints.java

/**
 * This class keeps track of the type of score and the points associated for it.
 * */
public class scoringPoints {
    private final String nameOfMethod;
    private final int point;

    public scoringPoints(String nameOfMethod, int point) {
        this.nameOfMethod = nameOfMethod;
        this.point = point;
    }

    public String getNameOfMethod(){ return nameOfMethod; }
    public int getPoint(){ return point; }

}
