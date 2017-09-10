package software_a.com.bionicvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    Bundle settingsGlobal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        initialiseUI();
    }

    private void initialiseUI()
    {
        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        Button btnCamera = (Button) findViewById(R.id.btn_camera);
        btnConfirm.setOnClickListener(confirmBtnListener);
        btnCamera.setOnClickListener(cameraBtnListener);
    }

    OnClickListener confirmBtnListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Spinner algorithmView = (Spinner) findViewById(R.id.spinner);
            String algorithmText = algorithmView.getSelectedItem().toString();

            settingsGlobal = saveSettings(algorithmText);
        }
    };

    OnClickListener cameraBtnListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToCamera(settingsGlobal);
        }
    };

    private Bundle saveSettings(String algorithm)
    {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putString("Algorithm", algorithm);

        return settingsBundle;
    }

    private void goToCamera(Bundle settings)
    {

        Log.i("TAG", "Settings: " + settings.getString("Algorithm"));

        Intent dataSettings = new Intent();
        dataSettings.setClass(getApplicationContext(), software_a.com.bionicvision.CameraActivity.class);
        dataSettings.putExtras(settings);

        startActivity(dataSettings);
    }
}
