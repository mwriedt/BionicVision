package software_a.com.bionicvision;

/**
 * Created by Mitchell on 10/10/2017.
 */

public class Phosphene {
    private int layer = 0;
    private Boolean touchingCP = Boolean.FALSE;
    private Boolean isCP = Boolean.FALSE;
    //Maybe store location(Camera/Image location) in here?
    private int xLoc;
    private int yLoc;
    private Boolean alive = Boolean.FALSE;
    private int Intensity;

    Phosphene(int yLocation, int xLocation, int fLayer)
    {
        xLoc = xLocation;
        yLoc = yLocation;
        layer = fLayer;
    }

    public int getIntensity() {
        return Intensity;
    }

    public void setIntensity(int intensity) {
        Intensity = intensity;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean lAlive) {
        alive = lAlive;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int lxLoc) {
        xLoc = lxLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int lyLoc) {
        yLoc = lyLoc;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int lLayer) {
        layer = lLayer;
    }

    public boolean getIsCP() {
        return isCP;
    }

    public void setIsCP(boolean lisCP) {
        isCP = lisCP;
    }

    public boolean getTouchingCP() {return touchingCP;}

    public void setTouchingCP(boolean lTouchingCP) {
        touchingCP = lTouchingCP;
    }
}
