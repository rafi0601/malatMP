package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Plane extends Geometry implements FlatGeometry{
    protected Point3D P;
    protected Vector normal;

    /********** Constructors ***********/

    public Plane(Point3D p, Vector normal) {
        P = p;
        this.normal = normal;
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3) {

        Vector v1 = new Vector(p1, p2);
        Vector v2 = new Vector(p1, p3);
        Vector n = new Vector(v1.crossProduct(v2));
        n.normalize();
        n.multiply(-1);

        P = new Point3D(p1);
        normal = new Vector(n);
    }

    public Plane(Color color, Point3D p, Vector normal) {
        super(color);
        P = p;
        this.normal = normal;
    }

    public Plane(Color color,Point3D p1,Point3D p2,Point3D p3) {
        super(color);
        Vector v1 = new Vector(p1, p2);
        Vector v2 = new Vector(p1, p3);
        Vector n = new Vector(v1.crossProduct(v2));
        n.normalize();
        n.multiply(-1);

        P = new Point3D(p1);
        normal = new Vector(n);
    }

    /************** Getters/Setters *******/

    public Point3D getP() {
        return P;
    }

    public Vector getNormal() {
        return normal;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Plane{" +
                "P=" + P +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p1) {
        return new Vector(getNormal());
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections=new ArrayList<Point3D>();
        Point3D P0=ray.getPoo();
        Point3D Q0=this.getP();
        Vector normal=this.getNormal(null);
        Vector v=ray.getDirection();
        if(normal.dotProduct(v)==0)
        {
            return EMPTY_LIST;
        }
        Vector u=new Vector(P0,Q0);
        double t=(normal.dotProduct(u))/normal.dotProduct(v);
        if(t>0)
            intersections.add(ray.getPoo().add(v.multiply(t)));
        if(t==0)
            intersections.add(ray.getPoo());
        return intersections;
    }
}
