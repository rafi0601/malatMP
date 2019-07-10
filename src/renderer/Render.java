package renderer;

import elements.LightSource;
import geometries.FlatGeometry;
import geometries.Geometry;
import primitives.*;
import primitives.Vector;
import scene.Scene;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.StrictMath.max;

public class Render {
    private ImageWriter imageWriter;
    private Scene scene;
    private final int RECURSION_LEVEL = 3;

    // ***************** Constructors ********************** //

    public Render(ImageWriter imageWriter, Scene scene) {
        this.imageWriter = new ImageWriter(imageWriter);
        this.scene = new Scene(scene);
    }

    /************** Operations ***************/

    private Color calcColor(Geometry geometry,Point3D point, Ray inRay) {
        return calcColor(geometry,point,inRay,0);
    }

    private Color calcColor(Geometry geometry,Point3D point, Ray inRay,int level){
        if(level==RECURSION_LEVEL) return new Color(0,0,0);

        Color ambientLight=scene.getAmbientLight().getIntensity();

        Color emissionLight=geometry.getEmission();

        Color I0=new Color(ambientLight.getColor().getRed()+emissionLight.getColor().getRed()
                ,ambientLight.getColor().getGreen()+emissionLight.getColor().getGreen()
                ,ambientLight.getColor().getBlue()+emissionLight.getColor().getBlue());

        Iterator<LightSource> lights=scene.getLightsIterator();
        Color diffuseLight=new Color(0,0,0);
        Color specularLight=new Color(0,0,0);
        double Kd=geometry.getMaterial().getKd();
        double Ks=geometry.getMaterial().getKs();
        Vector n=geometry.getNormal(point).normalize();
        Vector MV=new Vector(point, scene.
                getCamera().getP0()).normalize();
        while (lights.hasNext()) {
            LightSource Current = lights.next();
            Vector l=Current.getL(point).normalize();
            Color c=Current.getIntensity(point);
            int nShininess=geometry.getnShininess();
            if(!occluded(Current,point,geometry)) {
                diffuseLight = diffuseLight.add(calcDiffusiveComp(Kd, n, l,c));
                specularLight = specularLight.add(calcSpecularComp(Ks, MV,n,l,nShininess,c));
            }
        }

        //Recursive call for a reflected ray
        Ray reflectedRay=constructReflectedRay(geometry.getNormal(point),point,inRay);
        Map.Entry<Geometry,Point3D> reflectedEntry= findClosestIntersection(reflectedRay);
        Color reflectedColor;
        if(reflectedEntry==null)
            reflectedColor=new Color(0,0,0);
        else
        {
            reflectedColor=calcColor(reflectedEntry.getKey(),reflectedEntry.getValue(),reflectedRay,level+1);
        }
        double kr=geometry.getMaterial().getKr();
        Color reflectedLight=new Color(scaleColor(reflectedColor,kr));

        //Recursive call for a refracted ray
        Ray refractedRay=constructRefractedRay(geometry.getNormal(point),point,inRay);
        Map.Entry<Geometry,Point3D> refractedEntry= findClosestIntersection(refractedRay);
        Color refractedColor;
        if(refractedEntry==null)
            refractedColor=new Color(0,0,0);
        else{
            refractedColor=calcColor(refractedEntry.getKey(),refractedEntry.getValue(),refractedRay,level+1);
        }
        double kt=geometry.getMaterial().getKt();
        Color refractedLight=new Color(scaleColor(refractedColor,kt));

        return new Color(ambientLight.add(emissionLight,diffuseLight,specularLight,refractedLight,reflectedLight));
    }

