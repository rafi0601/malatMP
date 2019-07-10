package elements;

import primitives.Color;

public abstract class Light {
    protected Color color;

    /********** Constructors ***********/

    public Light(Color color){
        this.color=color;
    }

    /************** Operations ***************/

    public abstract Color getIntensity();

    public Color scaleColor(Color c, double factor) {
        return new Color(Math.min(Math.abs((int) (c.getColor().getRed() * factor)), 255), Math.min(Math.abs((int) (c.getColor().getGreen() * factor)), 255), Math.min(Math.abs((int) (c.getColor().getBlue() * factor)), 255));
    }
}
