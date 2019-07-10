package scene;

import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;
import elements.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scene {
    private String sceneName;
    private Color background;
    private AmbientLight ambientLight;
    private List<LightSource> Lights;
    private Geometries geometries;
    private Camera camera;
    private double screenDistance;

    /********** Constructors ***********/

    public Scene(String sceneName) {
        this.sceneName = sceneName;
        geometries=new Geometries();
        ambientLight=new AmbientLight();
        background =new Color(java.awt.Color.BLACK);
        camera=new Camera();
        Lights=new ArrayList<LightSource>();
        screenDistance=100;
    }

    public Scene(Scene scene) {
        background = scene.getBackground();
        ambientLight = scene.getAmbientLight();
        geometries = scene.geometries;
        camera= scene.getCamera();
        screenDistance = scene.screenDistance;
        Lights=new ArrayList<LightSource>(scene.Lights);
    }

    /************** Getters/Setters *******/

    public String getSceneName() {
        return sceneName;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getScreenDistance() {
        return screenDistance;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    public void setCameraAndDistance(Camera camera,double d) {
        screenDistance=d;
        this.camera=camera;
    }

    public void setGeometries(Geometries geometries) {
        this.geometries=geometries;
    }

    public List<LightSource> getLights() {
        return Lights;
    }

    public void setLights(List<LightSource> lights) {
        Lights = lights;
    }

    public void addLight(LightSource light) {
        Lights.add(light);
    }

    public Iterator<LightSource> getLightsIterator() {
        return Lights.listIterator();
    }

    /************** Operations ***************/

    public void addGeometry(Intersectable geometry) {
        geometries.add(geometry);
    }
}
