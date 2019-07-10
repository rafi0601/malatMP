package primitives;

import java.util.Objects;

public class Point2D {
    protected Coordinate x;
    protected Coordinate y;

    /********** Constructors ***********/

    public Point2D(Coordinate x1, Coordinate y1) {
        this.x = new Coordinate(x1);
        this.y = new Coordinate(y1);

    }

    public Point2D() {
        this.x= Coordinate.ZERO;
        this.y= Coordinate.ZERO;
    }

    public Point2D(Point2D other) {
        this.x=new Coordinate(other.x);
        this.y=new Coordinate(other.y);
    }

    /************** Getters/Setters *******/

    public Coordinate getx() {
        return x;
    }

    public void setx(Coordinate x) {
        this.x = x;
    }

    public Coordinate gety() {
        return y;
    }

    public void sety(Coordinate y) {
        this.y = y;
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null) return false;
        if (!(o instanceof Point2D)) return false;
        Point2D point2D = (Point2D) o;
        return x.equals(point2D.x) && y.equals(point2D.y);
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getx(), gety());
    }

}

