package geometries;

import primitives.Color;

public abstract class RadialGeometry extends Geometry  {
    protected double _radius;

    /********** Constructors ***********/

    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
    }

    public RadialGeometry(Color color, double _radius) {
        super(color);
        this._radius = _radius;
    }

    /************** Getters/Setters *******/

    public double get_radius() {
        return _radius;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
