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

    /**
     *
     * @return return the scene name.
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     *
     * @return return the background color
     */
    public Color getBackground() {
        return background;
    }

    /**
     *
     * @return return the ambient light color.
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     *
     * @return return the geometries in the scene.
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     *
     * @return return the camera object.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     *
     * @return return the distance between the camera and the view plane.
     */
    public double getScreenDistance() {
        return screenDistance;
    }

    /**
     *
     * @param background set the background color.
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     *
     * @param ambientLight set the ambient light color.
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    /**
     *
     * @param camera set the camera object.
     * @param d set the distance between the camera and the view plane.
     */
    public void setCameraAndDistance(Camera camera, double d) {
        screenDistance=d;
        this.camera=camera;
    }

    /**
     *
     * @param geometries set the object that represent the geometries of all the scene.
     */
    public void setGeometries(Geometries geometries) {
        this.geometries=geometries;
    }

    /**
     *
     * @return return the lights source that in the scene.
     */
    public List<LightSource> getLights() {
        return Lights;
    }

    /**
     *
     * @param lights set the lights into the scene.
     */
    public void setLights(List<LightSource> lights) {
        Lights = lights;
    }

    /**
     *
     * @return return the Iterator of the light source list.
     */
    public Iterator<LightSource> getLightsIterator() {
        return Lights.listIterator();
    }

    /************** Operations ***************/

    /**
     *
     * @param geometry add geometry to the scene.
     */
    public void addGeometry(Intersectable geometry) {
        geometries.add(geometry);
    }

    /**
     *
     * @param light add light to the scene.
     */
    public void addLight(LightSource light) {
        Lights.add(light);
    }
}
