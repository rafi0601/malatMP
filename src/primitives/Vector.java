package primitives;

import java.util.Objects;

public class Vector {
    private Point3D head;
    public final static Vector ZERO = new Vector();

    /********** Constructors ***********/

    public Vector(Point3D head) {
        this.head = head;
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public Vector() {
        this.head=new Point3D();
    }

    public Vector(Point3D p1, Point3D p2) { //Vector from p1 to p2
        this.head = p2.subtract(p1).head;
    }

    public Vector(Vector other) {
        this.head=new Point3D(other.head);
    }

    /************** Getters/Setters *******/

    public Point3D gethead() {
        return head;
    }

    public void sethead(Point3D head) {
        this.head = head;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + gethead() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector Vector = (Vector) o;
        return Objects.equals(gethead(), Vector.gethead());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gethead());
    }

    /************** Operations ***************/

    public Vector projection(Vector other){
        Vector u=new Vector(this);
        Vector v=new Vector(other);

        v.normalize();

        return v.multiply(u.dotProduct(v));
    }//projection this on other

    public Vector add(Vector other) {
        this.head= new Vector(head.add(other)).head;
        return this;
    }//change the original

    public Vector subtract(Vector other){
        Vector minusVector;
        try {
            minusVector = new Point3D().subtract(other.head);
        }
        catch (Exception ex) {
            throw ex;
        }
        return this.add(minusVector);
    }//change the original

    public Vector multiply(double scale){
        Vector v = new Vector(new Point3D(this.head.getx().scale(scale),this.head.gety().scale(scale),this.head.getz().scale(scale)));
        this.sethead(v.head);
        return this;
    }//change the original

    public double dotProduct(Vector other) {
        return Util.uadd(Util.uadd(this.head.getx().multiply(other.head.getx()).get(), this.head.gety().multiply(other.head.gety()).get()), this.head.getz().multiply(other.head.getz()).get());
    }

    public Vector crossProduct(Vector other) {
        double x1 = this.gethead().getx().get();
        double y1 = this.gethead().gety().get();
        double z1 = this.gethead().getz().get();

        double x2 = other.gethead().getx().get();
        double y2 = other.gethead().gety().get();
        double z2 = other.gethead().getz().get();

        Coordinate x3=new Coordinate(Util.usubtract(Util.uscale(y1, z2), Util.uscale(z1, y2)));
        Coordinate y3=new Coordinate(Util.usubtract(Util.uscale(z1, x2), Util.uscale(x1, z2)));
        Coordinate z3=new Coordinate(Util.usubtract(Util.uscale(x1, y2), Util.uscale(y1, x2)));

        Vector result=new Vector(new Point3D(x3,y3,z3));
        if(result.equals(ZERO))
            throw new IllegalArgumentException("zero vector!!!");
        return result;
    }

    public double length() {
        return this.head.distance(Point3D.ZERO);
    }

    public Vector normalize() {
        double length=this.length();
        if ( length==0)
            return ZERO;
        Point3D newHead=new Point3D(new Coordinate(this.head.getx().get()/this.length()),new Coordinate(this.head.gety().get()/this.length()),new Coordinate(this.head.getz().get()/this.length()));
        this.sethead(newHead);
        if(this.length()==0)
            throw new IllegalArgumentException("zero vector!!!");
        return this;
    }//change the original
}
