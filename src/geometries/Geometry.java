package geometries;

import primitives.*;

import static java.awt.Color.*;

public abstract class Geometry implements Intersectable {
    private Material material;
    private Color emission;

    /********** Constructors ***********/

    public Geometry() {
        emission =new Color(BLACK);
        material=new Material();
    }

    public Geometry(Color color){
        emission =new Color(color);
        material=new Material();

    }

    public Geometry(Material material, Color emission) {
        this.material = material;
        this.emission = emission;
    }

    /************** Getters/Setters *******/

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Color getEmission() {
        return emission;
    }

    public void setEmission(Color emission) {
        this.emission = emission;
    }

    public int getnShininess() {
        return material.getnShininess();
    }

    public void setnShininess(int nShininess) {
        this.material.setnShininess( nShininess);
    }

    /************** Operations ***************/

    public abstract Vector getNormal(Point3D p1);

}
