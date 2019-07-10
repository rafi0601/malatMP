package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private Point3D P0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    /************** Getters/Setters *******/

    public Point3D getP0() {
        return P0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    /********** Constructors ***********/

    public Camera(Point3D location,Vector up,Vector To) {
        P0 = new Point3D(location);
        if (!(up.dotProduct(To) == 0))
            throw new IllegalArgumentException("The vectors must be vertical.");
        up.normalize();
        To.normalize();
        vUp = new Vector(up);
        vTo = new Vector(To);
        vRight = up.crossProduct(To).normalize();
    }

    public Camera(){
        this.P0=new Point3D(0,0,0);
        this.vTo=new Vector(0,0,-1);
        this.vUp=new Vector(1,0,0);
        vRight = vUp.crossProduct(vTo).normalize();
    }

    /************** Operations ***************/

    public Ray constructRayThroughPixel(int Nx ,int Ny,double i,double j,double screenDistance,double screenWidth,double screenHeight) {
        Point3D Pc=getP0().add(new Vector(vTo).multiply(screenDistance));//pc=p0+d*vTo
        double Rx=screenWidth/Nx;
        double Ry=screenHeight/Ny;
        double yj=(j-Ny/2.0)*Ry-Ry/2.0;
        double xi=(i-Nx/2.0)*Rx-Rx/2.0;
        Point3D Pij= Pc.add(new Vector(vRight).multiply(xi).subtract(new Vector(vUp).multiply(yj)));//Pij=pc+(xi*vRight-yj*vUp)
        Vector Vij=new Vector(getP0(),Pij);
        Vij.normalize();
        return new Ray(getP0(),Vij);
    }

    public List<Ray> constructRaysThroughPixel(int Nx ,int Ny,int i,int j,double screenDistance,double screenWidth,double screenHeight) {
        List<Ray> rays=new ArrayList<Ray>();
        Ray center=constructRayThroughPixel(Nx,Ny,i,j,screenDistance,screenWidth,screenHeight);
        Ray Right=constructRayThroughPixel(Nx,Ny,i+0.4,j,screenDistance,screenWidth,screenHeight);
        Ray Left=constructRayThroughPixel(Nx,Ny,i-0.4,j,screenDistance,screenWidth,screenHeight);
        Ray Up=constructRayThroughPixel(Nx,Ny,i,j+0.4,screenDistance,screenWidth,screenHeight);
        Ray Down=constructRayThroughPixel(Nx,Ny,i,j-0.4,screenDistance,screenWidth,screenHeight);
        rays.add(center);
        rays.add(Right);
        rays.add(Left);
        rays.add(Up);
        rays.add(Down);
        return rays;
    }
}
