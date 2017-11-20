package software_a.com.bionicvision;

class FieldOfView
{
    // ***********************************************
    // PERFORM THIS FUNCTION FOR BOTH WIDTH AND HEIGHT
    // ***********************************************
    static int CalculateCaptureFoV(double degrees, int maxLength)
    {
        degrees = Math.toRadians(degrees);

        // STAGE 1: Work out virtual distance 'maxAngle'
        double maxAngle = 75;

        // Cut the triangle in half to get a right angle
        double theta = Math.toRadians((maxAngle) / 2);
        System.out.print(theta + "\n");

        // calculate 'distance' to convert triangle to pixels
        double distance = (maxLength / 2) / Math.tan(theta);
        System.out.println("Distnace: " + distance);

        // STAGE 2: Calculate height at given angle

        // Use the Cosine Rule to evaluate the height of the frame
        double height = distance * Math.tan(degrees / 2);

        return (int) Math.ceil(height * 2);
    }
}
