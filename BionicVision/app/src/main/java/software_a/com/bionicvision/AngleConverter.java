package software_a.com.bionicvision;

public class AngleConverter
{
    private double screenHeight = 7.26;
    private double screenDist = 2.0;
    private int screenResolution = 1440;

    public double convertAngleToPixels(int phospheneSize)
    {
        double degree = Math.toDegrees(Math.atan2(0.5 * screenHeight, screenDist)) / (.5 * screenResolution);
        return degree / phospheneSize;
    }
}
