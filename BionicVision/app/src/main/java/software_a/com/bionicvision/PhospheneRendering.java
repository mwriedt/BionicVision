package software_a.com.bionicvision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

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
        int radius = 5;
        Scalar intensity = new Scalar(0);
        double[] colour = new double[64];
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
            org.opencv.imgproc.Imgproc.circle(temp, positions[i], radius, intensity, -1);
        }

        for (int i = 0; i < noOfCircles; i++)
        {
            positions[i].x += 2*(spacingW + (noOfRows) * spacingW) - 6*(radius + spacingH);
            intensity = new Scalar(colour[i]);
            org.opencv.imgproc.Imgproc.circle(temp, positions[i], radius, intensity, -1);
        }

        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(frame, bmp);
        } catch (CvException e) {
            Log.d("TAG", e.getMessage());
        }

        frame.release();

        FileOutputStream out = null;

//        String filename = "frame.png";

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
}
