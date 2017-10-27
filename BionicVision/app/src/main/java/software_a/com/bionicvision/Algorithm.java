package software_a.com.bionicvision;

import org.opencv.core.Mat;

import java.util.List;

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

    abstract List<Phosphene> process(Mat frame, List<Phosphene> phospheneGrid, int maxGrid);

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
