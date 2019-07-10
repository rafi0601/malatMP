import geometries.*;
import primitives.*;

public class Main {
    public static void main(String[] args) {
        try {
            Coordinate c1 = new Coordinate(1);
            Coordinate c2 = new Coordinate(2);
            System.out.println("--------------------cordinates-----------------");
            System.out.println("c1:" + c1.toString());
            System.out.println("c1:" + c2.toString());
            System.out.println("c1*5:" + c1.scale(5));
            System.out.println("c1+c2:" + c1.add(c2));
            System.out.println("c1-c2:" + c1.subtract(c2));
            System.out.println("c1*c2:" + c1.multiply(c2));
            System.out.println("c1==c2?:" + c1.equals(c2));
            System.out.println("c1:" + c1.toString());
            System.out.println("c1:" + c2.toString());
            System.out.println("--------------------Point2D-----------------");
            Point2D p1 = new Point2D();
            Point2D p2 = new Point2D(c1, c2);
            System.out.println("p1:" + p1.toString());
            System.out.println("p2:" + p2.toString());
            p1.setx(new Coordinate(5.5));
            System.out.println("p1 after change in the x:" + p1.toString());
            p1.sety(new Coordinate(4.5));
            System.out.println("p1 after change in the y:" + p1.toString());
            System.out.println("--------------------Point3D-----------------");
            Point3D p3 = new Point3D();
            Point3D p4 = new Point3D(c1, c2, new Coordinate(3.3));
            System.out.println("p3:" + p3.toString());
            System.out.println("p4:" + p4.toString());
            System.out.println("the distance between p3 and p4:" + p3.distance(p4));
            System.out.println("the distance between p3 and p4 by pow:" + p3.distancePow(p4));
            System.out.println("p3 +Vector(p4):" + p3.add(new Vector(p4)));
            System.out.println("p3-p4:" + p3.subtract(p4));
            System.out.println("p3==p4?:" + p3.equals(p4));
            System.out.println("--------------------Vector-----------------");
            Vector v1 = new Vector();
            Vector v2 = new Vector(p4);
            System.out.println("v1:" + v1.toString());
            System.out.println("v2:" + v2.toString());
            System.out.println("the normal of v2:" + v2.normalize());
            System.out.println("the lenght of v2:" + v2.length());
            System.out.println("v2 +Vector(p4):" + v2.add(new Vector(p4)));
            System.out.println("v2*5:" + v2.multiply(5));
            System.out.println("v2 *Vector(p4):" + v2.dotProduct(new Vector(p4)));
            System.out.println("v2XVector(p4):" + v2.crossProduct(new Vector(p4)));
            System.out.println("v2:" + v2.toString());
            System.out.println("v1==v2?:" + v2.equals(v1));
            System.out.println("v2-Vector(p4):" + v2.subtract(new Vector(p4)));
            System.out.println("--------------------Ray-----------------");
            Ray r1 = new Ray();
            Ray r2 = new Ray(p4, v2);
            r1.setDirection(new Vector(p4));
            System.out.println("r1 after change in the direction:" + r1.toString());
            p3.setx(new Coordinate(5.5));
            r1.setPoo(p3);
            System.out.println("r1 after change in the point:" + r1.toString());
            System.out.println("--------------------cylinder-----------------");
            Cylinder cy1 = new Cylinder(6, r2, 45);
            System.out.println(cy1.toString());
            System.out.println("--------------------plane-----------------");
            Plane pl1 = new Plane(p4, v2);
            System.out.println(pl1.toString());
            System.out.println("--------------------Sphere-----------------");
            Sphere sp1 = new Sphere(6.7, p3);
            System.out.println(sp1.toString());
            System.out.println("--------------------Triangle-----------------");
            Point3D p5 = new Point3D(new Coordinate(5.8), new Coordinate(8.8), new Coordinate(5.6));
            Triangle tr1 = new Triangle(p3, p4, p5);
            System.out.println(tr1.toString());
            System.out.println("--------------------Tube-----------------");
            Tube tu1 = new Tube(4.7, r1);
            System.out.println(tu1.toString());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Point3D p3 = new Point3D();
        Point3D p4 = new Point3D(1,2,3);
        Point3D p5 = new Point3D(new Coordinate(5.8), new Coordinate(8.8), new Coordinate(5.6));
        Triangle tr1 = new Triangle(p3, p4, p5);
        System.out.println(tr1 instanceof FlatGeometry);
    }
}
