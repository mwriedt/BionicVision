package software_a.com.bionicvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {
    private static final int RESULT_SETTINGS = 1;
    private Bundle settings = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);

        intialiseUI();
    }

    private void intialiseUI()
    {
        ImageButton cameraBtnView = (ImageButton) findViewById(R.id.btn_camera);
        ImageButton settingsBtnView = (ImageButton) findViewById(R.id.btn_settings);
        ImageButton aboutBtnView = (ImageButton) findViewById(R.id.btn_about);
        cameraBtnView.setOnClickListener(cameraClickListener);
        settingsBtnView.setOnClickListener(settingClickListener);
        aboutBtnView.setOnClickListener(aboutClickListener);
    }

    View.OnClickListener cameraClickListener = new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
          goToCamera();
      }
    };

    View.OnClickListener settingClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToSettings();
        }
    };

    View.OnClickListener aboutClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToAbout();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (intent != null)
        {
            ArrayList<Setting> settingsList = intent.getParcelableArrayListExtra("SETTING");
            Setting settingsObj = settingsList.get(0);
            String outAlg = settingsObj.getAlgorithm();
            double outGamma = settingsObj.getPhosGamma();
            double outSpacing = settingsObj.getPhosSpacing();
            int outAmount = settingsObj.getPhosAmount();
            int outSize = settingsObj.getPhosSize();

            settings.putString("Algorithm", outAlg);
            settings.putDouble("PhospheneGamma", outGamma);
            settings.putDouble("PhospheneSpacing", outSpacing);
            settings.putInt("PhospheneAmount", outAmount);
            settings.putInt("PhospheneSize", outSize);
        }
    }

    public void goToCamera()
    {
        if (settings == null)
        {
            Bundle blankSettings = new Bundle();
            blankSettings.putString("Algorithm", "Blank");
            blankSettings.putDouble("PhospheneGamma", 0.0);
            blankSettings.putDouble("PhospheneSpacing", 1.0);
            blankSettings.putInt("PhospheneAmount", 64);
            blankSettings.putInt("PhospheneSize", 16);

            Intent cameraIntent = new Intent();
            cameraIntent.setClass(getApplicationContext(), CameraActivity.class);
            cameraIntent.putExtras(blankSettings);
            startActivity(cameraIntent);
        }
        else
        {
            Intent cameraIntent = new Intent();
            cameraIntent.setClass(getApplicationContext(), CameraActivity.class);
            cameraIntent.putExtras(settings);
            startActivity(cameraIntent);
        }
    }

    public void goToSettings()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i, 0);
    }

    public void goToAbout()
    {
        startActivity(new Intent(LaunchActivity.this, AboutActivity.class));
    }
}
