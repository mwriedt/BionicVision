package software_a.com.bionicvision;

import org.opencv.core.Mat;

import java.util.List;

class IntensityAlgorithm extends Algorithm
{
    IntensityAlgorithm() {super.setName("Intensity");}

    @Override
    List<Phosphene> process(Mat frame, List<Phosphene> phospheneGrid, int maxGrid)
    {
        //!!!! MAXGRID MUST BE AN ODD NUMBER!!!!
        int gridIndexX;
        int gridIndexY;
        double[] temp;
        int avg;
        int count;

        for (Phosphene p: phospheneGrid)
        {
            avg = 0;
            count = 0;
            gridIndexX = p.getxLoc();
            gridIndexY = p.getyLoc();

            // in order to calculate intensity, the Phosphene Map is split into a series of grids
            // one phosphene occupies each grid space
            // within each grid space, 9 pixels are chosen to generate the average intensity for that region
            // phosphene intensity is then set for that region before moving onwards
            for(int i = gridIndexX; i < gridIndexX + frame.width() / maxGrid; i += (frame.width() / maxGrid) / 3)
            {
                for (int j = gridIndexY; j < gridIndexY + frame.height() / maxGrid; j += (frame.height() / maxGrid) / 3)
                {
                    temp = frame.get(i, j);
                    avg += temp[0];
                    count++;
                }
            }

            p.setIntensity(avg/count);
        }

        return phospheneGrid;
    }
}
