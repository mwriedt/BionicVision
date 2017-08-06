package software_a.com.bionicvision;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class CameraActivity extends AppCompatActivity implements CvCameraViewListener2 {

    //Display displayScale = getWindowManager().getDefaultDisplay();
   // Display displayScale = (Display)getWindowManager().getDefaultDisplay();

    //============= THIS SESSION
  //  WindowManager window = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

//   Display display = displayScale;
//    Point windowSize = new Point();
//    int width = display.getWidth();
//    int height = display.getHeight();



    //For debug mode make true
//    boolean debug = false;
    PhospheneRendering renderDots = new PhospheneRendering();
    // Used for logging success or failure messages
    private static final String TAG = "OCVSample::Activity";

    // Loads camera view of OpenCV for us to use. This lets us see using OpenCV
    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public CameraActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.camera_activity_java_surface_view);
        mOpenCvCameraView.setMaxFrameSize(320, 240);

//        android.graphics.Rect screen = new android.graphics.Rect();
//        mOpenCvCameraView.getWindowVisibleDisplayFrame(screen);
//        mOpenCvCameraView.setMaxFrameSize(screen.width(), screen.height());

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);



        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {

    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.gray();

        int numRows = 8;
        int numCols = 8;
        int gridIndexX = 0;
        int gridIndexY = 0;
        Mat intensityMap = new Mat(numRows, numCols, CvType.CV_8U);
        double[] temp;
        int[][] avgIntent = new int[numRows][numCols];

        for (int a = 0; a < numCols * numRows; a++) {
            for (int i = (frame.height() / numCols) * gridIndexY; i < (frame.height() / numCols) * (gridIndexY + 1); i += 9) {
                for (int j = (frame.width() / numRows) * gridIndexX; j < (frame.width() / numRows) * (gridIndexX + 1); j += 19) {
                    temp = frame.get(i, j);
                    avgIntent[gridIndexY][gridIndexX] += temp[0];

                }
            }

            avgIntent[gridIndexY][gridIndexX] /= 9;

            gridIndexX++;

            if (gridIndexX > numRows - 1) {
                gridIndexX = 0;
                gridIndexY++;
            }
        }

        intensityMap = InitMatrix(intensityMap, avgIntent);

        for (int p = 0; p < numCols; p++)
        {
            for (int q = 0; q < numRows; q++) {
                //if(debug)
                    Log.i(TAG, "Average Intensity: " + avgIntent[p][q]);
            }
        }
        Mat dots = renderDots.Render(intensityMap, 2560, 1440, numCols * numRows, frame);
        //DisplayMetrics displayMetrics = new DisplayMetrics();
        return dots; //frame;
    }

    private Mat InitMatrix(Mat matrix, int[][] array)
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
