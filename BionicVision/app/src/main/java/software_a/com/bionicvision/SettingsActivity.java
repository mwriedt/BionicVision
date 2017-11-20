package software_a.com.bionicvision;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity
{
    private Setting currentSetting;
    static final Integer WRITE_EXST = 0x3;
    static final Integer PCAMERA = 0x5;
    public ArrayList<File> fileList = new ArrayList<File>();
    public ArrayList<String> stringFileList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        initialiseUI();


//        //CSV folder init
//        File out = getFilesDir();
//        File folder = new File(out + "/CSV");
//        boolean success = true;
//
//        // Create folder if it doesn't exist
//        if(!folder.exists())
//        {
//            success = folder.mkdir();
//        }
//
//        //Read CSV Files in folder
//        if(success)
//        {
//            for (File f : folder.listFiles())
//            {
//                fileList.add(f);
//                stringFileList.add(f.getAbsolutePath());
//            }
//            //Spinner filePicker = (Spinner)findViewById(R.id.);
//            Spinner filePicker = (Spinner) findViewById(R.id.spn_filePicker);
//            //=====================
//            // Might need to populate stringFileList with a file location first before compilation
//            //=====================
//            if(stringFileList.size() == 0)
//            {
//                //ArrayList<String> noFilesList = new ArrayList<String>();
//                //noFilesList.add("No Files Foind.");
//                stringFileList.add("No Files Found");
//            }
//            else
//            {
//                filePicker.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_settings, stringFileList));
//            }
//        }
//        else
//        {
//            fileList = new ArrayList<File>();
//        }
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
        case R.id.btn_storage:
            askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
            break;
        case R.id.btn_camera:
            askForPermission(Manifest.permission.CAMERA,PCAMERA);
            break;
        default:
            break;
    }
}

    private void initialiseUI()
    {
        int[] defaultSettings = new int[6];
        defaultSettings[0] = 13;
        defaultSettings[1] = 5;
        defaultSettings[2] = 75;
        defaultSettings[3] = 0;
        defaultSettings[4] = 10;
        defaultSettings[5] = 5;

        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(confirmBtnListener);

        SeekBar amountSeekView = (SeekBar) findViewById(R.id.seek_amount);
        amountSeekView.setProgress(defaultSettings[0]);
        EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);

        SeekBar maxlistsizeSeekView = (SeekBar) findViewById(R.id.seek_maxlistsize);
        maxlistsizeSeekView.setProgress(defaultSettings[1]);
        EditText maxlistsizeEditView = (EditText) findViewById(R.id.edittxt_maxlistsize);

        SeekBar fovcameraSeekView = (SeekBar) findViewById(R.id.seek_fovcamera);
        fovcameraSeekView.setProgress(defaultSettings[2]);
        EditText fovcameraEditView = (EditText) findViewById(R.id.edittxt_fovcamera);

        SeekBar fovscreenSeekView = (SeekBar) findViewById(R.id.seek_fovscreen);
        fovscreenSeekView.setProgress(defaultSettings[3]);
        EditText fovscreenEditView = (EditText) findViewById(R.id.edittxt_fovscreen);

        SeekBar spacingSeekView = (SeekBar) findViewById(R.id.seek_spacing);
        spacingSeekView.setProgress(defaultSettings[4]);
        EditText spacingEditView = (EditText) findViewById(R.id.edittxt_spacing);

        SeekBar sizeSeekView = (SeekBar) findViewById(R.id.seek_size);
        sizeSeekView.setProgress(defaultSettings[5]);
        EditText sizeEditView = (EditText) findViewById(R.id.edittxt_size);

        Spinner filePicker = (Spinner) findViewById(R.id.spn_filePicker);

        ArrayAdapter<String> fileAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, generateFileLocations());
        filePicker.setAdapter(fileAdapter);

        amountEditView.setText(String.valueOf(amountSeekView.getProgress()));
        maxlistsizeEditView.setText(String.valueOf(maxlistsizeSeekView.getProgress()));
        fovcameraEditView.setText(String.valueOf(fovscreenSeekView.getProgress()));
        fovscreenEditView.setText(String.valueOf(fovscreenSeekView.getProgress()));
        spacingEditView.setText(String.valueOf(spacingSeekView.getProgress()));
        sizeEditView.setText(String.valueOf(sizeSeekView.getProgress()));

        amountSeekView.setOnSeekBarChangeListener(amountSeekChangeListener);
        amountEditView.setOnFocusChangeListener(amountFocusListener);
        maxlistsizeSeekView.setOnSeekBarChangeListener(maxlistsizeSeekChangeListener);
        maxlistsizeEditView.setOnFocusChangeListener(maxlistsizeFocusListener);
        fovcameraSeekView.setOnSeekBarChangeListener(fovcameraSeekChangeListener);
        fovcameraEditView.setOnFocusChangeListener(fovcameraFocusListener);
        fovscreenSeekView.setOnSeekBarChangeListener(fovscreenSeekChangeListener);
        fovscreenEditView.setOnFocusChangeListener(fovscreenFocusListener);
        spacingSeekView.setOnSeekBarChangeListener(spacingSeekChangeListener);
        spacingEditView.setOnFocusChangeListener(spacingFocusListener);
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

    OnSeekBarChangeListener amountSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);
            amountEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    OnFocusChangeListener amountFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar amountSeekView = (SeekBar) findViewById(R.id.seek_amount);

                if (((EditText) v).getText().toString().equals(""))
                {
                    amountSeekView.setProgress(0);
                }

                String progValue = ((EditText) v).getText().toString();
                amountSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };

    OnSeekBarChangeListener maxlistsizeSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText maxlistsizeEditView = (EditText) findViewById(R.id.edittxt_maxlistsize);
            maxlistsizeEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    OnFocusChangeListener maxlistsizeFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar maxlistsizeSeekView = (SeekBar) findViewById(R.id.seek_maxlistsize);

                if (((EditText) v).getText().toString().equals(""))
                {
                    maxlistsizeSeekView.setProgress(0);
                }

                String progValue = ((EditText) v).getText().toString();
                maxlistsizeSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };

    OnSeekBarChangeListener fovcameraSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText fovcameraEditView = (EditText) findViewById(R.id.edittxt_fovcamera);
            fovcameraEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    OnFocusChangeListener fovcameraFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar fovcameraSeekView = (SeekBar) findViewById(R.id.seek_fovcamera);

                if (((EditText) v).getText().toString().equals(""))
                {
                    fovcameraSeekView.setProgress(0);
                }

                String progValue = ((EditText) v).getText().toString();
                fovcameraSeekView.setProgress(Integer.parseInt(progValue));
            }
        }
    };

    OnSeekBarChangeListener fovscreenSeekChangeListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            EditText fovscreenEditView = (EditText) findViewById(R.id.edittxt_fovscreen);
            fovscreenEditView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    OnFocusChangeListener fovscreenFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar fovscreenSeekView = (SeekBar) findViewById(R.id.seek_fovscreen);

                if (((EditText) v).getText().toString().equals(""))
                {
                    fovscreenSeekView.setProgress(0);
                }

                String progValue = ((EditText) v).getText().toString();
                fovscreenSeekView.setProgress(Integer.parseInt(progValue));
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
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    OnFocusChangeListener spacingFocusListener = new OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (!hasFocus)
            {
                SeekBar spacingSeekView = (SeekBar) findViewById(R.id.seek_spacing);

                if (((EditText) v).getText().toString().equals(""))
                {
                    spacingSeekView.setProgress(0);
                }

                String progValue = ((EditText) v).getText().toString();
                spacingSeekView.setProgress(Integer.parseInt(progValue));
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

                if (((EditText) v).getText().toString().equals(""))
                {
                    sizeSeekView.setProgress(0);
                }

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

        EditText amountEditView = (EditText) findViewById(R.id.edittxt_amount);
        EditText maxlistEditView = (EditText) findViewById(R.id.edittxt_maxlistsize);
        EditText cfovEditView = (EditText) findViewById(R.id.edittxt_fovcamera);
        EditText sfovEditView = (EditText) findViewById(R.id.edittxt_fovscreen);
        EditText spacingEditView = (EditText) findViewById(R.id.edittxt_spacing);
        EditText sizeEditView = (EditText) findViewById(R.id.edittxt_size);
        CheckBox loadView = (CheckBox) findViewById(R.id.chk_load);
        CheckBox recordView = (CheckBox) findViewById(R.id.chk_record);

        String file = "blank";

        if (recordView.isChecked())
        {
            Spinner fileView = (Spinner) findViewById(R.id.spn_filePicker);
            file = fileView.getSelectedItem().toString();
        }

        int amount = Integer.parseInt(amountEditView.getText().toString());
        int maxlist = Integer.parseInt(maxlistEditView.getText().toString());
        double cfov = Double.parseDouble(cfovEditView.getText().toString());
        double sfov = Double.parseDouble(sfovEditView.getText().toString());
        double spacing = Double.parseDouble(spacingEditView.getText().toString());
        int size = Integer.parseInt(sizeEditView.getText().toString());
        boolean load = loadView.isChecked();
        boolean record = recordView.isChecked();

        if (currentSetting == null)
        {
            currentSetting = new Setting(algorithm, amount, maxlist, cfov, sfov, spacing, size, load, record, file);
        }
        else
        {
            currentSetting.update(algorithm, amount, maxlist, cfov, sfov, spacing, size, load, record, file);
        }
    }

    public ArrayList<String> generateFileLocations()
    {
        try
        {
            AssetManager assetManager = getApplicationContext().getAssets();

            String[] temp = assetManager.list("");
            ArrayList<String> result = new ArrayList<>();

            for (String file : temp)
            {
                if (file.endsWith(".csv"))
                {
                    result.add(file);
                }
            }

            return result;
        }
        catch (IOException e)
        {
            ArrayList<String> result = new ArrayList<>();
            result.add("No entries found");
            return result;
        }
    }
}
