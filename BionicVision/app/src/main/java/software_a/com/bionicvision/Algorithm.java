package software_a.com.bionicvision;

import org.opencv.core.Mat;

/**
 * Created by Justin on 5/09/2017.
 */

public abstract class Algorithm
{
    Algorithm()
    {

    }
    Algorithm(String name)
    {
        String lName = name;
    }

    abstract Mat process(Mat frame);

    protected Mat InitMatrix(Mat matrix, int[][] array)
    {
        Mat temp = matrix;
        for (int i = 0; i < 8;i++)
        {
            for (int j = 0; j<8;j++)
            {
                temp.put(i,j,array[i][j]);
            }
        }

        return temp;
    }

}
