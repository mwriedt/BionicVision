package software_a.com.bionicvision;

import org.opencv.core.Mat;

class BlankAlgorithm extends Algorithm
{
    BlankAlgorithm()
    {
     super.setName("Blank");
    }

    Mat process(Mat frame)
    {
        return null;
    }
}
