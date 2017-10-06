package software_a.com.bionicvision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by koalaah on 6/10/17.
 */

public class Storage {

    private Mat fFrame;
    private FileOutputStream fOStream;

    Storage(Mat frame) {
        this.fFrame = frame;
        this.fOStream = null;
    }

    public Bitmap getData() {

        Bitmap output = null;

        try {
            output = Bitmap.createBitmap(fFrame.cols(), fFrame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(fFrame, output);

        } catch (CvException e) {
            e.printStackTrace();
            Log.d("TAG", e.getMessage());
        }

        fFrame.release();

        return output;
    }

    public File getDirectory() {
        File externalDir = new File(Environment.getExternalStorageDirectory() + "/images");

        if (!externalDir.exists()) {
            externalDir.mkdir();
        }

        return externalDir;
    }

    public void saveFile() {
        File outputDir = getDirectory();
        Bitmap outputData = getData();

        try {
            File outputFile = outputDir;
            outputFile.createNewFile();

            fOStream = new FileOutputStream(outputFile);
            outputData.compress(Bitmap.CompressFormat.PNG, 100, fOStream);

            fOStream.close();

            Log.d("EXTERNAL", outputFile + " saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



    // Convert to Bitmap
//    Bitmap bmp = null;
//    try {
//        bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(frame, bmp);
//    } catch (CvException e) {
//        Log.d("TAG", e.getMessage());
//    }
//
//        frame.release();
//
//    FileOutputStream out = null;
//
//    String filename = "TestFile.png";
//
//    File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//    boolean success = true;
//        if (!sd.exists()) {
//        success = sd.mkdir();
//    }
//        if (success) {
//        File dest = new File(sd, filename);
//
//        try {
//            out = new FileOutputStream(dest);
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
//            // PNG is a lossless format, the compression factor (100) is ignored
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("TAG", e.getMessage());
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                    Log.d("TAG", "OK!!");
//                    Log.d("External", Environment.getExternalStorageDirectory().getAbsolutePath());
//                }
//            } catch (IOException e) {
//                Log.d("TAG", e.getMessage() + "Error");
//                e.printStackTrace();
//            }
//        }
//    }

