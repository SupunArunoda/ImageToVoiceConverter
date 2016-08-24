

package com.yalantis.sample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.yalantis.cameramodule.activity.CameraActivity;
import com.yalantis.sample.Const;
import com.yalantis.sample.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.PATH, Const.FOLDERS.PATH);
        intent.putExtra(CameraActivity.OPEN_PHOTO_PREVIEW, true);
        intent.putExtra(CameraActivity.LAYOUT_ID, R.layout.fragment_camera_custom);
        intent.putExtra(CameraActivity.USE_FRONT_CAMERA, false);
        startActivity(intent);
    }

}
