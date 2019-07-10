package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

public class Sphere extends RadialGeometry {
    private Point3D center;

    /********** Constructors ***********/

    public Sphere(double _radius, Point3D center) {
        super(_radius);
        this.center = center;
    }

    public Sphere(Color color, double _radius, Point3D center) {
        super(color,_radius);
        this.center = center;
    }

    /************** Getters/Setters *******/

    public Point3D getCenter() {
        return center;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +super.toString()+
                '}';
    }

    @Override
    public Vector getNormal(Point3D p1)  {
        try {
            Vector n = new Vector(center, p1);
            n.normalize();
            return n;
        }
        catch (Exception ex)
        {
            return Vector.ZERO;
        }
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector u;
        try {
            u=new Vector(ray.getPoo(),this.getCenter());
        }
        catch (Exception ex)
        {
            u=Vector.ZERO;
        }
        double tm=u.dotProduct(ray.getDirection());
        double d=Math.sqrt(u.length()*u.length()-tm*tm);
        if(d>this.get_radius())
            return EMPTY_LIST;
        double th=Math.sqrt(this.get_radius()*this.get_radius()-d*d);
        double t1=tm+th;
        double t2=tm-th;
        List<Point3D> intersections=new ArrayList<Point3D>();
        Vector v=new Vector(ray.getDirection());
        if(t1>0)
            intersections.add(ray.getPoo().add(v.multiply(t1)));
        if(t2>0)
            intersections.add(ray.getPoo().add(new Vector(ray.getDirection()).multiply(t2)));
        if(t1==0||t2==0)
            intersections.add(ray.getPoo());
        return intersections;
    }
}
