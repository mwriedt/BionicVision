package software_a.com.bionicvision;

import android.util.DisplayMetrics;

import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Imgproc.*;

/**
 * Created by aaron on 22/07/2017.
 */

public class PhospheneRendering {


    Mat Render(Mat data, int width, int height, int noOfCircles, Mat frame)
    {
        //Mat& img
        //Point center
        //int Radius
        //const Scalar& color
        //int thickness
        //int lineTpye

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        double noOfRows = Math.sqrt(noOfCircles);
        double noOfColumns = noOfRows;
        int maxRad = 5;
        int radius = 255/maxRad;
        Scalar intensity = new Scalar(0);
        double[] colour = new double[width*height];
        int count = 0;

        org.opencv.core.Point positions[] = new org.opencv.core.Point[noOfCircles];
        int spacingW = (width/noOfCircles * 7/8)/2;
        int spacingH = height/noOfCircles * 3/4;

        // Define Positions
        for (int i = 0; i < noOfColumns; i++)
        {
            int yPos = spacingH/2 + i * spacingH;
            for(int j = 0; j < noOfRows; j++)
            {
                int xPos = spacingW/2 + (j) * spacingW;
                org.opencv.core.Point pos = new org.opencv.core.Point(xPos, yPos);
                positions[i*8 + j] = pos;
                colour[count] = data.get(i,j)[0];
                count++;
            }
        }

        // Call draw function for each circle
        for (int i = 0; i < noOfCircles; i++)
        {
            positions[i].y += spacingH * 3;
            intensity = new Scalar(colour[i]);
            int phospheneRadius = (int)intensity.val[0]/radius;
            org.opencv.imgproc.Imgproc.circle(temp, positions[i], phospheneRadius, intensity, -1);
        }

        for (int i = 0; i < noOfCircles; i++)
        {
            positions[i].x += 2*(spacingW + (noOfRows) * spacingW) - 6*(maxRad + spacingH);
            intensity = new Scalar(colour[i]);
            //Imgproc.cvtColor(data,data, Imgproc.COLOR_GRAY2RGBA);
            //Get radius for Phosphene rendering based on Mat value
            int phospheneRadius = (int)intensity.val[0]/radius;
            org.opencv.imgproc.Imgproc.circle(temp, positions[i], phospheneRadius, intensity, -1);
        }

        temp = GaussianBlur(temp, new Size(9,9));
        return temp;
    }

    Mat GaussianBlur(Mat data, Size blur)
    {
        Imgproc.GaussianBlur(data,data,blur,3);
        return data;
    }
}
