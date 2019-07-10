package geometries;

import primitives.Point3D;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Plane{
    protected Point3D p1;
    protected Point3D p2;
    protected Point3D p3;

    /********** Constructors ***********/

    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1,p2,p3);
        this.p1=new Point3D(p1);
        this.p2=new Point3D(p2);
        this.p3=new Point3D(p3);
    }

    public Triangle(Color color,Point3D p1, Point3D p2, Point3D p3) {
        super(color,p1,p2,p3);
        this.p1=new Point3D(p1);
        this.p2=new Point3D(p2);
        this.p3=new Point3D(p3);
    }

    /************** Getters/Setters *******/

    public Point3D getP1() {
        return p1;
    }

    public Point3D getP2() {
        return p2;
    }

    public Point3D getP3() {
        return p3;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Triangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersectionPoints = new ArrayList<Point3D>();
        Point3D p0=ray.getPoo();
        Vector n =getNormal(null);
        Plane plane=new Plane(p1,p2,p3);
        List<Point3D> intersections=plane.findIntersections(ray);
        if (intersections.isEmpty()||intersections.size()==0)
            return EMPTY_LIST;
        Point3D intersectionPlane = intersections.get(0);
        Vector p_p0=new Vector(p0,intersectionPlane);
        Vector V1_1 = new Vector(p0, this.p1);
        Vector V2_1 = new Vector(p0, this.p2);
        Vector N1=V1_1.crossProduct(V2_1);
        N1.normalize();
        double S1 = p_p0.dotProduct(N1);

        Vector V1_2 = new Vector(p0, this.p2);
        Vector V2_2 = new Vector(p0, this.p3);
        Vector N2 = V1_2.crossProduct(V2_2);
        N2.normalize();
        double S2 = p_p0.dotProduct(N2);


        Vector V1_3 = new Vector(p0, this.p3);
        Vector V2_3 = new Vector(p0, this.p1);
        Vector N3 = V1_3.crossProduct(V2_3);
        N3.normalize();
        double S3 = p_p0.dotProduct(N3);

        if (((S1 > 0) && (S2 > 0) && (S3 > 0)) ||
                ((S1 < 0) && (S2 < 0) && (S3 < 0))) {
            intersectionPoints.add(intersectionPlane);
        }
        return intersectionPoints;
    }
}
