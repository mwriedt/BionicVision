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

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity
{
    private Setting currentSetting;

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
        Parser parse = new Parser(getApplicationContext());
        StringBuilder phosFileOutput = parse.readFile();
        String[] phosFileStr = phosFileOutput.toString().split(",");

        Log.d("PHOS", "Phosphenes: " + phosFileStr[0] + " " + phosFileStr[1]);

        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(confirmBtnListener);
    }

    OnClickListener confirmBtnListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            saveSettings();
        }
    };

    public void onBackPressed()
    {
        saveSettings();

        Intent intent = new Intent();
        ArrayList<Setting> dataList = new ArrayList<>();
        dataList.add(currentSetting);

        intent.putParcelableArrayListExtra("SETTING", dataList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void saveSettings()
    {
        Spinner algorithmView = (Spinner) findViewById(R.id.spinner);
        String algorithmText = algorithmView.getSelectedItem().toString();

        if (currentSetting == null)
        {
            currentSetting = new Setting(algorithmText, 0.0, 0.0, 0, 0);
        }
        else
        {
            currentSetting.update(algorithmText, 0.0, 0.0, 0, 0);
        }
    }
}
