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

    /**
     * create a new render object.
     * @param imageWriter set the image writer.
     * @param scene set the scene to the render.
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this.imageWriter = new ImageWriter(imageWriter);
        this.scene = new Scene(scene);
    }

    /************** Operations ***************/

    private Color calcColor(Geometry geometry,Point3D point, Ray inRay) {
        return calcColor(geometry,point,inRay,0);//enter the recursive function one.
    }

    private Color calcColor(Geometry geometry,Point3D point, Ray inRay,int level){
        if(level==RECURSION_LEVEL) return new Color(0,0,0);//stop condition.

        Color ambientLight=scene.getAmbientLight().getIntensity();//get the ambient light

        Color emissionLight=geometry.getEmission();//get the emmision light.

        Color I0=new Color(ambientLight.getColor().getRed()+emissionLight.getColor().getRed()
                ,ambientLight.getColor().getGreen()+emissionLight.getColor().getGreen()
                ,ambientLight.getColor().getBlue()+emissionLight.getColor().getBlue());//caculate the sum of them.

        Iterator<LightSource> lights=scene.getLightsIterator();//get the iterator to pass on the light source list.
        Color diffuseLight=new Color(0,0,0);
        Color specularLight=new Color(0,0,0);
        double Kd=geometry.getMaterial().getKd();//get fixed setting of the diffuse light.
        double Ks=geometry.getMaterial().getKs();//get fixed setting of the specular light.
        Vector n=geometry.getNormal(point).normalize();//get the normal vector to the geometry in our point.
        Vector MV=new Vector(point, scene.
                getCamera().getP0()).normalize();//get the vector from the point to the position of the camera.
        while (lights.hasNext()) {//if there are more lights...
            LightSource Current = lights.next();//get the light source.
            Vector l=Current.getL(point).normalize();//get the vector from the light to the point
            Color c=Current.getIntensity(point);//get the intensity that suppose to be in this point
            int nShininess=geometry.getnShininess();//get the shininess of the geometry.
            if(!occluded(Current,point,geometry)) {//if the point is'nt occluded
                diffuseLight = diffuseLight.add(calcDiffusiveComp(Kd, n, l,c));//calculate the diffuse light at this point and light source
                specularLight = specularLight.add(calcSpecularComp(Ks, MV,n,l,nShininess,c));//calculate the specular light at this point and light source
            }
        }

        //Recursive call for a reflected ray
        Ray reflectedRay=constructReflectedRay(geometry.getNormal(point),point,inRay);//construct the reflected ray
        Map.Entry<Geometry,Point3D> reflectedEntry= findClosestIntersection(reflectedRay);//find the closesnt intersection that have to the ray.
        Color reflectedColor;
        if(reflectedEntry==null)//if there isn't any intersections
            reflectedColor=new Color(0,0,0);
        else
        {
            reflectedColor=calcColor(reflectedEntry.getKey(),reflectedEntry.getValue(),reflectedRay,level+1);//else calculate the color of the reflected ray.
        }
        double kr=geometry.getMaterial().getKr();//get fixed setting of the reflected light.
        Color reflectedLight=new Color(scaleColor(reflectedColor,kr));//calculate the reflected color.

        //Recursive call for a refracted ray
        Ray refractedRay=constructRefractedRay(geometry.getNormal(point),point,inRay);//construc the refracted ray
        Map.Entry<Geometry,Point3D> refractedEntry= findClosestIntersection(refractedRay);//find the closesnt intersection that have to the ray.
        Color refractedColor;
        if(refractedEntry==null)//if there isn't any intersections
            refractedColor=new Color(0,0,0);
        else{
            refractedColor=calcColor(refractedEntry.getKey(),refractedEntry.getValue(),refractedRay,level+1);//else calculate the color of the refracted light.
        }
        double kt=geometry.getMaterial().getKt();//get fixed setting of the refracted light.
        Color refractedLight=new Color(scaleColor(refractedColor,kt));//calculate the refracted color.

        return new Color(ambientLight.add(emissionLight,diffuseLight,specularLight,refractedLight,reflectedLight));//return the color that suppose to be at this point by Phong model
    }

    private Color improvingOccludedCalcColor(Geometry geometry, Point3D point, Ray inRay, int level){
        if(level==RECURSION_LEVEL) return new Color(0,0,0);//stop condition.

        Color ambientLight=scene.getAmbientLight().getIntensity();//get the ambient light

        Color emissionLight=geometry.getEmission();//get the emmision light.

        Color I0=new Color(ambientLight.getColor().getRed()+emissionLight.getColor().getRed()
                ,ambientLight.getColor().getGreen()+emissionLight.getColor().getGreen()
                ,ambientLight.getColor().getBlue()+emissionLight.getColor().getBlue());//calculate the sum of them.

        Iterator<LightSource> lights=scene.getLightsIterator();//get the iterator to pass on the light source list.
        Color diffuseLight=new Color(0,0,0);
        Color specularLight=new Color(0,0,0);
        double Kd=geometry.getMaterial().getKd();//get fixed setting of the diffuse light.
        double Ks=geometry.getMaterial().getKs();//get fixed setting of the specular light.
        Vector n=geometry.getNormal(point).normalize();//get the normal vector to the geometry in our point.
        Vector MV=new Vector(point, scene.
                getCamera().getP0()).normalize();//get the vector from the point to the position of the camera.
        while (lights.hasNext()) {//if there are more lights...
            LightSource Current = lights.next();//get the light source.
            Vector l=Current.getL(point).normalize();//get the vector from the light to the point
            Color c=Current.getIntensity(point).scale(improvingOccluded(Current,point,geometry));//get the internsity that supose to be in this point relate to the occlude.
            int nShininess=geometry.getnShininess();//get the shininess of the geometry.
            diffuseLight = diffuseLight.add(calcDiffusiveComp(Kd, n, l,c));//calculate the diffuse light at this point and light source
            specularLight = specularLight.add(calcSpecularComp(Ks, MV,n,l,nShininess,c));//calculate the specular light at this point and light source
        }

        //Recursive call for a reflected ray
        Ray reflectedRay=constructReflectedRay(geometry.getNormal(point),point,inRay);//construct the reflected ray
        Map.Entry<Geometry,Point3D> reflectedEntry= findClosestIntersection(reflectedRay);//find the closesnt intersection that have to the ray.
        Color reflectedColor;
        if(reflectedEntry==null)//if there isn't any intersections
            reflectedColor=new Color(0,0,0);
        else
        {
            reflectedColor=calcColor(reflectedEntry.getKey(),reflectedEntry.getValue(),reflectedRay,level+1);//else calculate the color of the reflected ray.
        }
        double kr=geometry.getMaterial().getKr();//get fixed setting of the reflected light.
        Color reflectedLight=new Color(scaleColor(reflectedColor,kr));//calculate the reflected color

        //Recursive call for a refracted ray
        Ray refractedRay=constructRefractedRay(geometry.getNormal(point),point,inRay);//construct the refracted ray
        Map.Entry<Geometry,Point3D> refractedEntry= findClosestIntersection(refractedRay);//find the closesnt intersection that have to the ray.
        Color refractedColor;
        if(refractedEntry==null)//if there isn't any intersections
            refractedColor=new Color(0,0,0);
        else{
            refractedColor=calcColor(refractedEntry.getKey(),refractedEntry.getValue(),refractedRay,level+1);//else calculate the color of the refracted light.
        }
        double kt=geometry.getMaterial().getKt();//get fixed setting of the refracted light.
        Color refractedLight=new Color(scaleColor(refractedColor,kt));//calculate the refracted color.

        return new Color(ambientLight.add(emissionLight,diffuseLight,specularLight,refractedLight,reflectedLight));//return the color that suppose to be at this point by Phong model
    }


    private Map.Entry<Geometry, Point3D> findClosestIntersection(Ray ray) {

        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);//get the intersections points with the scene.

        if (intersectionPoints.size() == 0)//if there isn't any intersection points
            return null;

        Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);//get the closest intersection points.
        return closestPoint.entrySet().iterator().next();//get the closest intersection points and return it.
    }

    private Ray constructRefractedRay(Vector normal, Point3D point, Ray inRay) {
        Vector v= inRay.getDirection().normalize();//get the direction of the ray.
        Vector no=new Vector(normal).normalize();
        no.multiply(-2);//multiply by -2 the normal
        Point3D p=new Point3D(point);
        p=p.add(no);//raise the point to prevent intersection by itself.

        return new Ray(p,v);//make the ray and return it.
    }

    private Ray constructReflectedRay(Vector normal, Point3D point, Ray inRay) {
        Vector n=new Vector(normal);
        Vector DirectionRay=new Vector(inRay.getDirection().normalize());

        double dotProduct=DirectionRay.dotProduct(n);//calculate the dot product between the normal and the direction of the ray.

        Vector R=DirectionRay.subtract(n.multiply(dotProduct*2));//calculate the R vector.
        R.normalize();

        Vector v = new Vector(normal);
        v.multiply(2);//multiply the normal by 2
        Point3D p=new Point3D(point);
        p.add(v);//raise the point to prevent intersection by itself.

        return new Ray(p,R);//make the ray.
    }

    private boolean occluded(LightSource light, Point3D point, Geometry geometry) {
        Vector lightDirection=light.getL(point).normalize();//get the light direction.
        lightDirection.multiply(-1);//change the position.

        Point3D geometryPoint=new Point3D(point);

        Vector epsVector = new Vector(geometry.getNormal(geometryPoint)).normalize();
        epsVector.multiply((epsVector.dotProduct(lightDirection) > 0) ? 2 : -2);

        geometryPoint = point.add(epsVector);//to prevent interection with itself.

        Ray lightRay =new Ray(geometryPoint,lightDirection);//make the ray.

        Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(lightRay);//get the interctions with the ray.

        if(geometry instanceof FlatGeometry) {//flat geometry can't interection with itself.
            intersectionPoints.remove(geometry);
        }
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()){
            if(entry.getKey().getMaterial().getKt()==0)//if there is geometry that doesn't clear so this point.
                return true;
        }
        return false;
    }

    private double improvingOccluded(LightSource light, Point3D point, Geometry geometry) {
        Vector lightDirection=light.getL(point).normalize();//get the light direction.
        lightDirection.multiply(-1);//change the position.

        Point3D geometryPoint=new Point3D(point);

        Vector epsVector = new Vector(geometry.getNormal(geometryPoint)).normalize();
        epsVector.multiply((epsVector.dotProduct(lightDirection) > 0) ? 2 : -2);

        geometryPoint = point.add(epsVector);//to prevent interection with itself.

        Ray lightRay =new Ray(geometryPoint,lightDirection);//make the ray.

        Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(lightRay);//get the interctions with the ray.

        if(geometry instanceof FlatGeometry) {//flat geometry can't interection wuth itself.
            intersectionPoints.remove(geometry);
        }
        double shadowK=1.0;
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()){
            shadowK*=entry.getKey().getMaterial().getKt();//to do how much the point is occluded.
        }
        return shadowK;
    }

    private Color calcSpecularComp(double ks, Vector minusVector, Vector normal, Vector l, int nShininess, Color internsity) {
        minusVector.normalize();
        Vector n=new Vector(normal.normalize()); // TODO REMOVE
        l.normalize();
        Vector R=new Vector(l).subtract(new Vector(normal).multiply(2*normal.dotProduct(l)));//calculate the R vector.
        double dot=(minusVector.dotProduct(R));
        double dotPow=pow(dot,nShininess);
        double k=max(0,ks*dotPow);

        return new Color(scaleColor( internsity,k));//calculate the specular light.
    }

    private Color calcDiffusiveComp(double kd, Vector normal, Vector l, Color intensity) {
        double dotProduct=normal.dotProduct(l);
        return new Color(scaleColor(intensity, kd * dotProduct));//calculate the diffuse color
    }

    private Color scaleColor(Color c, double factor) {
        double red=Math.min(Math.abs((c.getColor().getRed() * factor)), 255);
        double green= Math.min(Math.abs((c.getColor().getGreen() * factor)), 255);
        double blue=Math.min(Math.abs( (c.getColor().getBlue() * factor)), 255);//the max is 255
        return new Color(red,green,blue);
    }

    private Map<Geometry,Point3D> getClosestPoint(Map<Geometry,List<Point3D>> intersectionPoints){
        double distance=Double.MAX_VALUE;
        Point3D P0=scene.getCamera().getP0();
        Map<Geometry,Point3D> minDistancePoint=new HashMap<Geometry,Point3D>();
        for (Map.Entry<Geometry,List<Point3D>> entry: intersectionPoints.entrySet()) {
            for (Point3D point: entry.getValue())
            {
                if(P0.distance(point)<distance)//if we find point that more closer.
                {//put it in the variable.
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(),new Point3D(point));
                    distance=P0.distance(point);
                }
            }
        }
        return minDistancePoint;
    }

    /**
     * function that make the picture from the scene.
     */
    public void renderImage() {
        for(int i=0;i<imageWriter.getWidth();i++)
            for(int j=0;j<imageWriter.getHeight();j++)
            {
                Ray ray=scene.getCamera().constructRayThroughPixel(imageWriter.getNx(),imageWriter.getNy(),i,j,scene.getScreenDistance(),imageWriter.getWidth(),imageWriter.getHeight());//make the ray through the pixel.
                Map<Geometry,List<Point3D>> intersectionPoints=getSceneRayIntersections(ray);//find the intersection points with the ray.
                if(intersectionPoints.isEmpty())//if there isn't any intersection points with the scene
                    imageWriter.writePixel(i,j,scene.getBackground().getColor());//color the pixel with the background color.
                else
                {
                    Map<Geometry,Point3D> closestPoint=getClosestPoint(intersectionPoints);//find the closest point.
                    for (Map.Entry<Geometry,Point3D> point: closestPoint.entrySet()) {
                        imageWriter.writePixel(i, j, calcColor(point.getKey(),point.getValue() ,ray).getColor());//color the pixel with the color that suppose to be in this point.
                    }
                }
            }
    }

    /**
     * function that make the picture from the scene with super sampling.
     */
    public void renderImageWithSuperSampling() {
        for(int i=0;i<imageWriter.getWidth();i++)
            for(int j=0;j<imageWriter.getHeight();j++)
            {
                List<Ray> rays=scene.getCamera().constructRaysThroughPixel(imageWriter.getNx(),imageWriter.getNy(),i,j,scene.getScreenDistance(),imageWriter.getWidth(),imageWriter.getHeight());//make the rays through the pixel.
                List<Color> colorsRays=new ArrayList<Color>();
                for (Ray ray: rays)
                {
                    Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);//to any ray find the intersection points between the ray and the scene.
                    if (intersectionPoints.isEmpty())
                        colorsRays.add(new Color(scene.getBackground().getColor()));//add to colors the background color.
                    else {
                        Map<Geometry, Point3D> closestPoint = getClosestPoint(intersectionPoints);//get the closest point
                        for (Map.Entry<Geometry, Point3D> point : closestPoint.entrySet()) {
                            colorsRays.add(new Color(calcColor(point.getKey(), point.getValue(), ray)));//add the color of the point to the colors.
                        }
                    }
                }
                imageWriter.writePixel(i, j, average(colorsRays).getColor());//write to the pixel the average of the colors.
            }
    }

    /**
     * print grid of squre from X*X pixels.
     * @param interval the number of X.
     */
    public void printGrid(int interval) {

        int height = (int)imageWriter.getHeight();
        int width = (int)imageWriter.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (i % interval == 0 || j % interval == 0)//if we need to print the pixel.
                    imageWriter.writePixel(j, i, java.awt.Color.white);

            }
        }
    }

    private Map<Geometry,List<Point3D>> getSceneRayIntersections(Ray ray){
        return scene.getGeometries().findIntersections(ray);//find the intersections.
    }

    /**
     * make the picture
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    private Color average(List<Color> colors) {
        double Red=0, Green=0, Blue=0;
        if(colors.size()==0)
            return new Color();
        for (Color color:colors) {
            Red+=color.getColor().getRed();
            Green+=color.getColor().getGreen();
            Blue+=color.getColor().getBlue();
        }//sum all of the colors
        int size=colors.size();
        Red/=size;
        Green/=size;
        Blue/=size;//divided by the size of the List.
        return new Color(Red,Green,Blue);
    }
}
