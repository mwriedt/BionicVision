package software_a.com.bionicvision;

class Phosphene
{
    private int layer = 0;
    private Boolean touchingCP = Boolean.FALSE;
    private Boolean isCP = Boolean.FALSE;
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

    int getIntensity() {return Intensity;}
    void setIntensity(int intensity) {Intensity = intensity;}
    boolean getAlive() {return alive;}
    void setAlive(boolean lAlive) {alive = lAlive;}
    int getxLoc() {return xLoc;}
    int getyLoc() {return yLoc;}
    int getLayer() {return layer;}
    void setLayer(int lLayer) {layer = lLayer;}
    boolean getIsCP() {return isCP;}
    void setIsCP(boolean lisCP) {isCP = lisCP;}
    boolean getTouchingCP() {return touchingCP;}
    void setTouchingCP(boolean lTouchingCP) {touchingCP = lTouchingCP;}
}
