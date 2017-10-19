package software_a.com.bionicvision;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class SettingsActivity extends AppCompatActivity
{
    private Setting currentSetting;
    static final Integer WRITE_EXST = 0x3;
    static final Integer PCAMERA = 0x5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        initialiseUI();
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SettingsActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
    public void ask(View v){
    switch (v.getId()){
        case R.id.btnS:
            askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
            break;
        case R.id.btnC:
            askForPermission(Manifest.permission.CAMERA,PCAMERA);
            break;
        default:
            break;
    }
}

    private void initialiseUI()
    {
        Parser parse = new Parser(getApplicationContext());
        StringBuilder phosFileOutput = parse.readFile();
        String[] phosFileStr = phosFileOutput.toString().split(",");

        Log.d("PHOS", "Phosphenes: " + phosFileStr[0] + " " + phosFileStr[1]);

        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(confirmBtnListener);

        SeekBar gammaSeekView = (SeekBar) findViewById(R.id.seek_gamma);
        EditText gammaEditView = (EditText) findViewById(R.id.edittxt_gamma);
        SeekBar spacingSeekView = (SeekBar) findViewById(R.id.seek_spacing);
        EditText spacingEditView = (EditText) findViewById(R.id.edittxt_spacing);
        SeekBar amountSeekView = (SeekBar) findViewById(R.id.seek_amount);
        EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);
        SeekBar sizeSeekView = (SeekBar) findViewById(R.id.seek_size);
        EditText sizeEditView = (EditText) findViewById(R.id.edittxt_size);

        gammaEditView.setText(String.valueOf(gammaSeekView.getProgress()));
        spacingEditView.setText(String.valueOf(spacingSeekView.getProgress()));
        amountEditView.setText(String.valueOf(amountSeekView.getProgress()));
        sizeEditView.setText(String.valueOf(sizeSeekView.getProgress()));

        gammaSeekView.setOnSeekBarChangeListener(gammaSeekChangeListener);
        gammaEditView.setOnFocusChangeListener(gammaFocusListener);
        spacingSeekView.setOnSeekBarChangeListener(spacingSeekChangeListener);
        spacingEditView.setOnFocusChangeListener(spacingFocusListener);
        amountSeekView.setOnSeekBarChangeListener(amountSeekChangeListener);
        amountEditView.setOnFocusChangeListener(amountFocusListener);
        sizeSeekView.setOnSeekBarChangeListener(sizeSeekChangeListener);
        sizeEditView.setOnFocusChangeListener(sizeFocusListener);
    }

    OnClickListener confirmBtnListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            saveSettings();
        }
    };

    OnSeekBarChangeListener gammaSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText gammaEditView = (EditText) findViewById(R.id.edittxt_gamma);
            gammaEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };

    OnFocusChangeListener gammaFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar gammaSeekView = (SeekBar) findViewById(R.id.seek_gamma);
                String progValue = ((EditText) v).getText().toString();
                gammaSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };

    OnSeekBarChangeListener spacingSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText spacingEditView = (EditText) findViewById(R.id.edittxt_spacing);
            spacingEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };

    OnFocusChangeListener spacingFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar spacingSeekView = (SeekBar) findViewById(R.id.seek_spacing);
                String progValue = ((EditText) v).getText().toString();
                spacingSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };


    OnSeekBarChangeListener amountSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);
            amountEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };

    OnFocusChangeListener amountFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar amountSeekView = (SeekBar) findViewById(R.id.seek_amount);
                String progValue = ((EditText) v).getText().toString();
                amountSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };

    OnSeekBarChangeListener sizeSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText sizeEditView = (EditText) findViewById(R.id.edittxt_size);
            sizeEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };

    OnFocusChangeListener sizeFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar sizeSeekView = (SeekBar) findViewById(R.id.seek_size);
                String progValue = ((EditText) v).getText().toString();
                sizeSeekView.setProgress(Integer.parseInt(progValue));
            }
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
        Spinner algorithmView = (Spinner) findViewById(R.id.spin_algorithm);
        String algorithm = algorithmView.getSelectedItem().toString();

        EditText gammaEditView = (EditText) findViewById(R.id.edittxt_gamma);
        double gamma = Double.parseDouble(gammaEditView.getText().toString());
        EditText spacingEditView = (EditText) findViewById(R.id.edittxt_spacing);
        double spacing = Double.parseDouble(spacingEditView.getText().toString());
        EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);
        int amount = Integer.parseInt(amountEditView.getText().toString());
        EditText sizeEditView = (EditText) findViewById(R.id.edittxt_size);
        int size = Integer.parseInt(sizeEditView.getText().toString());

        if (currentSetting == null)
        {
            currentSetting = new Setting(algorithm, gamma, spacing, amount, size);
        }
        else
        {
            currentSetting.update(algorithm, gamma, spacing, amount, size);
        }
    }
}
