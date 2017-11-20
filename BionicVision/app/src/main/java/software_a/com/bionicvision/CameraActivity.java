package software_a.com.bionicvision;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;

public class CameraActivity extends AppCompatActivity implements CvCameraViewListener2
{
    Algorithm intensity = new IntensityAlgorithm();
    PhospheneRendering renderDots = new PhospheneRendering();

    private Algorithm algorithm;
    int phospheneAmount;
    int maxListSize;
    double cameraFoV;
    double screenFoV;
    double phospheneSpacing;
    int phospheneSize;
    boolean fileLoad;
    boolean phospheneRecording;

    PhospheneMap phospeheneMap;
    List<Phosphene> alivePhosphenes;

    // Used for logging success or failure messages
    private static final String TAG = "BIONIC::CameraActivity";

    // Loads camera view of OpenCV to be displayed when the Activity is run
    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this)
    {
        @Override
        public void onManagerConnected(int status)
        {
            switch (status)
            {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default:
                {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public CameraActivity() {Log.i(TAG, "Instantiated new " + this.getClass());}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.camera_activity_java_surface_view);
        mOpenCvCameraView.setMaxFrameSize(320, 240);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        initialiseUI();
    }

    private void initialiseUI()
    {
        Bundle settingsBundle = getIntent().getExtras();
        String alg = settingsBundle.getString("Algorithm");
        phospheneAmount = settingsBundle.getInt("PhospheneAmount");
        maxListSize = settingsBundle.getInt("PhospheneMaxListSize");
        cameraFoV = settingsBundle.getDouble("PhospheneCameraFoV");
        screenFoV = settingsBundle.getDouble("PhospheneScreenFoV");
        phospheneSpacing = settingsBundle.getDouble("PhospheneSpacing");
        phospheneSize = settingsBundle.getInt("PhospheneSize");
        fileLoad = settingsBundle.getByte("PhospheneLoad") != 0;
        phospheneRecording = settingsBundle.getByte("PhospheneRecording") != 0;

        renderDots = new PhospheneRendering(phospheneSize, phospheneSpacing);
        phospeheneMap = new PhospheneMap(phospheneAmount);
        alivePhosphenes = phospeheneMap.getPhosphenes();

        if (alg == null)
        {
            algorithm = new BlankAlgorithm();
        }
        else
        {
            algorithm = AlgorithmSwitch.choose(alg);
        }
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

        Mat croppedFrame = CroptoFoV(frame, 75);

        List<Phosphene> intensityMap = intensity.process(croppedFrame, alivePhosphenes, maxListSize);

        if (algorithm.getName().equals("Intensity"))
        {
            return renderDots.RenderGrid(intensityMap, 320, 240, phospheneAmount, maxListSize, croppedFrame);
        }

        return croppedFrame;
    }

    public Mat CroptoFoV(Mat current, int fov)
    {
        int width = FieldOfView.CalculateCaptureFoV(fov, current.width());
        int height = FieldOfView.CalculateCaptureFoV(fov, current.height());

        // Setup a rectangle to define your region of interest
        Rect cropRegion = new Rect((current.width()-width)/2, (current.height()-height)/2, width, height);

        Mat croppedImage = Mat.zeros(current.size(),current.type());

        // Crop the full image to that image contained by the rectangle myROI
        // Note that this doesn't copy the data
        //current.submat(cropRegion).copyTo(croppedImage.submat(cropRegion));
        org.opencv.imgproc.Imgproc.resize(current.submat(cropRegion), croppedImage, current.size());
        return croppedImage;
    }
}

