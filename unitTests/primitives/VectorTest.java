package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest
{

    @Test
    void add()
    {
        Vector v1=new Vector(new Point3D(1.0,2.0,0.0));
        Vector v2=new Vector(new Point3D(0.0,1.0,2.0) );
        v1.add(v2);
        assertEquals("Vector{head=Point3D{x=1.0, y=3.0, z=2.0}}",v1.toString());
        v2=new Vector();
        try
        {
            v2.add(v2);
            fail("its a zero Vector");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
    }

    @Test
    void subtract()
    {
        Vector v1=new Vector(new Point3D(1.0,2.0,0.0));
        Vector v2=new Vector(new Point3D(0.0,1.0,2.0) );
        v1.subtract(v2);
        assertEquals("Vector{head=(1.0, 1.0, -2.0)}",v1.toString());
        v2=new Vector();
        try
        {
            v2.subtract(v2);
            fail("its a zero Vector");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
    }

    @Test
    void multiply()
    {
        Vector v1=new Vector(new Point3D(1.0,2.0,0.0));
        v1.multiply(5);
        assertEquals("Vector{head=Point3D{x=5.0, y=10.0, z=0.0}}",v1.toString());
        try
        {
            v1.multiply(0);
            fail("its a zero Vector");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
    }

    @Test
    void dotProduct()
    {
        Vector v1=new Vector(new Point3D(1.0,0.0,0.0));
        Vector v2=new Vector(new Point3D(0.0,1.0,0.0) );
        double result=v1.dotProduct(v2);
        assertEquals(0,result);
        v1=new Vector(new Point3D(1.0,2.0,3.0));
        v2=new Vector(new Point3D(2.0,4.0,6.0));
        result=v1.dotProduct(v2);
        assertEquals(28,result);
        v1=new Vector(new Point3D(2.0,2.0,3.0));
        v2=new Vector(new Point3D(1.0,2.0,3.0));
        result=v1.dotProduct(v2);
        assertEquals(15,result);
        v1=new Vector(new Point3D(1.0,2.0,3.0));
        v2=new Vector(new Point3D(-1.0,-2.0,-3.0));
        //v1.multiply(-1);
        result=v1.dotProduct(v2);
        assertEquals(-14,result);
        v1=new Vector(new Point3D(2.0,2.0,3.0));
        v2=new Vector(new Point3D(-1.0,2.0,-3.0));
        result=v1.dotProduct(v2);
        assertEquals(-7,result);
    }

    @Test
    void crossProduct()
    {
        Vector v1=new Vector(new Point3D(1.0,0.0,0.0));
        Vector v2=new Vector(new Point3D(0.0,1.0,0.0) );
        Vector v3=v1.crossProduct(v2);
        v3.normalize();
        assertEquals("Vector{head=Point3D{x=0.0, y=0.0, z=1.0}}",v3.toString());
        v1=new Vector(new Point3D(1.0,2.0,3.0));
        v2=new Vector(new Point3D(2.0,4.0,6.0));
        try {
            v3 = v1.crossProduct(v2);
            fail("its a zero Vector!!");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
        v1=new Vector(new Point3D(1.0,2.0,3.0));
        v2=new Vector(new Point3D(-1.0,-2.0,-3.0));
        try {
            v3 = v1.crossProduct(v2);
            fail("its a zero Vector!!");
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
        v1=new Vector(new Point3D(2.0,2.0,3.0));
        v2=new Vector(new Point3D(-1.0,2.0,-3.0));
        v3=v1.crossProduct(v2);
        assertEquals("Vector{head=Point3D{x=-12.0, y=3.0, z=6.0}}",v3.toString());
        v1=new Vector(new Point3D(2.0,2.0,3.0));
        v2=new Vector(new Point3D(1.0,2.0,3.0));
        v3=v1.crossProduct(v2);
        assertEquals("Vector{head=Point3D{x=0.0, y=-3.0, z=2.0}}",v3.toString());
    }

    @Test
    void length()
    {
        Vector v1=new Vector(new Point3D(1.0,0.0,0.0));
        assertEquals(1,v1.length());
        Vector v2=new Vector(new Point3D(1,2,3));
        assertEquals(Math.sqrt(14),v2.length());
    }

    @Test
    void normalize()
    {
        Vector v = new Vector(new Point3D(.5, -5, 10));
        v.normalize();
        assertEquals(1, v.length(), 1e-10);
        v = new Vector();
        try {
            v.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

}