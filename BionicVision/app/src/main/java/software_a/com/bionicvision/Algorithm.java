package software_a.com.bionicvision;

import org.opencv.core.Mat;

public abstract class Algorithm
{
    private String name = "";

    Algorithm()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    abstract Mat process(Mat frame);

    Mat InitMatrix(Mat matrix, int[][] array)
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