    private Color improvingOccludedCalcColor(Geometry geometry, Point3D point, Ray inRay, int level){
        if(level==RECURSION_LEVEL) return new Color(0,0,0);

        Color ambientLight=scene.getAmbientLight().getIntensity();

        Color emissionLight=geometry.getEmission();

        Color I0=new Color(ambientLight.getColor().getRed()+emissionLight.getColor().getRed()
                ,ambientLight.getColor().getGreen()+emissionLight.getColor().getGreen()
                ,ambientLight.getColor().getBlue()+emissionLight.getColor().getBlue());

        Iterator<LightSource> lights=scene.getLightsIterator();
        Color diffuseLight=new Color(0,0,0);
        Color specularLight=new Color(0,0,0);
        double Kd=geometry.getMaterial().getKd();
        double Ks=geometry.getMaterial().getKs();
        Vector n=geometry.getNormal(point).normalize();
        Vector MV=new Vector(point, scene.
                getCamera().getP0()).normalize();
        while (lights.hasNext()) {
            LightSource Current = lights.next();
            Vector l=Current.getL(point).normalize();
            Color c=Current.getIntensity(point).scale(improvingOccluded(Current,point,geometry));
            int nShininess=geometry.getnShininess();
            diffuseLight = diffuseLight.add(calcDiffusiveComp(Kd, n, l,c));
            specularLight = specularLight.add(calcSpecularComp(Ks, MV,n,l,nShininess,c));
        }

        //Recursive call for a reflected ray
        Ray reflectedRay=constructReflectedRay(geometry.getNormal(point),point,inRay);
        Map.Entry<Geometry,Point3D> reflectedEntry= findClosestIntersection(reflectedRay);
        Color reflectedColor;
        if(reflectedEntry==null)
            reflectedColor=new Color(0,0,0);
        else
        {
            reflectedColor=calcColor(reflectedEntry.getKey(),reflectedEntry.getValue(),reflectedRay,level+1);
        }
        double kr=geometry.getMaterial().getKr();
        Color reflectedLight=new Color(scaleColor(reflectedColor,kr));

        //Recursive call for a refracted ray
        Ray refractedRay=constructRefractedRay(geometry.getNormal(point),point,inRay);
        Map.Entry<Geometry,Point3D> refractedEntry= findClosestIntersection(refractedRay);
        Color refractedColor;
        if(refractedEntry==null)
            refractedColor=new Color(0,0,0);
        else{
            refractedColor=calcColor(refractedEntry.getKey(),refractedEntry.getValue(),refractedRay,level+1);
        }
        double kt=geometry.getMaterial().getKt();
        Color refractedLight=new Color(scaleColor(refractedColor,kt));

        return new Color(ambientLight.add(emissionLight,diffuseLight,specularLight,refractedLight,reflectedLight));
    }



    private Map.Entry<Geometry, Point3D> findClosestIntersection(Ray ray) {

        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);

