package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest
{
    Plane plane=new Plane(new Point3D(3,0,0),new Vector(1,0,0));

    @Test
    void getNormal()
    {
        Vector result=plane.getNormal(null);
        assertEquals("Vector{head=Point3D{x=1.0, y=0.0, z=0.0}}",result.toString());

    }

    @Test
    void findIntersections()
    {
        Plane p=new Plane(new Point3D(100, 0,0),new Vector(-1,0,0));
        Vector v=new Vector(1,2,0);
        Point3D point3D=new Point3D(0,0,0);
        Point3D point=p.findIntersections(new Ray(point3D,v)).get(0);
        System.out.println(point);
        assertEquals(100, point.getx().get());
        assertEquals(200, point.gety().get());


        Plane p2=new Plane(new Point3D(30, 0,0),new Point3D(5,1,1),new Point3D(20,1,0));
        Vector v2=new Vector(1,2,0);
        Point3D point3D2=new Point3D(0,0,0);
        Point3D point2=p2.findIntersections(new Ray(point3D2,v2)).get(0);
        System.out.println(point2);
        assertEquals(1.43, point2.getx().get(), 0.01);
        assertEquals(2.86, point2.gety().get(), 0.01);


        Plane p3=new Plane(new Point3D(30, 0,0),new Point3D(5,1,1),new Point3D(100,1,0));
        Vector v3=new Vector(1,2,0);
        Point3D point3D3=new Point3D(0,0,0);
        List<Point3D> g = p3.findIntersections(new Ray(point3D3,v3));
        boolean flag = false;
        if(g.isEmpty())
            flag = true;
        assertEquals(flag,true);
    }

}