package software_a.com.bionicvision;

import org.opencv.core.Mat;

import java.util.List;

class BlankAlgorithm extends Algorithm
{
    BlankAlgorithm()
    {
     super.setName("Blank");
    }


    List<Phosphene> process(Mat frame, List<Phosphene> phospheneGrid, int maxGrid)
    {
        return null;
    }
}