        if (intersectionPoints.size() == 0)
            return null;

        Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
        return closestPoint.entrySet().iterator().next();
    }
    private Ray constructRefractedRay(Vector normal, Point3D point, Ray inRay) {
        Vector v= inRay.getDirection().normalize();
        Vector no=new Vector(normal).normalize();
        no.multiply(-2);
        Point3D p=new Point3D(point);
        p=p.add(no);

        return new Ray(p,v);
    }

    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) {
        Vector n=new Vector(normal);
        Vector DirectionRay=new Vector(inRay.getDirection().normalize());

        double dotProduct=DirectionRay.dotProduct(n);

        Vector R=DirectionRay.subtract(n.multiply(dotProduct*2));
        R.normalize();

        Vector v = new Vector(normal);
        v.multiply(2);
        Point3D p=new Point3D(point);
        p.add(v);

        return new Ray(p,R);
    }

    private boolean occluded(LightSource light, Point3D point, Geometry geometry) {
        Vector lightDirection=light.getL(point).normalize();
        lightDirection.multiply(-1);

        Point3D geometryPoint=new Point3D(point);

        Vector epsVector = new Vector(geometry.getNormal(geometryPoint)).normalize();
        epsVector.multiply((epsVector.dotProduct(lightDirection) > 0) ? 2 : -2);

        geometryPoint = point.add(epsVector);

        Ray lightRay =new Ray(geometryPoint,lightDirection);

        Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(lightRay);

        if(geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()){
            if(entry.getKey().getMaterial().getKt()==0)
                return true;
        }
        return false;
    }

    private double improvingOccluded(LightSource light, Point3D point, Geometry geometry) {
        Vector lightDirection=light.getL(point).normalize();
        lightDirection.multiply(-1);

        Point3D geometryPoint=new Point3D(point);

        Vector epsVector = new Vector(geometry.getNormal(geometryPoint));
        epsVector.multiply((epsVector.dotProduct(lightDirection) > 0) ? 2 : -2);

        geometryPoint = point.add(epsVector);

        Ray lightRay =new Ray(geometryPoint,lightDirection);

        Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(lightRay);

        if(geometry instanceof FlatGeometry) {
            intersectionPoints.remove(geometry);
        }
        double shadowK=1.0;
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()){
            shadowK*=entry.getKey().getMaterial().getKt();
        }
        return shadowK;
    }

    private Color calcSpecularComp(double ks, Vector minusVector, Vector normal, Vector l, int nShininess, Color internsity) {
        minusVector.normalize();
        Vector n=new Vector(normal.normalize()); // TODO REMOVE
        l.normalize();
        Vector R=new Vector(l).subtract(new Vector(normal).multiply(2*normal.dotProduct(l)));
        double dot=(minusVector.dotProduct(R));
        double dotPow=pow(dot,nShininess);
        double k=max(0,ks*dotPow);

        return new Color(scaleColor( internsity,k));
    }

    private Color calcDiffusiveComp(double kd, Vector normal, Vector l, Color internsity) {
        double dotProduct=normal.dotProduct(l);
        return new Color(scaleColor(internsity, kd * dotProduct));
    }

    public Color scaleColor(Color c, double factor) {
        double red=Math.min(Math.abs((c.getColor().getRed() * factor)), 255);
        double green= Math.min(Math.abs((c.getColor().getGreen() * factor)), 255);
        double blue=Math.min(Math.abs( (c.getColor().getBlue() * factor)), 255);
        return new Color(red,green,blue);
    }

    private Map<Geometry,Point3D> getClosestPoint(Map<Geometry,List<Point3D>> intersectionPoints){
        double distance=Double.MAX_VALUE;
        Point3D P0=scene.getCamera().getP0();
        Map<Geometry,Point3D> minDistancePoint=new HashMap<Geometry,Point3D>();
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()) {
            for (Point3D point: entry.getValue())
            {
                if(P0.distance(point)<distance)
                {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(),new Point3D(point));
                    distance=P0.distance(point);
                }
            }
        }
        return minDistancePoint;
    }

    public void renderImage() {
        for(int i=0;i<imageWriter.getWidth();i++)
            for(int j=0;j<imageWriter.getHeight();j++)
            {
                Ray ray=scene.getCamera().constructRayThroughPixel(imageWriter.getNx(),imageWriter.getNy(),i,j,scene.getScreenDistance(),imageWriter.getWidth(),imageWriter.getHeight());
                Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(ray);
                if(intersectionPoints.isEmpty())
                    imageWriter.writePixel(i,j,scene.getBackground().getColor());
                else
                {
                    Map<Geometry,Point3D> closestPoint=getClosestPoint(intersectionPoints);
                    for (Map.Entry<Geometry,Point3D> point: closestPoint.entrySet()) {
                        imageWriter.writePixel(i, j, calcColor(point.getKey(),point.getValue() ,ray).getColor());
                    }
                }
            }
    }

    public void renderImageWithSuperSampling() {
        for(int i=0;i<imageWriter.getWidth();i++)
            for(int j=0;j<imageWriter.getHeight();j++)
            {
                List<Ray> rays=scene.getCamera().constructRaysThroughPixel(imageWriter.getNx(),imageWriter.getNy(),i,j,scene.getScreenDistance(),imageWriter.getWidth(),imageWriter.getHeight());
                List<Color> colorsRays=new ArrayList<Color>();
                for (Ray ray: rays)
                {
                    Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                    if (intersectionPoints.isEmpty())
                        colorsRays.add(new Color(scene.getBackground().getColor()));
                    else {
                        Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);
                        for (Map.Entry<Geometry, Point3D> point : closestPoint.entrySet()) {
                            colorsRays.add(new Color(calcColor(point.getKey(), point.getValue(), ray)));
                        }
                    }
                }
                imageWriter.writePixel(i, j, average(colorsRays).getColor());
            }
    }

    public void printGrid(int interval) {

        int height = (int)imageWriter.getHeight();
        int width = (int)imageWriter.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, java.awt.Color.white);

            }
        }
    }

    private Map<Geometry,List<Point3D>> getSceneRayIntersections(Ray ray){
        return scene.getGeometries().findIntersections(ray);
    }

    public void writeToImage() {
        imageWriter.writeToImage();
    }

    public Color average(List<Color> colors) {
        double Red=0, Green=0, Blue=0;
        if(colors.size()==0)
            return new Color();
        for (Color color:colors) {
            Red+=color.getColor().getRed();
            Green+=color.getColor().getGreen();
            Blue+=color.getColor().getBlue();
        }
        int size=colors.size();
        Red/=size;
        Green/=size;
        Blue/=size;
        return new Color(Red,Green,Blue);
    }
}
