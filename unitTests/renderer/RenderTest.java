package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

class RenderTest
{
    @Test
    void basicRendering()
    {
        Scene scene = new Scene("Test scene");
        scene.setCameraAndDistance(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
        scene.setBackground(new Color(0, 0, 0));
        Geometries geometries = new Geometries();
        scene.setGeometries(geometries);
        geometries.add(new Sphere(new Color(0,0,0), 50, new Point3D(0, 0, 150)));

        geometries.add(new Triangle(new Color(java.awt.Color.red), new Point3D( 100, 0, 149),
                new Point3D(  0, 100, 149),
                new Point3D( 100, 100, 149)));

        geometries.add(new Triangle(new Color(java.awt.Color.GREEN), new Point3D( 100, 0, 149),
                new Point3D(  0, -100, 149),
                new Point3D( 100,-100, 149)));

        geometries.add(new Triangle(new Color(java.awt.Color.BLUE), new Point3D(-100, 0, 149),
                new Point3D(  0, 100, 149),
                new Point3D(-100, 100, 149)));

        geometries.add(new Triangle(new Color(0,0,0), new Point3D(-100, 0, 149),
                new Point3D(  0,  -100, 149),
                new Point3D(-100, -100, 149)));
        //scene.setBackground(new Color(75,127,190));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray),0.3));
        ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50);
        render.writeToImage();
    }

