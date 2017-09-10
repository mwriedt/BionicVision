package software_a.com.bionicvision;

class AlgorithmSwitch
{
    public static Algorithm choose(String algorithm)
    {
        // the AlgorithmSwitch class is used to generate an algorithm for the camera frame.
        // depending on the option selected in the settings activity, the case will change
        // and a certain algorithm will be rendered to the screen.
        switch(algorithm)
        {
            case ("Intensity"):
                return new IntensityAlgorithm();
            case ("Edge Detection"):
                return new EdgeDetectionAlgorithm();
            case ("Blank"):
                return new BlankAlgorithm();
        }
        return null;
    }
}
