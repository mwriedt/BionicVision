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

import java.util.List;

/**
 * Created by aaron on 22/07/2017.
 */

public class PhospheneRendering {


    Mat Render(List<Phosphene> data, int width, int height, int noOfCircles, Mat frame)
    {
        //Mat& img
        //Point center
        //int Radius
        //const Scalar& color
        //int thickness
        //int lineTpye

        //----- make these funnction params -----
        int maxRad = 5;
        float fov = 1.0f;
        // -------------------------------------

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        double noOfRows = Math.sqrt(noOfCircles);
        double noOfColumns = noOfRows;
        int radius = 255/maxRad;
        Scalar intensity = new Scalar(0);
        double[] colour = new double[width*height];
        int count = 0;

        // Determine centerpoints for each grid
        int cp1Height = height/2;
        int cp1Width = width/4;
        int cp2Height = cp1Height;
        int cp2Width = width - cp1Width;

       // Imgproc.cvtColor(temp,temp, Imgproc.COLOR_GRAY2RGBA, 4);

        org.opencv.core.Point positions[] = new org.opencv.core.Point[noOfCircles];
        int spacingW = (width/noOfCircles * 7/8)/2;
        int spacingH = height/noOfCircles * 3/4;



        // Define Positions
//        for (int i = 0; i < noOfColumns; i++)
//        {
//            int yPos = spacingH/2 + i * spacingH;
//            for(int j = 0; j < noOfRows; j++)
//            {
//                int xPos = spacingW/2 + (j) * spacingW;
//                org.opencv.core.Point pos = new org.opencv.core.Point(xPos, yPos);
//                positions[i*8 + j] = pos;
//                colour[count] = data.get(i,j)[0];
//                count++;
//            }
//        }

//        for (Phosphene p :data)
//        {
//            org.opencv.core.Point pos = new org.opencv.core.Point(p.xLoc * spacingW + (p.xLoc - 1) * 2*(maxRad) + maxRad,
//                    p.yLoc * spacingH + (p.yLoc - 1) * 2*(maxRad) + maxRad);


      //  }
//        for (int i = 0; i < noOfCircles; i++)
//        {
//            org.opencv.core.Point pos = new org.opencv.core.Point(data[i].xLoc * spacingW + (data[i].xLoc - 1) * 2*(maxRad) + maxRad,
//                    data[i].yLoc * spacingH + (data[i].yLoc - 1) * 2*(maxRad) + maxRad);
//            double finalcolour;
//            if (colour[i] > 15)
//                finalcolour = (data[i].getIntensity())/255;
//            else
//                finalcolour = 0;
//
//            //Get radius for Phosphene rendering based on Mat value
//            int phospheneRadius = (int)intensity.val[0]/radius;
//            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
//
//        }

        //Make new array to store rgba colours
//        Mat intensityArray[] = new Mat[width*height];
//        for(int i = 0; i < intensityArray.length; i++)
//        {
//            intensityArray[i] = new Mat(255,255,255,new Scalar(colour[i]));
//        }

        // Call draw function for each circle
      //  for (int i = 0; i < noOfCircles; i++)
       // {
           // data[i].xLoc += spacingH * 3;
            //data[i].yLoc += spacingH * 3;

            //intensity = new Scalar(colour[i]);

            //Crush blacks
//            double finalcolour;
//            if (colour[i] > 15)
//                finalcolour = (data[i].getIntensity())/255;
//            else
//                finalcolour = 0;
//
//            //Get radius for Phosphene rendering based on Mat value
//            int phospheneRadius = (int)intensity.val[0]/radius;
//            org.opencv.imgproc.Imgproc.circle(temp, positions[i], phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
            //org.opencv.imgproc.Imgproc.circle(temp,)
        //}

        //Mat tempFrame = new Mat();
        //double opacity = 0.0;
//
//        for (int i = 0; i < noOfCircles; i++)
//        {
//            positions[i].x += 2*(spacingW + (noOfRows) * spacingW) - 6*(maxRad + spacingH);
//            intensity = new Scalar(colour[i]);
//
//            //Crush blacks
//            double finalcolour;
//            if (colour[i] > 25)
//                finalcolour = (colour[i]-25)/255;
//            else
//                finalcolour = 0;
//
//            //Get radius for Phosphene rendering based on Mat value
//            int phospheneRadius = (int)intensity.val[0]/radius;
//            org.opencv.imgproc.Imgproc.circle(temp, positions[i], phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
//
//        }

        temp = GaussianBlur(temp, new Size(9,9));
        return temp;
    }

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
        // -------------------------------------

        int xSpace = 5;
        int ySpace = 5;

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        //double noOfRows = Math.sqrt(noOfCircles);
        //double noOfColumns = noOfRows;
        int radius = 255/maxRad;
        Scalar intensity = new Scalar(0);
        double[] colour = new double[width*height];
        int count = 0;

        // Determine centerpoints for each grid
        int cp1Height = height/2;
        int cp1Width = width/4;
        int cp2Height = cp1Height;
        int cp2Width = width - cp1Width;

       // int spacing = (int)((fov -(2*(noOfColumns*maxRad)))/(noOfColumns-1));

        //int tc1x = (int)(cp1Width - ((noOfRows/2)*maxRad) - (((noOfRows-1)/2) * spacing));
        //int tc1y = (int)(cp1Height - ((noOfColumns/2)*maxRad) - (((noOfColumns-1)/2) * spacing));

        //int tc2x = (int)(cp2Width - ((noOfRows/2)*maxRad)- (((noOfRows-1)/2) * xSpace));
        //int tc2y = (int)(cp2Height - ((noOfColumns/2)*maxRad)- (((noOfColumns-1)/2) * ySpace));

        // Imgproc.cvtColor(temp,temp, Imgproc.COLOR_GRAY2RGBA, 4);

        org.opencv.core.Point positions[] = new org.opencv.core.Point[noOfCircles];
        int spacingW = 0;//(width/noOfCircles * 7/8)/2;
        int spacingH = 0;//height/noOfCircles * 3/4;




        // Call draw function for each circle
        for (int i = 0; i < noOfCircles; i++)
        {
           // int xPos = data.get(i).xLoc * spacingW + (data.get(i).xLoc - 1) * (2 * maxRad) + maxRad;
            int xPos = (data.get(i).xLoc * (width/17))/2;
            int yPos = data.get(i).yLoc * (height/17);
            //int yPos = data.get(i).yLoc * spacingH + (data.get(i).yLoc - 1) * (2 * maxRad) + maxRad;
//            org.opencv.core.Point pos = new org.opencv.core.Point((data.get(i).xLoc * spacingW + (data.get(i).xLoc - 1) * 2*(maxRad) + maxRad)/2,
//                    data.get(i).yLoc * spacingH + (data.get(i).yLoc - 1) * 2*(maxRad) + maxRad);
            org.opencv.core.Point pos = new org.opencv.core.Point(xPos,yPos);
           // org.opencv.core.Point pos = new org.opencv.core.Point((data.get(i).xLoc * spacingW) +(data.get(i).xLoc - 1) * (2 * maxRad)) + maxRad)/2, data.get(i).yLoc * (spacingH + (2 * maxRad))+ maxRad);
            double finalcolour;
            if (data.get(i).getIntensity() > 15)
                finalcolour = (data.get(i).getIntensity())/255;
            else
                finalcolour = 0;

            //Get radius for Phosphene rendering based on Mat value
            //int phospheneRadius = (int)intensity.val[0]/radius;
            int phospheneRadius = (int)data.get(i).getIntensity()/radius;
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
            xPos = (data.get(i).xLoc * (width/17))/2 + width/2;
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