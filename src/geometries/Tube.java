package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Tube extends RadialGeometry {
    protected Ray ray;

    /********** Constructors ***********/

    public Tube(double _radius, Ray ray) {
        super(_radius);
        this.ray = ray;
    }

    public Tube(Color color, double _radius, Ray ray) {
        super(color,_radius);
        this.ray = ray;
    }

    /************** Getters/Setters *******/

    public Ray getRay() {
        return ray;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tube{" +
                "ray=" + ray +super.toString()+
                '}';
    }

    @Override
    public Vector getNormal( Point3D p1) {
        Vector temp=new Vector(getRay().getPoo(),p1);
        double powRadius=get_radius()*get_radius();
        if (temp.length()*temp.length()-powRadius<0)
            throw new ArithmeticException();
        double distance =Math.sqrt(temp.length()*temp.length()-powRadius);
        double cosAngle=(temp.dotProduct(getRay().getDirection()))/(temp.length()*getRay().getDirection().length());
        if(cosAngle>0)
        {
            Vector direction=new Vector(getRay().getDirection().normalize().multiply(distance));
            Point3D center=new Point3D(getRay().getPoo().add(direction));
            return new Vector(center,p1).normalize();
        }
        else
        {
            Vector direction=new Vector(getRay().getDirection().normalize().multiply(-1*distance));
            Point3D center=new Point3D(getRay().getPoo().add(direction));
            return new Vector(center,p1).normalize();
        }
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector v=new Vector(ray.getDirection());
        v.normalize();
        Vector v0=new Vector(this.getRay().getDirection());
        v0.normalize();
        double r=super.get_radius();
        Point3D p=new Point3D(ray.getPoo());
        Point3D p0=new Point3D(this.getRay().getPoo());
        Vector deltaP=new Vector(p0,p);

        Vector temp=v.subtract(v.projection(v0));
        Vector temp1=deltaP.subtract(deltaP.projection(v0));
        double A=temp.dotProduct(temp);
        double B=2*temp.dotProduct(temp1)/(temp1.length()*temp1.length());
        double C=temp1.dotProduct(temp1)-r*r;

        //solve At^2+Bt+C=0
        double delta=B*B-4*A*C;
        v=new Vector(ray.getDirection());
        v.normalize();
        if(delta<0)
        {
            return EMPTY_LIST;
        }
        else if(delta==0)
        {
            List<Point3D> intersections=new ArrayList<Point3D>();
            double t=-B/(2*A);
            intersections.add(p.add(v.multiply(t)));
            return intersections;
        }
        else
        {
            List<Point3D> intersections=new ArrayList<Point3D>();
            double t1=(-B+Math.sqrt(delta))/(2*A);
            double t2=(-B-Math.sqrt(delta))/(2*A);

            intersections.add(p.add(v.multiply(t1)));
            v.normalize();
            intersections.add(p.add(v.multiply(t2)));
            return intersections;
        }
    }
}
