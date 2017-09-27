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
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class CameraActivity extends AppCompatActivity implements CvCameraViewListener2 {

    //Display displayScale = getWindowManager().getDefaultDisplay();
   // Display displayScale = (Display)getWindowManager().getDefaultDisplay();

    //============= THIS SESSION
  //  WindowManager window = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

//   Display display = displayScale;
//    Point windowSize = new Point();
//    int width = display.getWidth();
//    int height = display.getHeight();

    //

    //For debug mode make true
//    boolean debug = false;
    Algorithm intensity = new IntensityAlgorithm();
    PhospheneRendering renderDots = new PhospheneRendering();

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

//        android.graphics.Rect screen = new android.graphics.Rect();
//        mOpenCvCameraView.getWindowVisibleDisplayFrame(screen);
//        mOpenCvCameraView.setMaxFrameSize(screen.width(), screen.height());

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        initialiseUI();
    }

    private void initialiseUI()
    {
        Bundle settingsBundle = getIntent().getExtras();
        String alg = settingsBundle.getString("Algorithm");

        AlgorithmSwitch theSwitch = new AlgorithmSwitch();
        algorithm = theSwitch.choose(alg);
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
        Mat intensityMap = intensity.process(frame);

        Log.i("TAG", "Algorithm Name: " + algorithm.getName());

        if (algorithm.getName() == "Intensity")
        {
            Mat dots = renderDots.Render(intensityMap, 2560, 1440, 64, frame);
            return dots;
        }

        return frame;

        //DisplayMetrics displayMetrics = new DisplayMetrics();
    }
}
