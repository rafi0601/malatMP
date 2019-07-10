package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest
{
    Tube tu=new Tube(5,new Ray(new Point3D(0,0,0),new Vector(0,0,1)));

    @Test
    void getNormal()
    {
        Vector v1=tu.getNormal(new Point3D(0,5,1));
        assertEquals("Vector{head=Point3D{x=0.0, y=1.0, z=0.0}}",v1.toString());
    }

    @Test
    void findIntersections()
    {
    }

}