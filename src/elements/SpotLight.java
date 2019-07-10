package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;

    /********** Constructors ***********/

    public SpotLight(Color color, Point3D position, Vector direction,double kc, double kl, double kq) {
        super(color, position, kc, kl, kq);
        this.direction = new Vector(direction);
        this.direction.normalize();
    }

    /*************** Admin *****************/

    @Override
    public Color getIntensity() {
        return null;
    }

    @Override
    public Color getIntensity(Point3D point) {
        Color I0=super.getIntensity(point);

        Vector dir=new Vector(direction).normalize();
        Vector I=this.getL(point).normalize();

        return this.scaleColor(I0,dir.dotProduct(I));
    }

}
