package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Cylinder extends Tube {
    protected double length;

    /********** Constructors ***********/

    public Cylinder(double _radius, Ray ray, double length) {
        super(_radius, ray);
        this.length = length;
    }

    public Cylinder(Color color, double _radius, Ray ray, double length) {
        super(color,_radius, ray);
        this.length = length;
    }

    /************** Getters/Setters *******/

    public double getLength() {
        return length;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Cylinder{" +
                "length=" + length +
                ", " + super.toString() +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p1) {
        Vector v=new Vector(this.getRay().getDirection());
        v.normalize();
        Vector u=new Vector(this.getRay().getPoo(),p1);
        if(u.dotProduct(v)==0)
            return v.multiply(-1);
        Vector projection=u.projection(v);//the projection of u on v
        if(projection.length()==this.getLength()) {
            return v;
        }
        return super.getNormal(p1);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = super.findIntersections(ray);
        Vector v0 = new Vector(this.getRay().getDirection());
        v0.normalize();
        Point3D p1 = new Point3D(this.getRay().getPoo());
        Point3D p2 = new Point3D(new Point3D(p1).add(new Vector(v0).multiply(this.getLength())));//p2=p1+v0*length
        if (!intersections.isEmpty()) {
            for (int i = 0; i < intersections.size(); i++) {
                Point3D qi = intersections.get(i);//qi=p+v*ti
                Vector qiSUBp1 = qi.subtract(p1);
                Vector qiSUBp2 = qi.subtract(p2);
                double test1 = v0.dotProduct(qiSUBp1) / (qiSUBp1.length() * qiSUBp1.length());
                double test2 = v0.dotProduct(qiSUBp2) / (qiSUBp2.length() * qiSUBp2.length());
                Vector qiSUBp = new Vector(qi.subtract(ray.getPoo()));
                double ti = qiSUBp.gethead().getz().get() / ray.getDirection().gethead().getz().get();
                if (!(ti > 0 && test1 > 0 && test2 < 0)) {
                    intersections.remove(i);
                }
            }
        }
        Plane upper = new Plane(p2, new Vector(v0));
        Plane down = new Plane(p1, new Vector(v0));
        //the caps intersections

        List<Point3D> intersectionsPlaneUpper = upper.findIntersections(ray);
        List<Point3D> intersectionsPlaneDown = down.findIntersections(ray);

        if (!intersectionsPlaneUpper.isEmpty()) {
            Point3D q4 = intersectionsPlaneUpper.get(0);//q4=p+v*t4
            Vector q4SUBp2 = q4.subtract(p2);
            double test = q4SUBp2.dotProduct(q4SUBp2);
            Vector q4SUBp = new Vector(q4.subtract(ray.getPoo()));
            double t4 = q4SUBp.gethead().getz().get() / ray.getDirection().gethead().getz().get();
            if (!(t4 > 0 && test < get_radius()*get_radius()) ){
                intersectionsPlaneUpper.remove(0);
            }
        }
        if (!intersectionsPlaneDown.isEmpty()) {
            Point3D q3 = intersectionsPlaneDown.get(0);//q3=p+v*t3
            Vector q3SUBp1 = q3.subtract(p1);
            double test = q3SUBp1.dotProduct(q3SUBp1);
            Vector q3SUBp = new Vector(q3.subtract(ray.getPoo()));
            double t3 = q3SUBp.gethead().getz().get() / ray.getDirection().gethead().getz().get();
            if (!(t3 > 0 && test < get_radius()*get_radius()) ){
                intersectionsPlaneDown.remove(0);
            }

        }
        intersections.addAll(intersectionsPlaneUpper);
        intersections.addAll(intersectionsPlaneDown);
        return intersections;
    }
}
