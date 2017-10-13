package software_a.com.bionicvision;

/**
 * Created by Mitchell on 10/10/2017.
 */

public class Phosphene {
    public int layer = 0;
    public Boolean touchingCP = Boolean.FALSE;
    public Boolean isCP = Boolean.FALSE;
    //Maybe store location(Camera/Image location) in here?
    public int xLoc;
    public int yLoc;
    public Boolean alive = Boolean.FALSE;
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
}
