package software_a.com.bionicvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {
    private static final int RESULT_SETTINGS = 1;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
        }
        return true;
    }
}
