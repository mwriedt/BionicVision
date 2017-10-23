package software_a.com.bionicvision;

import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Created by aaron on 22/07/2017.
 */

public class PhospheneRendering {


    int CPx = 0;
    int CPy = 0;
    int valX;
    int valY;
    int drawX;
    int drawY;
    int CPRawX = 0;
    int CPRawY = 0;
    int maxGridSize = 0;

    Mat RenderGrid(List<Phosphene> data, int width, int height, int noOfCircles, Mat frame)
    {
        //Mat& img
        //Point center
        //int Radius
        //const Scalar& color
        //int thickness
        //int lineTpye

        //----- make these funnction params -----
        int maxRad = 5;
        float fov = 115;
        double spacing = 1;
        // -------------------------------------

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        int radius = 255/maxRad;


        for (Phosphene p: data)
        {
            if (p.getIsCP())
            {
                maxGridSize = 2 * p.getxLoc() + 1;
                CPx = (p.getxLoc() * (width/maxGridSize)/2);
                CPy  = (p.getyLoc()) * (height/maxGridSize);
                CPRawX = p.getxLoc();
                CPRawY = p.getyLoc();
            }
        }

        // Call draw function for each circle
        for (int i = 0; i < noOfCircles; i++)
        {
            valX = data.get(i).getxLoc() - CPRawX;
            valY = data.get(i).getyLoc() - CPRawY;
            drawX = CPx + (int)(valX * spacing) + (valX * (2 * maxRad));
            drawY = CPy + (int)(valY * spacing) + (valY * (2 * maxRad));
            int xPos = drawX;
            int yPos = drawY;
            org.opencv.core.Point pos = new org.opencv.core.Point(xPos,yPos);
            double finalcolour;
            if (data.get(i).getIntensity() > 15)
                finalcolour = (data.get(i).getIntensity())/255;
            else
                finalcolour = 0;
            //Get radius for Phosphene rendering based on Mat value
            //int phospheneRadius = (int)intensity.val[0]/radius;
            int phospheneRadius = (int)data.get(i).getIntensity()/radius;
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
            xPos += width/2;
            pos = new org.opencv.core.Point(xPos,yPos);
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
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