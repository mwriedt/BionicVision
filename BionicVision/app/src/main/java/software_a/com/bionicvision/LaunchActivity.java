package software_a.com.bionicvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {
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
            int outAmount = settingsObj.getPhosAmount();
            int outMaxList = settingsObj.getPhosMaxList();
            double outCameraFoV = settingsObj.getPhosCFoV();
            double outScreenFoV = settingsObj.getPhosSFoV();
            double outSpacing = settingsObj.getPhosSpacing();
            int outSize = settingsObj.getPhosSize();
            boolean outLoad = settingsObj.getPhosLoad();
            boolean outRecord = settingsObj.getPhosRecord();

            Log.d("TAG", "Spacing (Launch): " + outSpacing);
            Log.d("TAG", "Size (Launch): " + outSize);

            settings.putString("Algorithm", outAlg);
            settings.putInt("PhospheneAmount", outAmount);
            settings.putInt("PhospheneMaxListSize", outMaxList);
            settings.putDouble("PhospheneCameraFoV", outCameraFoV);
            settings.putDouble("PhospheneScreenFoV", outScreenFoV);
            settings.putDouble("PhospheneSpacing", outSpacing);
            settings.putInt("PhospheneSize", outSize);
            settings.putByte("PhospheneLoad", (byte) (outLoad ? 1 : 0));
            settings.putByte("PhospheneRecording", (byte) (outRecord ? 1 : 0));
        }
    }

    public void goToCamera()
    {
        Log.d("TAG", "Settings = null: " + (settings.isEmpty()));

        if (settings.isEmpty())
        {
            Bundle blankSettings = new Bundle();
            blankSettings.putString("Algorithm", "Blank");
            blankSettings.putInt("PhospheneAmount", 13);
            blankSettings.putInt("PhospheneMaxListSize", 5);
            blankSettings.putDouble("PhospheneCameraFoV", 75);
            blankSettings.putDouble("PhospheneScreenFoV", 0);
            blankSettings.putDouble("PhospheneSpacing", 10.0);
            blankSettings.putInt("PhospheneSize", 16);
            blankSettings.putByte("PhospheneLoad", (byte) 0);
            blankSettings.putByte("PhospheneRecording", (byte) 0);

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

    public void goToAbout() {startActivity(new Intent(LaunchActivity.this, AboutActivity.class));}
}
