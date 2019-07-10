import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;

public class RecursiveTest {

    @Test
    public void recursive(){
        Scene scene = new Scene("recursiveTest");
        scene.setCameraAndDistance(new Camera(),300);
        scene.setAmbientLight(new AmbientLight(0.1));
        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0.5, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(250, new Point3D(0.0, 0.0, -1000));
        sphere2.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere2.setEmission(new Color(255, 0, 0));
        scene.addGeometry(sphere2);

        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150), new Vector(2, 2, -3), 0.1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("Recursive Test 5", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void recursive2(){

        Scene scene = new Scene("recursiveTest2");
        scene.setCameraAndDistance(new Camera(),300);
        scene.setAmbientLight(new AmbientLight(0.1));
        Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000));
        sphere.setMaterial(new Material(1,1,0,0.5,200));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000));
        sphere2.setMaterial(new Material(1,1,0,0,200));
        sphere2.setEmission(new Color(100, 20, 20));
        scene.addGeometry(sphere2);

        Triangle triangle = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D(  200,  200, -375));

        Triangle triangle2 = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D( -1500, -1500, -1500));

        triangle.setEmission(new Color(20, 20, 20));
        triangle2.setEmission(new Color(20, 20, 20));
        triangle.setMaterial(new Material(1,1,1,0,1));
        triangle2.setMaterial(new Material(1,1,0.5,0,1));
        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        scene.addLight(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),new Vector(-2, -2, -3),
                0, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("Recursive Test 6 IS", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImage();
        //render.printGrid(50);
        render.writeToImage();
    }

    @Test
    public void recursiveSS(){
        Scene scene = new Scene("recursiveTest");
        scene.setCameraAndDistance(new Camera(),300);
        scene.setAmbientLight(new AmbientLight(0.1));
        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0.5, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(250, new Point3D(0.0, 0.0, -1000));
        sphere2.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere2.setEmission(new Color(255, 0, 0));
        scene.addGeometry(sphere2);

        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150), new Vector(2, 2, -3), 0.1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("Recursive Test 5SS", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImageWithSuperSampling();
        render.writeToImage();
    }

    @Test
    public void recursive2SS(){

        Scene scene = new Scene("recursiveTest2");
        scene.setCameraAndDistance(new Camera(),300);
        scene.setAmbientLight(new AmbientLight(0.1));
        Sphere sphere = new Sphere(300, new Point3D(-550, -500, -1000));
        sphere.setMaterial(new Material(1,1,0,0.5,200));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Sphere sphere2 = new Sphere(150, new Point3D(-550, -500, -1000));
        sphere2.setMaterial(new Material(1,1,0,0,200));
        sphere2.setEmission(new Color(100, 20, 20));
        scene.addGeometry(sphere2);

        Triangle triangle = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D(  200,  200, -375));

        Triangle triangle2 = new Triangle(new Point3D(  1500, -1500, -1500),
                new Point3D( -1500,  1500, -1500),
                new Point3D( -1500, -1500, -1500));

        triangle.setEmission(new Color(20, 20, 20));
        triangle2.setEmission(new Color(20, 20, 20));
        triangle.setMaterial(new Material(1,1,1,0,1));
        triangle2.setMaterial(new Material(1,1,0.5,0,1));
        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        scene.addLight(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),new Vector(-2, -2, -3),
                0, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("Recursive Test 6SS", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);

        render.renderImageWithSuperSampling();
        //render.printGrid(50);
        render.writeToImage();
    }
}
