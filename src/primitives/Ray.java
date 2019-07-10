package primitives;

import java.util.Objects;

public class Ray {
    private Point3D poo;
    private Vector direction;

    /********** Constructors ***********/

    public Ray(Point3D poo, Vector direction) {
        this.poo = poo;
        this.direction = direction;
        this.direction.normalize();
    }

    public Ray() {
        this.poo =new Point3D();
        this.direction=new Vector();
    }

    public Ray(Ray other) {
        this.poo =new Point3D(other.poo);
        this.direction=new Vector(other.direction);
        this.direction.normalize();
    }

    /************** Getters/Setters *******/

    public Point3D getPoo() {
        return poo;
    }

    public void setPoo(Point3D poo) {
        this.poo = poo;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return Objects.equals(poo, ray.poo) &&
                Objects.equals(direction, ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "poo=" + poo +
                ", direction=" + direction +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(poo, direction);
    }
}
