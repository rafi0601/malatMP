package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {

    /************** Operations ***************/

    Color getIntensity(Point3D point);

    Vector getL(Point3D point);
}
