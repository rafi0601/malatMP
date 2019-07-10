package primitives;

public class Material {
    private double Kd;
    private double Ks;
    private double Kr;
    private double Kt;
    private int nShininess;

    /********** Constructors ***********/

    public Material(double kd, double ks, int nShininess) {
        Kd = kd;
        Ks = ks;
        this.nShininess=nShininess;
        Kr=0;
        Kt=0;
    }

    public Material() {
        Kd = 1.0;
        Ks = 1.0;
        Kr=0.0;
        Kt=0.0;
        nShininess=4;

    }

    public Material(double kd, double ks, double kr, double kt, int nShininess) {
        Kd = kd;
        Ks = ks;
        Kr = kr;
        Kt = kt;
        this.nShininess = nShininess;
    }

    /************** Getters/Setters *******/

    public double getKd() {
        return Kd;
    }

    public void setKd(double kd) {
        Kd = kd;
    }

    public double getKs() {
        return Ks;
    }

    public void setKs(double ks) {
        Ks = ks;
    }

    public int getnShininess() {
        return nShininess;
    }

    public void setnShininess(int nShininess) {
        this.nShininess = nShininess;
    }

    public double getKr() {
        return Kr;
    }

    public void setKr(double kr) {
        Kr = kr;
    }

    public double getKt() {
        return Kt;
    }

    public void setKt(double kt) {
        Kt = kt;
    }
}
