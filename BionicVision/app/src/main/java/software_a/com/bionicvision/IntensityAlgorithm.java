package software_a.com.bionicvision;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;

class IntensityAlgorithm extends Algorithm
{
    IntensityAlgorithm()
    {
        super.setName("Intensity");
    }

    @Override
    List<Phosphene> process(Mat frame, List<Phosphene> phospheneGrid, int maxGrid)
    {

       // int numRows = 8;
       // int numCols = 8;
        int gridIndexX = 0;
        int gridIndexY = 0;
       // Mat intensityMap = new Mat(numRows, numCols, CvType.CV_8U);
        double[] temp;
        //int[][] avgIntent = new int[numRows][numCols];
        int avg = 0;
        int count = 0;
        int maxListSize = maxGrid; //!!!!MUST BE AN ODD NUMBER!!!!
        for (Phosphene p: phospheneGrid)
        {
            avg = 0;
            count = 0;
            gridIndexX = p.getxLoc();
            gridIndexY = p.getyLoc();
            for(int i = gridIndexX;i<gridIndexX + frame.width()/maxListSize;i += (frame.width()/maxListSize)/3) //Increment by a third each
            {
                for (int j = gridIndexY; j <gridIndexY + frame.height()/maxListSize;j += (frame.height()/maxListSize)/3)//Same
                {
                    temp = frame.get(i, j);
                    avg += temp[0];
                    count++;
                }
            }
            p.setIntensity(avg/count);
        }

        //intensityMap = InitMatrix(intensityMap, avgIntent);
        //return intensityMap;
        return phospheneGrid;
    }
}
