package software_a.com.bionicvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void goToCamera(View v) {
        startActivity(new Intent(LaunchActivity.this, CameraActivity.class));
    }

    public void goToSettings(View v) {
        startActivity(new Intent(LaunchActivity.this, SettingsActivity.class));
    }

    public void goToAbout(View v) {
        startActivity(new Intent(LaunchActivity.this, AboutActivity.class));
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
