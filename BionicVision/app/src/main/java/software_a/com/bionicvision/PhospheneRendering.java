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
        double spacing = 1;
        // -------------------------------------

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        int radius = 255/maxRad;
        int CPx = 0;
        int CPy = 0;
        int testX;
        int testY;
        int drawX;
        int drawY;
        int CPRawX = 0;
        int CPRawY = 0;
        int maxGridSize = 0;
        for (Phosphene p: data)
        {
            if (p.isCP)
            {
                maxGridSize = 2 * p.xLoc + 1;
                CPx = (p.xLoc * (width/maxGridSize)/2);
                CPy  = (p.yLoc) * (height/maxGridSize);
                CPRawX = p.xLoc;
                CPRawY = p.yLoc;
            }
        }

        // Call draw function for each circle
        for (int i = 0; i < noOfCircles; i++)
        {
            testX = data.get(i).xLoc - CPRawX;
            testY = data.get(i).yLoc - CPRawY;
            drawX = CPx + (int)(testX * spacing) + (testX * (2 * maxRad));
            drawY = CPy + (int)(testY * spacing) + (testY * (2 * maxRad));
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