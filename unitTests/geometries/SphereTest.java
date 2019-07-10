package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest
{
    Sphere sphere=new Sphere(5,new Point3D(0,0,0));

    @Test
    void getNormal()
    {
        Vector v1=sphere.getNormal(new Point3D(0,0,5));
        assertEquals("Vector{head=Point3D{x=0.0, y=0.0, z=1.0}}",v1.toString());
    }

    @Test
    void findIntersections()
    {
        Sphere s=new Sphere(10,new Point3D(20,20,0));
        Ray ray=new Ray(new Point3D(0,0,0),new Vector(1,1,0));
        List<Point3D> g=s.findIntersections(ray);
        if(g.isEmpty())
            System.out.println("is empty!");

        System.out.println(g.get(0));
        System.out.println(g.get(1));

        assertEquals(12.93,g.get(1).getx().get(),0.01);
        assertEquals(27.07,g.get(0).getx().get(),0.01);
        //assertEquals("Vector{head=Point3D{x=1.0, y=0.0, z=0.0}}",vector.toString());


        Sphere s2=new Sphere(10,new Point3D(0,0,0));
        Ray ray2=new Ray(new Point3D(0,0,0),new Vector(1,1,0));
        List<Point3D> g2=s2.findIntersections(ray2);
        if(g2.isEmpty())
            System.out.println("is empty!");

        System.out.println(g2.get(0));

        assertEquals(7.07,g2.get(0).getx().get(),0.01);
        //assertEquals("Vector{head=Point3D{x=1.0, y=0.0, z=0.0}}",vector.toString());


        Sphere s3=new Sphere(10,new Point3D(20,20,0));
        Ray ray3=new Ray(new Point3D(0,0,0),new Vector(10,1,0));
        List<Point3D> g3=s3.findIntersections(ray3);
        String a = new String();

        if(g3.isEmpty()) {
            a = new String("is empty!");
        }

        assertEquals(a,"is empty!");
        //assertEquals("Vector{head=Point3D{x=1.0, y=0.0, z=0.0}}",vector.toString());

    }

}