    @Test
    void spotLight()
    {
        Scene scene = new Scene("spotLightTest");

        Triangle triangle = new Triangle(new Color(40, 29, 28),new Point3D(  -3500,  3500, -2000),
                new Point3D( 3500, -3500, -1000),
                new Point3D(  3500, 3500, -2000));

        Triangle triangle2 = new Triangle(new Color(40, 29, 28),new Point3D(  -3500,  3500, -2000),
                new Point3D( 3500,  -3500, -1000),
                new Point3D( -3500, -3500, -1000));

        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));
        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100)
                ,new Vector(new Point3D(-2, -2, -3)), 0, 0.000001, 0.0000005 ));


        ImageWriter imageWriter = new ImageWriter( "spotLightTest", 500, 500, 500, 500);

        Render render = new Render(imageWriter,scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void spotLight2()
    {
        Scene scene = new Scene("spotLightTest2");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.3, 2, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-200, -200, -150),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("Spot test with shadow", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void spotLight3()
    {
        Scene scene = new Scene("spotLightTest3");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.01));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-150, -200, -260),
                new Point3D(-200, -150, -260),
                new Point3D(-200, -200, -270));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(1, 1, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-200, -200, -150),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("Spot test with shadow6", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void pointLight()
    {
        Scene scene = new Scene("pointLightTest");

        Triangle triangle = new Triangle(new Color(40, 29, 28),new Point3D(  -3500,  3500, -2000),
                new Point3D( 3500, -3500, -1000),
                new Point3D(  3500, 3500, -2000));

        Triangle triangle2 = new Triangle(new Color(40, 29, 28),new Point3D(  -3500,  3500, -2000),
                new Point3D( 3500,  -3500, -1000),
                new Point3D( -3500, -3500, -1000));

        scene.addGeometry(triangle);
        scene.addGeometry(triangle2);

        scene.addLight(new PointLight(new Color(255, 100, 100), new Point3D(200, 200, -100)
                , 0, 0.000001, 0.0000005));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));
        ImageWriter imageWriter = new ImageWriter( "pointLightTest", 500, 500, 500, 500);

        Render render = new Render(imageWriter,scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void sphereSpotLight()
    {
        Scene scene = new Scene("sphere_spotLightTest");
        Sphere sphere = new Sphere(new Color(40, 0, 40),800, new Point3D(0.0, 0.0, -1000));
        sphere.setnShininess(20);

        scene.addGeometry(sphere);
        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -100)
                ,new Vector(new Point3D(2, 2, -3)),0, 0.000001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("sphere_spotLightTest", 500, 500, 500, 500);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));

        Render render = new Render(imageWriter,scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void spherePointLight()
    {
        Scene scene = new Scene("sphere_pointLightTest");
        Sphere sphere = new Sphere(new Color(0, 0, 100),800, new Point3D(0.0, 0.0, -1000));
        sphere.setnShininess(20);

        scene.addGeometry(sphere);
        scene.addLight(new PointLight(new Color(255, 100, 100), new Point3D(-200, -200, -100),
                0, 0.000001, 0.000005));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));

        ImageWriter imageWriter = new ImageWriter("sphere_pointLightTest", 500, 500, 500, 500);

        Render render = new Render(imageWriter,scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void occludedChangeLight()
    {
        Scene scene = new Scene("occludedTest");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 1, 1, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.3, 2, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-199.49, -196.23, -196.79),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("occluded Test Change Light", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void occludedChangePosition()
    {
        Scene scene = new Scene("occludedTest");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-115, -215, -250),
                new Point3D(-215, -105, -250),
                new Point3D(-205, -205, -260));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.3, 2, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-200, -200, -150),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("occluded Test Change Position", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void cylinder()
    {
        Scene scene=new Scene("CylinderTest");

        Cylinder cylinder=new Cylinder(300,new Ray(new Point3D(0,0,-500),new Vector(new Point3D(700,0,0),new Point3D(0,0,-500))),100);
        cylinder.setEmission(new Color(40, 29, 28));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));
        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100)
                ,new Vector(new Point3D(-2, -2, -3)), 0, 0.000001, 0.0000005 ));

        scene.addGeometry(cylinder);
        ImageWriter imageWriter = new ImageWriter("CylinderTest", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void tube()
    {
        Scene scene=new Scene("TubeTest");

        Tube tube=new Tube(300,new Ray(new Point3D(0,0,-500),new Vector(new Point3D(700,0,0),new Point3D(0,0,-500))));
        tube.setEmission(new Color(40, 29, 28));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK)));
        scene.addLight(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100)
                ,new Vector(new Point3D(-2, -2, -3)), 0, 0.000001, 0.0000005 ));

        scene.addGeometry(tube);
        ImageWriter imageWriter = new ImageWriter("TubeTest", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    void superSampling1()
    {
        Scene scene = new Scene("spotLightTest3");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.01));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-150, -200, -260),
                new Point3D(-200, -150, -260),
                new Point3D(-200, -200, -270));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(1, 1, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-200, -200, -150),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("Spot test with shadowSS", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImageWithSuperSampling();
        //render.printGrid(50);
        render.writeToImage();
    }

    @Test
    void superSampling2()
    {
        Scene scene = new Scene("occludedTest");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-115, -215, -250),
                new Point3D(-215, -105, -250),
                new Point3D(-205, -205, -260));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.3, 2, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-200, -200, -150),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("occluded Test Change PositionWithSS", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImageWithSuperSampling();
        render.writeToImage();
    }

    @Test
    void superSampling3()
    {
        Scene scene = new Scene("occludedTest");
        scene.setBackground(new Color(0, 0, 0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.setCameraAndDistance(new Camera(), 200);

        Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
        sphere.setMaterial(new Material(1, 1, 0, 0, 20));
        sphere.setEmission(new Color(0, 0, 100));
        scene.addGeometry(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270));

        triangle.setEmission(new Color(0, 0, 100));
        triangle.setMaterial(new Material(0.3, 2, 0, 0, 4));
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight(
                new Color(255, 100, 100), new Point3D(-199.49, -196.23, -196.79),
                new Vector(2, 2, -3),0.1, 0.00001, 0.000005));


        ImageWriter imageWriter = new ImageWriter("occluded Test Change Light withSuperSampling", 500, 500, 500, 500);

        Render render = new Render(imageWriter, scene);
        render.renderImageWithSuperSampling();
        render.writeToImage();
    }

}