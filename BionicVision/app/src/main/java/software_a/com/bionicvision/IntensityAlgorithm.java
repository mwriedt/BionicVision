package software_a.com.bionicvision;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

class IntensityAlgorithm extends Algorithm
{
    IntensityAlgorithm()
    {
        super.setName("Intensity");
    }

    @Override
    Mat process(Mat frame)
    {
        int numRows = 8;
        int numCols = 8;
        int gridIndexX = 0;
        int gridIndexY = 0;
        Mat intensityMap = new Mat(numRows, numCols, CvType.CV_8U);
        double[] temp;
        int[][] avgIntent = new int[numRows][numCols];

        for (int a = 0; a < numCols * numRows; a++) {
        for (int i = (frame.height() / numCols) * gridIndexY; i < (frame.height() / numCols) * (gridIndexY + 1); i += 9) {
            for (int j = (frame.width() / numRows) * gridIndexX; j < (frame.width() / numRows) * (gridIndexX + 1); j += 19) {
                temp = frame.get(i, j);
                avgIntent[gridIndexY][gridIndexX] += temp[0];

            }
        }

        avgIntent[gridIndexY][gridIndexX] /= 9;

        gridIndexX++;

        if (gridIndexX > numRows - 1) {
            gridIndexX = 0;
            gridIndexY++;
        }
    }

        intensityMap = InitMatrix(intensityMap, avgIntent);
        return intensityMap;
    }
}
