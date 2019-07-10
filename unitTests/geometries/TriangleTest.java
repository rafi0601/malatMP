package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest
{
    Triangle tr = new Triangle(
            new Point3D(0.0, 1.0, 0.0),
            new Point3D(1.0, 0.0, 0.0),
            new Point3D(0.0, 0.0, 1.0)
    );


    @Test
    void getNormal()
    {
        Vector n = tr.getNormal(null);
        assertEquals("Vector{head=Point3D{x=0.5773502691896258, y=0.5773502691896258, z=0.5773502691896258}}", n.toString());

        Vector v = tr.getNormal(null);
        double l = v.length();
        assertEquals(1.0, l);
    }

    @Test
    void findIntersections()
    {
        Point3D x = new Point3D(-100, -100, -200);
        Point3D y = new Point3D(100, -100, -200);
        Point3D z = new Point3D(0, 100, -200);
        Triangle t = new Triangle(x, y, z);
        Vector v = new Vector(0, -1, -3);
        Point3D point3D = new Point3D(0, 0, 0);
        Point3D point = t.findIntersections(new Ray(point3D, v)).get(0);
        assertEquals(-66.6666, point.gety().get(), 0.01);


        Point3D x2 = new Point3D(10, 10, 50);
        Point3D y2 = new Point3D(20, 50, -20);
        Point3D z2 = new Point3D(30, 40, -10);
        Triangle t2 = new Triangle(x2, y2, z2);
        Vector v2 = new Vector(1, 5, 0);
        Point3D point3D2 = new Point3D(0, 0, 0);
        List<Point3D> g = t2.findIntersections(new Ray(point3D2, v2));

        boolean flag = false;
        if (g.isEmpty())
            flag = true;

        assertEquals(flag, true);
    }


}