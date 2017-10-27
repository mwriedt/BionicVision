package software_a.com.bionicvision;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
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

public class CameraActivity extends AppCompatActivity implements CvCameraViewListener2 {
    Algorithm intensity = new IntensityAlgorithm();
    PhospheneRendering renderDots = new PhospheneRendering();
    //TODO: Replace with grid from settings bundle
    int numOfPhos = 61;
    int maxListSize = 17;
    PhospheneMap phospeheneMap = new PhospheneMap(numOfPhos);
    List<Phosphene> alivePhosphenes = phospeheneMap.getPhosphenes();
    private Algorithm algorithm;

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
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        initialiseUI();
    }

    private void initialiseUI()
    {
        Bundle settingsBundle = getIntent().getExtras();
        String alg = settingsBundle.getString("Algorithm");
        double gamma = settingsBundle.getDouble("PhospheneGamma");
        double spacing = settingsBundle.getDouble("PhospheneSpacing");
        int amount = settingsBundle.getInt("PhospheneAmount");
        int size = settingsBundle.getInt("PhospheneSize");
        byte loadB = settingsBundle.getByte("PhospheneLoad");
        boolean load = loadB != 0;

        renderDots = new PhospheneRendering(size, spacing);

        if (alg == null)
        {
            algorithm = new BlankAlgorithm();
        }
        else
        {
            AlgorithmSwitch theSwitch = new AlgorithmSwitch();
            algorithm = theSwitch.choose(alg);
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
            return renderDots.RenderGrid(intensityMap, 320, 240, numOfPhos, croppedFrame);
        }

        return croppedFrame;
    }

    public Mat CroptoFoV(Mat Current, int FoV)
    {
        FieldOfView FoVObject = new FieldOfView();

        int width = FoVObject.CalculateCaptureFoV(FoV, Current.width());
        int height = FoVObject.CalculateCaptureFoV(FoV, Current.height());

        // Setup a rectangle to define your region of interest
        Rect cropRegion = new Rect((Current.width()-width)/2, (Current.height()-height)/2, width, height);

        Mat croppedImage = Current.zeros(Current.size(),Current.type());

        // Crop the full image to that image contained by the rectangle myROI
        // Note that this doesn't copy the data
        //Current.submat(cropRegion).copyTo(croppedImage.submat(cropRegion));
        org.opencv.imgproc.Imgproc.resize(Current.submat(cropRegion), croppedImage, Current.size());
        return croppedImage;
    }
}

