package software_a.com.bionicvision;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

class PhospheneRendering {

    private int mMaxRad;
    private double mFov;
    private double mSpacing;
    private int CPx = 0;
    private int CPy = 0;
    private int valX;
    private int valY;
    private int drawX;
    private int drawY;
    private int CPRawX = 0;
    private int CPRawY = 0;
    private int maxGridSize = 0;

    PhospheneRendering() {}

    PhospheneRendering(int size, double spacing, double fov)
    {
        this.mMaxRad = size;
        this.mFov = fov;
        this.mSpacing = spacing;
    }

    Mat RenderGrid(List<Phosphene> data, int width, int height, int noOfCircles, int gridSize, Mat frame)
    {
        Log.d("TAG", "spacing: " + mSpacing);

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));
        // Initialise Variables
        int radius = 255 / mMaxRad;

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
            drawX = CPx + (int)Math.ceil((valX * mSpacing) + (valX * (2 * mMaxRad))*(mFov/75));
            drawY = CPy + (int)Math.ceil((valY * mSpacing) + (valY * (2 * mMaxRad))*(mFov/75));
            int xPos = drawX;
            int yPos = drawY;
            org.opencv.core.Point pos = new org.opencv.core.Point(xPos,yPos);
            double finalcolour;
            if (data.get(i).getIntensity() > 15)
                finalcolour = (data.get(i).getIntensity())/255;
            else
                finalcolour = 0;

            int phospheneRadius = data.get(i).getIntensity()/radius;
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
            xPos += width/2;
            pos = new org.opencv.core.Point(xPos,yPos);
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
        }
        temp = GaussianBlur(temp, new Size(9,9));

        // record frames
        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(frame, bmp);
        } catch (CvException e) {
            Log.d("TAG", e.getMessage());
        }

        frame.release();

        FileOutputStream out = null;

        File sd = new File(Environment.getExternalStorageDirectory() + "/frames");
        boolean success = true;

        if (!sd.exists()) {
            success = sd.mkdir();
        }

        if (success) {
            Random gen = new Random();
            int n = 1000;
            n = gen.nextInt(n);
            String outputFileName = "Image-" + n + ".jpg";
            File dest = new File(sd, outputFileName);

            try {
                out = new FileOutputStream(dest);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        Log.d("TAG", "OK!!");
                    }
                } catch (IOException e) {
                    Log.d("TAG", e.getMessage() + "Error");
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }

    Mat RenderFromFile(List<Phosphene> data, int width, Mat frame, List<Point> positions)
    {
        int maxRad = 5;
        int radius = 255/maxRad;
        int noOfCircles = positions.size();

        frame.zeros(frame.size(),frame.type());
        Mat temp = new Mat(frame.size(),CvType.CV_8U,new Scalar(0,0,0));

        // Call draw function for each circle
        for (int i = 0; i < noOfCircles; i++)
        {
            // Get location for the next phosphene
            Point pos = positions.get(i);

            double finalcolour;
            if (data.get(i).getIntensity() > 15)
                finalcolour = (data.get(i).getIntensity())/255;
            else
                finalcolour = 0;

            // Get radius for Phosphene rendering based on Mat value
            int phospheneRadius = data.get(i).getIntensity()/radius;

            // Render for Left Eye
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);

            // Render for Right Eye
            pos = new Point(positions.get(i).x + width/2, positions.get(i).y); // Move over on X axis
            org.opencv.imgproc.Imgproc.circle(temp, pos, phospheneRadius, new Scalar(255,255,255,finalcolour), -1);
        }
        temp = GaussianBlur(temp, new Size(9,9));

        // record frames
        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(frame, bmp);
        } catch (CvException e) {
            Log.d("TAG", e.getMessage());
        }

        frame.release();

        FileOutputStream out = null;

        File sd = new File(Environment.getExternalStorageDirectory() + "/frames");
        boolean success = true;

        if (!sd.exists()) {
            success = sd.mkdir();
        }

        if (success) {
            Random gen = new Random();
            int n = 1000;
            n = gen.nextInt(n);
            String outputFileName = "Image-" + n + ".jpg";
            File dest = new File(sd, outputFileName);

            try {
                out = new FileOutputStream(dest);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        Log.d("TAG", "OK!!");
                    }
                } catch (IOException e) {
                    Log.d("TAG", e.getMessage() + "Error");
                    e.printStackTrace();
                }
            }
        }

        return temp;
    }

    private Mat GaussianBlur(Mat data, Size blur)
    {
        Imgproc.GaussianBlur(data,data,blur,3);
        return data;
    }
}
