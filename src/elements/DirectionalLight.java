package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector Direction;

    /********** Constructors ***********/

    public DirectionalLight(Color color, Vector direction){
        super(color);
        Direction=new Vector(direction);
        Direction.normalize();
    }

    /*************** Admin *****************/

    @Override
    public Color getIntensity() {
        return new Color(this.color);
    }

    @Override
    public Color getIntensity(Point3D point) {
        return new Color(this.color);
    }

    @Override
    public Vector getL(Point3D point) {
        return Direction.normalize();
    }
}
